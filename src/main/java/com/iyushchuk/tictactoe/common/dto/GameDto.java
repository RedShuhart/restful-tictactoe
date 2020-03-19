package com.iyushchuk.tictactoe.common.dto;

import com.iyushchuk.tictactoe.common.GameState;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GameDto implements IDto {

    private String xPlayer;

    private String oPlayer;

    private String tag;

    private GameState state;

    private String board;

}
