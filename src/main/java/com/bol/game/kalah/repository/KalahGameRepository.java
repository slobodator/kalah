package com.bol.game.kalah.repository;

import com.bol.game.kalah.entity.KalahGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KalahGameRepository extends JpaRepository<KalahGame, Long> {
}
