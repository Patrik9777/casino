package com.casino.services;

import com.casino.models.PlayedGames;
import com.casino.models.User;
import com.casino.repositories.PlayedGamesRepository;
import com.casino.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerGamesService {
    @Autowired
    private PlayedGamesRepository repository;

    public List<PlayedGames> getGames(User user) {
        PlayedGames examplePlayedGames = new PlayedGames();
        examplePlayedGames.user = user;
        return repository.findAll(Example.of(examplePlayedGames));
    }

    public void addGame(User user, String gameName, int bet, int win) {
        PlayedGames foundGame = null;
        for (PlayedGames games : getGames(user)) {
            if (games.gameName.equals(gameName)) {
                foundGame = games;
                break;
            }
        }

        if (foundGame == null) {
            foundGame = new PlayedGames();
            foundGame.gameName = gameName;
            foundGame.betAmount = 0;
            foundGame.winAmount = 0;
        }

        foundGame.betAmount += bet;
        foundGame.winAmount += win;
        repository.save(foundGame);
    }
}
