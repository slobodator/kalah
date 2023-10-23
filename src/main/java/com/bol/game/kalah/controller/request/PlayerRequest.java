package com.bol.game.kalah.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(
        description = "Player creation request"
)
public record PlayerRequest(
        @Schema(
                description = "Player email is used as a user name and thus must be unique",
                required = true,
                example = "adam@bol.com"
        )
        @NotBlank
        @Email
        String email,

        @Schema(
                description = "Player password",
                required = true,
                example = "mind a good password, please"
        )
        @NotBlank
        String password,

        @Schema(
                description = "Player nickname",
                required = true,
                example = "xXx @dam xXx"
        )
        @NotBlank
        String nickname
) {
}
