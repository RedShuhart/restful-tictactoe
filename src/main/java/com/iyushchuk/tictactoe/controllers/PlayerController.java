package com.iyushchuk.tictactoe.controllers;


import com.iyushchuk.tictactoe.common.dto.PlayerDto;
import com.iyushchuk.tictactoe.services.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("players")
public class PlayerController extends CrudController<PlayerDto, String> {

    public PlayerController(CrudService<PlayerDto, String> crudService) {
        super(crudService);
    }
}
