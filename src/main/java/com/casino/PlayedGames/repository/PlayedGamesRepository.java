package com.casino.PlayedGames.repository;

import com.casino.PlayedGames.model.PlayedGames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayedGamesRepository extends JpaRepository<Long, PlayedGames> {
}
