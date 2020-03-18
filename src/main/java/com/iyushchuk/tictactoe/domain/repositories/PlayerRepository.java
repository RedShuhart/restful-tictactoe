package com.iyushchuk.tictactoe.domain.repositories;

import com.iyushchuk.tictactoe.domain.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findPlayerByTag(String tag);

    void deletePlayerByTag(String tag);
}
