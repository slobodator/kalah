package com.bol.game.kalah.controller;

import com.bol.game.kalah.controller.request.KalahGameRequest;
import com.bol.game.kalah.controller.request.KalahMoveRequest;
import com.bol.game.kalah.controller.response.KalahGameDto;
import com.bol.game.kalah.service.KalahGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("kalah/games")
@RequiredArgsConstructor
public class KalahGameController {
    private final KalahGameService kalahGameService;

    @PostMapping
    public KalahGameDto create(
            @Valid @RequestBody KalahGameRequest kalahGameRequest,
            Principal principal
    ) {
        return kalahGameService.create(
                kalahGameRequest,
                principal
        );
    }

    @GetMapping("/{id}")
    public KalahGameDto get(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        return kalahGameService.get(
                id,
                principal
        );
    }

    @PostMapping("/{id}/moves")
    public KalahGameDto move(
            @PathVariable("id") Long id,
            @Valid @RequestBody KalahMoveRequest kalahMoveRequest,
            Principal principal
    ) {
        return kalahGameService.move(
                id,
                kalahMoveRequest,
                principal
        );
    }
}
