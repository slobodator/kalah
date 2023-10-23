package com.bol.game.kalah.controller;

import com.bol.game.kalah.controller.request.PlayerRequest;
import com.bol.game.kalah.controller.response.PlayerDto;
import com.bol.game.kalah.service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping
    public PlayerDto create(
            @Valid @RequestBody PlayerRequest playerRequest
    ) {
        return playerService.create(playerRequest);
    }
}
