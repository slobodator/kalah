package com.bol.game.kalah.mapper;

import com.bol.game.kalah.controller.response.KalahBoardDto;
import com.bol.game.kalah.controller.response.PitDto;
import com.bol.game.kalah.entity.KalahBoard;
import org.springframework.stereotype.Component;

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
                board
                        .getPits()
                        .stream()
                        .map(
                                p -> new PitDto(
                                        p.getStones(),
                                        p.isSmall(),
                                        p.isPlayable() && playable
                                )
                        )
                        .toList()
        );
    }
}
