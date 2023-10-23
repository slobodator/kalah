package com.bol.game.kalah.controller.request;

import com.bol.game.kalah.entity.KalahGame;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Schema(
        description = "Kalah game move request"
)
public record KalahMoveRequest(
        @Schema(
                description = "The pit index to play. From 0 to 5",
                minimum = "0",
                maximum = "5",
                required = true,
                example = "2"
        )
        @Min(0)
        @Max(KalahGame.PITS_AMOUNT - 1)
        Integer pitIndex
) {
}
