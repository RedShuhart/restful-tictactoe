package com.iyushchuk.tictactoe.domain.repositories;

import com.iyushchuk.tictactoe.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findGameByTag(String tag);
}
