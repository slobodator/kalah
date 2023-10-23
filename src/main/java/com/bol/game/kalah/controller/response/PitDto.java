package com.bol.game.kalah.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Pit information"
)
public record PitDto(
        @Schema(
                description = "Stones amount at the pit",
                required = true,
                example = "3"
        )
        Integer amount,

        @Schema(
                description = "Regular pits are small, the store is big",
                required = true
        )
        boolean small,

        @Schema(
                description = "Indicates if the pit is allowed to be played. " +
                        "The kalah/store and the opponent pits are not available for a move",
                required = true
        )
        boolean playable
) {
}
