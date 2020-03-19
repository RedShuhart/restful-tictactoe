package com.iyushchuk.tictactoe.services.converters;

import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.domain.entities.Game;
import org.springframework.stereotype.Service;

@Service
public class GameConverter implements EntityDtoConverter<Game, GameDto> {

    // DONT USE
    @Override
    @Deprecated
    public Game fromDto(GameDto dto) {
        return new Game();
    }

    @Override
    public GameDto fromEntity(Game entity) {
        return GameDto.builder()
                .xPlayer(entity.getXPlayer() != null ? entity.getXPlayer().getTag() : "")
                .oPlayer(entity.getOPlayer() != null ? entity.getOPlayer().getTag() : "")
                .tag(entity.getTag())
                .state(entity.getState())
                .build();
    }

    // DONT USE
    @Override
    @Deprecated
    public void updateEntity(Game entity, GameDto dto) {
        return;
    }
}
