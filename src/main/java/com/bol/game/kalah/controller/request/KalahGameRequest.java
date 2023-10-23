package com.bol.game.kalah.controller.request;

import com.bol.game.kalah.validation.constraint.ElementsVary;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(
        description = "Kalah game creation request"
)
public record KalahGameRequest(
        @Schema(
                description = "Player IDs that are going to play the game",
                required = true,
                example = "[1, 2]"
        )
        @NotNull
        @Size(min = 2, max = 2)
        @ElementsVary
        List<Long> playerIds,

        @Schema(
                description = "Stones per pit. If omitted the default value is used",
                defaultValue = "6",
                nullable = true
        )
        Integer stonesPerPit
) {
}
