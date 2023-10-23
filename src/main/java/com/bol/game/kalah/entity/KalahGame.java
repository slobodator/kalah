package com.bol.game.kalah.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "kalah_games")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Slf4j
public class KalahGame {
    public static final int PITS_AMOUNT = 6;
    public static final int DEFAULT_STONES_PER_PIT = 6;

    @SuppressWarnings("unused")
    @Id
    @SequenceGenerator(name = "kalah_games_seq", sequenceName = "kalah_games_seq")
    @GeneratedValue(generator = "kalah_games_seq", strategy = GenerationType.SEQUENCE)
    @ToString.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_1_id")
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player_2_id")
    private Player player2;

    @Column(name = "player_1_board")
    @Convert(converter = KalahBoardConverter.class)
    private KalahBoard player1Board;

    @Column(name = "player_2_board")
    @Convert(converter = KalahBoardConverter.class)
    private KalahBoard player2Board;

    @ManyToOne
    private Player whoseTurn;

    @ManyToOne
    @Nullable
    private Player winner;

    public KalahGame(
            List<Player> players,
            Integer stonesPerPit
    ) {
        if (players.size() != 2) {
            throw new KalahGameException("There should be 2 players");
        }

        this.player1 = players.get(0);
        this.player2 = players.get(1);

        if (this.player1.equals(this.player2)) {
            throw new KalahGameException("Players should vary");
        }

        this.whoseTurn = this.player1;

        this.player1Board = new KalahBoard(stonesPerPit);
        this.player2Board = new KalahBoard(stonesPerPit);
    }

    public KalahGame(List<Player> players) {
        this(players, DEFAULT_STONES_PER_PIT);
    }

    public boolean isInPlay() {
        return this.winner == null;
    }

    public Player opponent(Player player) {
        if (player.equals(this.player1)) {
            return this.player2;
        }

        if (player.equals(this.player2)) {
            return this.player1;
        }

        throw new IllegalArgumentException(
                "Player %s is not related to the game".formatted(player)
        );
    }

    public Boolean isWinner(Player player) {
        if (this.winner == null) {
            return null;
        }
        return this.winner.equals(player);
    }

    public boolean isMyTurn(Player player) {
        return this.whoseTurn.equals(player);
    }

    public KalahBoard board(Player player) {
        if (player.equals(this.player1)) {
            return this.player1Board;
        }

        if (player.equals(this.player2)) {
            return this.player2Board;
        }

        throw new IllegalArgumentException(
                "Player %s is not related to the game".formatted(player)
        );
    }

    public void move(int pitIndex, Player player) {
        if (pitIndex < 0 || pitIndex >= PITS_AMOUNT) {
            throw new KalahGameException(
                    "Pit %d is not at interval [0, %d)".formatted(pitIndex, PITS_AMOUNT)
            );
        }

        if (!player.equals(this.whoseTurn)) {
            throw new KalahGameException(
                    "It is not your turn now"
            );
        }

        this.player1Board.linkTo(player2Board);

        if (player.equals(this.player1)) {
            changeTurn(this.player1Board.move(pitIndex));
        } else if (player.equals(this.player2)) {
            changeTurn(this.player2Board.move(pitIndex));
        } else {
            throw new IllegalArgumentException(
                    "Player %s is not related to the game".formatted(player)
            );
        }
    }

    private void changeTurn(boolean change) {
        if (change) {
            this.whoseTurn = this.player1.equals(this.whoseTurn)
                    ? this.player2
                    : this.player1;
        }

        if (this.player1Board.isEmpty() || this.player1Board.isEmpty()) {
            log.debug("There are no more stones, the game is ended");
            this.winner = this.player1Board.gatherAllStones() > this.player2Board.gatherAllStones()
                    ? player1
                    : player2;
        }
    }

    /**
     * For testing purpose
     */
    public KalahGame setPosition(KalahBoard player1Board, KalahBoard player2Board) {
        this.player1Board = player1Board;
        this.player2Board = player2Board;
        return this;
    }
}
