package com.bol.game.kalah.controller.request;

import com.bol.game.kalah.entity.KalahGame;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record KalahMoveRequest(
        @Min(0)
        @Max(KalahGame.PITS_AMOUNT - 1)
        Integer pitIndex
) {
}
