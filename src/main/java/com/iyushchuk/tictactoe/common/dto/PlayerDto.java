package com.iyushchuk.tictactoe.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerDto implements IDto {

    private String tag;

    private String name;
}
