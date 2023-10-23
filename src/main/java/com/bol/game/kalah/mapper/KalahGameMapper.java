package com.bol.game.kalah.mapper;

import com.bol.game.kalah.controller.response.KalahBoardDto;
import com.bol.game.kalah.controller.response.KalahGameDto;
import com.bol.game.kalah.controller.response.PlayerDto;
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

        PlayerDto meDto = playerMapper.toDto(player);
        PlayerDto opponentDto = playerMapper.toDto(opponent);

        KalahBoardDto myBoardDto = kalahBoardMapper.toMyBoardDto(game.board(player));
        KalahBoardDto opponentBoardDto = kalahBoardMapper.toOpponentBoardDto(game.board(opponent));

        return new KalahGameDto(
                game.getId(),
                meDto,
                opponentDto,
                game.isInPlay(),
                game.isMyTurn(player),
                game.isWinner(player),
                myBoardDto,
                opponentBoardDto
        );
    }
}
