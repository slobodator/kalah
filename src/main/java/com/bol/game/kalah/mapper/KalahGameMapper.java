package com.bol.game.kalah.mapper;

import com.bol.game.kalah.controller.response.KalahGameDto;
import com.bol.game.kalah.entity.KalahGame;
import com.bol.game.kalah.entity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KalahGameMapper {
    private final PlayerMapper playerMapper;
    private final KalahBoardMapper kalahBoardMapper;

    public KalahGameDto toDto(KalahGame game, Player player) {
        Player opponent = game.opponent(player);

        return new KalahGameDto(
                game.getId(),
                playerMapper.toDto(player),
                playerMapper.toDto(opponent),
                game.isInPlay(),
                game.isMyTurn(player),
                game.isWinner(player),
                kalahBoardMapper.toDto(game.board(player), true),
                kalahBoardMapper.toDto(game.board(opponent), false)
        );
    }
}
