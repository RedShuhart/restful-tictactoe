package com.iyushchuk.tictactoe.services.converters;

import com.iyushchuk.tictactoe.common.dto.IDto;
import com.iyushchuk.tictactoe.domain.entities.IEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface EntityDtoConverter<Entity extends IEntity, Dto extends IDto>  {

    Entity fromDto(Dto dto);

    Dto fromEntity(Entity entity);

    void updateEntity(Entity entity, Dto dto);

    default List<Dto> fromEntities(Collection<Entity> entities) {
        return entities.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    default List<Entity> fromDtos(Collection<Dto> entities) {
        return entities.stream().map(this::fromDto).collect(Collectors.toList());
    }

}