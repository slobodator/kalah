package com.bol.game.kalah.service;

import com.bol.game.kalah.controller.PlayerRequest;
import com.bol.game.kalah.entity.Player;
import com.bol.game.kalah.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerService implements UserDetailsService {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void create(PlayerRequest playerRequest) {
        playerRepository
                .findByEmail(playerRequest.getEmail())
                .ifPresentOrElse(
                        p -> {
                            throw new ResponseStatusException(
                                    HttpStatus.BAD_REQUEST,
                                    "User %s already exists".formatted(playerRequest.getEmail())
                            );
                        },
                        () -> playerRepository.save(
                                new Player(
                                        playerRequest.getEmail(),
                                        passwordEncoder.encode(
                                                playerRequest.getPassword()
                                        ),
                                        playerRequest.getNickname()
                                )
                        )
                );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return playerRepository
                .findByEmail(username)
                .orElse(null);
    }
}
