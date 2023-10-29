package com.bol.game.kalah.entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Stream;

import static com.bol.game.kalah.entity.KalahGame.DEFAULT_STONES_PER_PIT;
import static com.bol.game.kalah.entity.KalahGame.PITS_AMOUNT;

@Slf4j
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class KalahBoard {
    @EqualsAndHashCode.Include
    @ToString.Include
    private final List<KalahPit> pits = new ArrayList<>();

    private KalahBoard opponentBoard;

    public KalahBoard(@Nullable Integer stonesPerPit) {
        this(
                Stream.concat(
                        Collections.nCopies(
                                Optional.ofNullable(stonesPerPit).orElse(DEFAULT_STONES_PER_PIT),
                                PITS_AMOUNT
                        ).stream(),
                        Stream.of(0)
                ).toList()
        );
    }

    public KalahBoard() {
        this(DEFAULT_STONES_PER_PIT);
    }

    private KalahBoard(List<Integer> pits) {
        for (int i = 0; i < PITS_AMOUNT; i++) {
            this.pits.add(KalahPit.smallOne(i, pits.get(i)));
        }
        this.pits.add(KalahPit.bigOne(pits.get(PITS_AMOUNT)));
    }

    public static KalahBoard init(List<Integer> pits) {
        return new KalahBoard(pits);
    }

    public static KalahBoard init(Integer... pits) {
        return new KalahBoard(Arrays.asList(pits));
    }

    /*
     * @return true if the turn is changed
     */
    protected boolean move(int pitIndex) {
        KalahBoard board = this;
        int stones = board.pit(pitIndex).pickUp();

        if (stones == 0) {
            throw new KalahGameException(
                    "The pit %d is not allowed for the move".formatted(pitIndex)
            );
        }

        while (stones > 0) {
            // next pit
            pitIndex++;

            // switches to the opponents board
            if (pitIndex > PITS_AMOUNT) {
                board = board.opponentBoard;
                pitIndex = 0;
                log.debug("Switches to the opponent board {}", board);
            }

            // sow a stone to the pit if it not a kalah pit
            if (board.equals(this) || pitIndex < PITS_AMOUNT) {
                int amount = board.pit(pitIndex).increase();

                // capture the opponent pit
                if (board.equals(this) && pitIndex < PITS_AMOUNT && amount == 1 && stones == 1) {
                    int opponentPitIndex = PITS_AMOUNT - pitIndex - 1;
                    this.pit(PITS_AMOUNT).increase(
                            board.pit(pitIndex).pickUp() +
                                    board.opponentBoard.pit(opponentPitIndex).pickUp()
                    );
                }
            }
            stones--;
        }

        @SuppressWarnings("UnnecessaryLocalVariable")
        boolean myTurn = pitIndex != PITS_AMOUNT;
        return myTurn;
    }

    @Nonnull
    private KalahPit pit(int pitIndex) {
        return this.pits.get(pitIndex);
    }

    protected void linkTo(KalahBoard opponentsBoard) {
        if (this.opponentBoard == null) {
            this.opponentBoard = opponentsBoard;
            this.opponentBoard.linkTo(this);
        }
    }

    protected boolean isEmpty() {
        return pits
                .stream()
                .limit(PITS_AMOUNT)
                .allMatch(KalahPit::isEmpty);
    }

    protected int gatherAllStones() {
        int sum = 0;
        for (int i = 0; i < PITS_AMOUNT; i++) {
            sum += pits.get(i).pickUp();
        }
        this.pit(PITS_AMOUNT).increase(sum);
        return sum;
    }
}
