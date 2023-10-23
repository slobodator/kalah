package com.bol.game.kalah.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        description = "Kalah player (half) a board response. Contains a list of pits"
)
public record KalahBoardDto(
        @Schema(
                description = "Pits of the board"
        )
        List<PitDto> pits
) {
}
