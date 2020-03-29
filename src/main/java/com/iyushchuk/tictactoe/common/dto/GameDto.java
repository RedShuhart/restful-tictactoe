package com.iyushchuk.tictactoe.common.dto;

import com.iyushchuk.tictactoe.common.GameState;
import lombok.*;


@Data
@Builder
@With
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GameDto implements IDto {

    private String xPlayer;

    private String oPlayer;

    private String tag;

    private GameState state;

    private String placements;

    private String fancyBoard;

}
