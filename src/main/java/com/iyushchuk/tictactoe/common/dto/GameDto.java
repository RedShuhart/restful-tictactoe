package com.iyushchuk.tictactoe.common.dto;

import com.iyushchuk.tictactoe.common.GameState;
import lombok.Builder;
import lombok.Data;
import lombok.With;


@Data
@Builder
@With
public class GameDto implements IDto {

    private String xPlayer;

    private String oPlayer;

    private String tag;

    private GameState state;

    private String placements;

    private String fancyBoard;

}
