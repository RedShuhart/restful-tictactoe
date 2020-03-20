package com.iyushchuk.tictactoe.services;

import com.iyushchuk.tictactoe.common.dto.PlayerDto;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.domain.entities.Player;


public interface PlayerService extends CrudService<PlayerDto, String> {

    Player findPlayerByTag(String tag) throws PlayerDoesNotExistException;

}
