package com.iyushchuk.tictactoe.services;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.domain.entities.Player;

import java.util.List;

public interface GameService extends CrudService<GameDto, String> {
    List<GameDto> getGamesForPlayer(Player player, List<GameState> states);
}
