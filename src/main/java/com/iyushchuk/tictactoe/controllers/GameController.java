package com.iyushchuk.tictactoe.controllers;

import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.services.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games")
public class GameController extends CrudController<GameDto, String> {

    public GameController(CrudService<GameDto, String> crudService) {
        super(crudService);
    }
}
