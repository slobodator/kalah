package com.bol.game.kalah.mapper;

import com.bol.game.kalah.controller.response.KalahBoardDto;
import com.bol.game.kalah.controller.response.PitDto;
import com.bol.game.kalah.entity.KalahBoard;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static com.bol.game.kalah.entity.KalahGame.PITS_AMOUNT;

@Component
public class KalahBoardMapper {
    public KalahBoardDto toDto(KalahBoard board, boolean enabled) {
        return new KalahBoardDto(
                Stream.concat(
                        board
                                .getPits()
                                .stream()
                                .limit(PITS_AMOUNT)
                                .map(p -> new PitDto(p, true, p > 0 && enabled)),
                        Stream.of(
                                new PitDto(board.kalahPit(), false, false)
                        )
                ).toList()
        );
    }
}
