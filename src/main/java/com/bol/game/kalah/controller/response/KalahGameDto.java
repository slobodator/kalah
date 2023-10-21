package com.bol.game.kalah.controller.response;

public record KalahGameDto(
        Long id,
        PlayerDto me,
        PlayerDto opponent,
        boolean inPlay,
        boolean myTurn,
        Boolean iAmWinner,
        KalahBoardDto myBoard,
        KalahBoardDto opponentBoard
) {
}
