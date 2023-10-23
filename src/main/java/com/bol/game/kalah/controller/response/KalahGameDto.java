package com.bol.game.kalah.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Kalah game response"
)
public record KalahGameDto(
        @Schema(
                description = "Kalah game ID",
                required = true,
                example = "1"
        )
        Long id,

        @Schema(
                description = "My info",
                required = true
        )
        PlayerDto me,

        @Schema(
                description = "Opponent info",
                required = true
        )
        PlayerDto opponent,

        @Schema(
                description = "'true' means the game is on going, 'false' is someone won",
                required = true
        )
        boolean inPlay,

        @Schema(
                description = "Any player may ask the current status of the game. It says if it is my turn",
                required = true
        )
        boolean myTurn,

        @Schema(
                description = "Indicates who is a winner." +
                        " 'true' means the actual player won," +
                        " 'false' -- her/his opponent." +
                        " Until the game is playing it is 'null'",
                required = true,
                nullable = true
        )
        Boolean iAmWinner,

        @Schema(
                description = "My board. It is a player specific. The opponent will see her/his board as 'mine'",
                required = true
        )
        KalahBoardDto myBoard,

        @Schema(
                description = "Opponent board. It is a player specific.",
                required = true
        )
        KalahBoardDto opponentBoard
) {
}
