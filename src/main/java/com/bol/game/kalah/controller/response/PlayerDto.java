package com.bol.game.kalah.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Player response"
)
public record PlayerDto(
        @Schema(
                description = "Player ID",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Player nickname",
                example = "xXx @dam xXx"
        )
        String nickname
) {
}
