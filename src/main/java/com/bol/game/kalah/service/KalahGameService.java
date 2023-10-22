package com.bol.game.kalah.service;

import com.bol.game.kalah.controller.request.KalahGameRequest;
import com.bol.game.kalah.controller.request.KalahMoveRequest;
import com.bol.game.kalah.controller.response.KalahGameDto;
import com.bol.game.kalah.entity.KalahGame;
import com.bol.game.kalah.entity.Player;
import com.bol.game.kalah.mapper.KalahGameMapper;
import com.bol.game.kalah.repository.KalahGameRepository;
import com.bol.game.kalah.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class KalahGameService {
    private final KalahGameRepository kalahGameRepository;
    private final PlayerRepository playerRepository;

    private final KalahGameMapper kalahGameMapper;

    public KalahGameDto create(KalahGameRequest kalahGameRequest, Principal principal) {
        List<Player> players = kalahGameRequest
                .players()
                .stream()
                .map(
                        id -> playerRepository
                                .findById(id)
                                .orElseThrow(
                                        () -> new ResponseStatusException(
                                                HttpStatus.BAD_REQUEST,
                                                "Player %d not found".formatted(id)
                                        )
                                )
                ).toList();

        KalahGame kalahGame = kalahGameRepository.save(
                new KalahGame(
                        players,
                        kalahGameRequest.stonesPerPit()
                )
        );

        Player player = playerRepository.findByPrincipal(principal);
        return kalahGameMapper.toDto(kalahGame, player);
    }

    @Transactional(readOnly = true)
    public KalahGameDto get(Long id, Principal principal) {
        KalahGame kalahGame = kalahGameRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Kalah Game %d not found".formatted(id)
                        )
                );
        Player player = playerRepository.findByPrincipal(principal);
        return kalahGameMapper.toDto(kalahGame, player);
    }

    public KalahGameDto move(Long id, KalahMoveRequest kalahMoveRequest, Principal principal) {
        KalahGame kalahGame = kalahGameRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Kalah Game %d not found".formatted(id)
                        )
                );
        Player player = playerRepository.findByPrincipal(principal);
        kalahGame.move(kalahMoveRequest.pitIndex(), player);
        return kalahGameMapper.toDto(kalahGame, player);
    }
}
