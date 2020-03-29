package com.iyushchuk.tictactoe.services.converters;

import com.iyushchuk.tictactoe.common.dto.IDto;
import com.iyushchuk.tictactoe.domain.entities.IEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface EntityDtoConverter<ENTITY extends IEntity, DTO extends IDto>  {

    ENTITY fromDto(DTO dto);

    DTO fromEntity(ENTITY entity);

    void updateEntity(ENTITY entity, DTO dto);

    default List<DTO> fromEntities(Collection<ENTITY> entities) {
        return entities.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    default List<ENTITY> fromDtos(Collection<DTO> entities) {
        return entities.stream().map(this::fromDto).collect(Collectors.toList());
    }

}