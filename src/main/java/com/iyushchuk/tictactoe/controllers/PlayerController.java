package com.iyushchuk.tictactoe.controllers;


import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.dto.PlayerDto;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.services.CrudService;
import com.iyushchuk.tictactoe.services.TicTacToeService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("players")
public class PlayerController extends CrudController<PlayerDto, String> {

    private final TicTacToeService ticTacToeService;

    public PlayerController(CrudService<PlayerDto, String> crudService, TicTacToeService ticTacToeService) {
        super(crudService);
        this.ticTacToeService = ticTacToeService;
    }

    @GetMapping("/{tag}/games")
    public List<GameDto> getPlayersGames(
            @PathVariable(value = "tag") String playerTag,
            @RequestParam(value = "state", required = false) List<GameState> gameStates
    ) throws PlayerDoesNotExistException {
        return ticTacToeService.findPlayersGames(playerTag, gameStates);
    }
}
