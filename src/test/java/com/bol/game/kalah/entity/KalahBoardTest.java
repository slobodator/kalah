package com.bol.game.kalah.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class KalahBoardTest {
    @Test
    void startBoard() {
        assertThat(
                new KalahBoard()
        ).isEqualTo(
                KalahBoard.init(
                        List.of(
                                6, 6, 6, 6, 6, 6, 0
                        )
                )
        );
    }
}