package com.iyushchuk.tictactoe.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BoardDto implements IDto {

    private String grid;

}
