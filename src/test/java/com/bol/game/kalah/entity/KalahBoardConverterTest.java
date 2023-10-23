package com.bol.game.kalah.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KalahBoardConverterTest {
    private final KalahBoardConverter converter = new KalahBoardConverter();

    @Test
    void convert() {
        KalahBoard kalahBoard = KalahBoard.init(0, 1, 2, 3, 4, 5, 6);

        assertThat(kalahBoard)
                .isEqualTo(
                        converter.convertToEntityAttribute(
                                converter.convertToDatabaseColumn(kalahBoard)
                        )
                );
    }
}