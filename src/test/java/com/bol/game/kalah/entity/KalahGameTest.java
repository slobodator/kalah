package com.bol.game.kalah.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class KalahGameTest {
    private final Player adamPlayer = new Player("adam@bol.com", "Adam");
    private final Player bobPlayer = new Player("bob@bol.com", "Bob");
    private final KalahGame game = new KalahGame(
            List.of(
                    adamPlayer,
                    bobPlayer
            )
    );

    @Test
    void shouldKeepTurn() {
        game.setPosition(
                        KalahBoard.init(6, 0, 0, 0, 0, 0, 0),
                        KalahBoard.init(0, 0, 0, 0, 0, 0, 0)
                )
                .move(0, adamPlayer);

        assertThat(
                game.board(adamPlayer)
        ).isEqualTo(
                KalahBoard.init(0, 1, 1, 1, 1, 1, 1)
        );

        assertThat(
                game.isMyTurn(adamPlayer)
        ).isTrue();
    }

    @Test
    void shouldSkipOpponentsKalah() {
        game.setPosition(
                        KalahBoard.init(0, 15, 0, 0, 0, 0, 0),
                        KalahBoard.init(0, 0, 0, 0, 0, 0, 0)
                )
                .move(1, adamPlayer);

        assertThat(
                game.board(adamPlayer)
        ).isEqualTo(
                KalahBoard.init(1, 1, 2, 1, 1, 1, 1)
        );

        assertThat(
                game.isMyTurn(adamPlayer)
        ).isFalse();

        assertThat(
                game.board(bobPlayer)
        ).isEqualTo(
                KalahBoard.init(1, 1, 1, 1, 1, 1, 0)
        );
    }

    @Test
    void shouldTakeFromOppositePitIfOne() {
        game.setPosition(
                        KalahBoard.init(0, 0, 0, 0, 1, 0, 0),
                        KalahBoard.init(10, 0, 0, 0, 0, 0, 0)
                )
                .move(4, adamPlayer);

        assertThat(
                game.board(adamPlayer)
        ).isEqualTo(
                KalahBoard.init(0, 0, 0, 0, 0, 0, 11)
        );

        assertThat(
                game.board(bobPlayer)
        ).isEqualTo(
                KalahBoard.init(0, 0, 0, 0, 0, 0, 0)
        );
    }

    @Test
    void shouldEndGame() {
        game.setPosition(
                        KalahBoard.init(0, 0, 0, 0, 0, 1, 6),
                        KalahBoard.init(10, 0, 0, 0, 0, 0, 0)
                )
                .move(5, adamPlayer);

        assertThat(
                game.board(adamPlayer)
        ).isEqualTo(
                KalahBoard.init(0, 0, 0, 0, 0, 0, 7)
        );

        assertThat(
                game.board(bobPlayer)
        ).isEqualTo(
                KalahBoard.init(0, 0, 0, 0, 0, 0, 10)
        );

        assertThat(
                game.isInPlay()
        ).isFalse();

        assertThat(
                game.isWinner(bobPlayer)
        ).isTrue();
    }

    @Test
    void shouldNotAllowPlayEmptyPit() {
        assertThatThrownBy(
                () -> game.setPosition(
                                KalahBoard.init(0, 0, 0, 0, 0, 1, 6),
                                KalahBoard.init(10, 0, 0, 0, 0, 0, 0)
                        )
                        .move(0, adamPlayer)
        )
                .isInstanceOf(KalahGameException.class)
                .hasMessageContaining("The pit 0 is not allowed for the move");

    }
}