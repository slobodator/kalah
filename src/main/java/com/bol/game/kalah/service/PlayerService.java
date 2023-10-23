package com.bol.game.kalah.service;

import com.bol.game.kalah.controller.request.PlayerRequest;
import com.bol.game.kalah.controller.response.PlayerDto;
import com.bol.game.kalah.entity.Player;
import com.bol.game.kalah.mapper.PlayerMapper;
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
    private final PlayerMapper playerMapper;

    @Transactional
    public PlayerDto create(PlayerRequest playerRequest) {
        if (playerRepository.findByEmail(playerRequest.email()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User %s already exists".formatted(playerRequest.email())
            );
        }

        return playerMapper.toDto(
                playerRepository.save(
                        Player.newWithPassword(
                                playerRequest.email(),
                                passwordEncoder.encode(
                                        playerRequest.password()
                                ),
                                playerRequest.nickname()
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
