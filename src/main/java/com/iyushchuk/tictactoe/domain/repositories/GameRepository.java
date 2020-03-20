package com.iyushchuk.tictactoe.domain.repositories;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.domain.entities.Game;
import com.iyushchuk.tictactoe.domain.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findGameByTag(String tag);

    List<Game> findGamesByPlayerOOrPlayerX(Player oPlayer, Player xPlayer);

    List<Game> findGamesByPlayerOOrPlayerXAndStateIn(Player oPlayer, Player xPlayer, List<GameState> states);
}
