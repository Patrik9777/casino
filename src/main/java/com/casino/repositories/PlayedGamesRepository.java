package com.casino.repositories;

import com.casino.models.PlayedGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayedGamesRepository extends JpaRepository<PlayedGames, Integer> {
}
