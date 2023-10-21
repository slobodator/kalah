package com.bol.game.kalah.repository;

import com.bol.game.kalah.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Principal;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByEmail(String email);

    default Player findByPrincipal(Principal principal) {
        return findByEmail(principal.getName())
                .orElseThrow(
                        () -> new IllegalStateException(
                                "Player %s not found".formatted(principal.getName())
                        )
                );

    }
}
