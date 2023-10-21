package com.bol.game.kalah.controller.request;

import com.bol.game.kalah.validation.constraint.ElementsVary;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record KalahGameRequest(
        @NotNull
        @Size(min = 2, max = 2)
        @ElementsVary
        List<Long> players,

        Integer stonesPerPit
) {
}
