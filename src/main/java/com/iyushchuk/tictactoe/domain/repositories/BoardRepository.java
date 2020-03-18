package com.iyushchuk.tictactoe.domain.repositories;

import com.iyushchuk.tictactoe.domain.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
