package com.iyushchuk.tictactoe.services.impl;

import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;
import com.iyushchuk.tictactoe.common.exceptions.NotAuthorizedToPlayGameException;
import com.iyushchuk.tictactoe.common.game.Coordinate;
import com.iyushchuk.tictactoe.common.game.GameResolver;
import com.iyushchuk.tictactoe.services.GamePlayerService;
import com.iyushchuk.tictactoe.services.GameService;
import org.springframework.stereotype.Service;

@Service
public class GamePlayerServiceImpl implements GamePlayerService {

    private final GameService gameService;

    public GamePlayerServiceImpl(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public GameDto playGame(String playerTag, String gameTag, Coordinate move) throws ApplicationException {

        GameDto game = gameService.getById(gameTag);

        checkAuthorization(playerTag, game);

        GameResolver gameResolver = new GameResolver(game, Coordinate.fromHumanCoordinates(move));

        GameDto newSate = gameResolver.getNextState();


        return gameService.update(gameTag, newSate);
    }

    private void checkAuthorization(String playerTag, GameDto game) throws NotAuthorizedToPlayGameException {
        if (!playerTag.equals(game.getXPlayer()) && !playerTag.equals(game.getOPlayer())) {
            throw new NotAuthorizedToPlayGameException(playerTag, game.getTag());
        }
    }
}
