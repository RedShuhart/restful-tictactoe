package com.iyushchuk.tictactoe.services.converters;

import com.iyushchuk.tictactoe.common.dto.BoardDto;
import com.iyushchuk.tictactoe.domain.entities.Board;
import org.springframework.stereotype.Service;

@Service
public class BoardConverter implements EntityDtoConverter<Board, BoardDto> {
    @Override
    public Board fromDto(BoardDto dto) {
        return new Board(dto.getGrid());
    }

    @Override
    public BoardDto fromEntity(Board entity) {
        return new BoardDto(entity.getPlacements());
    }

    @Override
    public void updateEntity(Board entity, BoardDto dto) {
        entity.setPlacements(dto.getGrid());
    }
}
