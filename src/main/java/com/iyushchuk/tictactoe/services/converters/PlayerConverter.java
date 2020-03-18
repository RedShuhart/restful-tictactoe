package com.iyushchuk.tictactoe.services.converters;

import com.iyushchuk.tictactoe.common.dto.PlayerDto;
import com.iyushchuk.tictactoe.domain.entities.Player;
import org.springframework.stereotype.Service;

@Service
public class PlayerConverter implements EntityDtoConverter<Player, PlayerDto> {
    @Override
    public Player fromDto(PlayerDto dto) {
        return new Player(dto.getTag(), dto.getName());
    }

    @Override
    public PlayerDto fromEntity(Player entity) {
        return PlayerDto.builder()
                .tag(entity.getTag())
                .name(entity.getName())
                .build();
    }

    @Override
    public void updateEntity(Player entity, PlayerDto dto) {
        entity.setName(dto.getName());
    }
}
