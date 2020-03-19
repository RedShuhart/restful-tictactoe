package com.iyushchuk.tictactoe.controllers;

import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;
import com.iyushchuk.tictactoe.common.game.Coordinate;
import com.iyushchuk.tictactoe.services.CrudService;
import com.iyushchuk.tictactoe.services.GamePlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("games")
public class GameController extends CrudController<GameDto, String> {

    private final GamePlayerService gamePlayerService;

    public GameController(CrudService<GameDto, String> crudService, GamePlayerService gamePlayerService) {
        super(crudService);
        this.gamePlayerService = gamePlayerService;
    }

    @PatchMapping("/{tag}")
    public GameDto playTurn(
            @RequestHeader(value = "playerTag") String playerTag,
            @PathVariable(value = "tag")  String gameTag,
            @RequestBody Coordinate turnCoordinate) throws ApplicationException {
        return gamePlayerService.playGame(playerTag, gameTag, turnCoordinate);
    }
}
