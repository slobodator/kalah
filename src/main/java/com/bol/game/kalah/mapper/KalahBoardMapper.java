package com.bol.game.kalah.mapper;

import com.bol.game.kalah.controller.response.KalahBoardDto;
import com.bol.game.kalah.controller.response.PitDto;
import com.bol.game.kalah.entity.KalahBoard;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static com.bol.game.kalah.entity.KalahGame.PITS_AMOUNT;

@Component
public class KalahBoardMapper {
    public KalahBoardDto toMyBoardDto(KalahBoard board) {
        return toDto(board, true);
    }

    public KalahBoardDto toOpponentBoardDto(KalahBoard board) {
        return toDto(board, false);
    }

    private KalahBoardDto toDto(KalahBoard board, boolean playable) {
        return new KalahBoardDto(
                Stream.concat(
                        board
                                .getPits()
                                .stream()
                                .limit(PITS_AMOUNT)
                                .map(p -> new PitDto(p, true, p > 0 && playable)),
                        Stream.of(
                                new PitDto(board.getStorePit(), false, false)
                        )
                ).toList()
        );
    }
}
