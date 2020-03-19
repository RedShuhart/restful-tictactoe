package com.iyushchuk.tictactoe.services.impl;

import com.iyushchuk.tictactoe.common.dto.BoardDto;
import com.iyushchuk.tictactoe.common.exceptions.UnsupportedGridException;
import com.iyushchuk.tictactoe.domain.entities.Board;
import com.iyushchuk.tictactoe.domain.repositories.BoardRepository;
import com.iyushchuk.tictactoe.services.BoardService;
import com.iyushchuk.tictactoe.services.converters.BoardConverter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static com.iyushchuk.tictactoe.common.util.GridHelper.isValidGrid;

@Service
public class BoardServiceImpl implements BoardService {


    private final BoardRepository boardRepository;
    private final BoardConverter converter;

    public BoardServiceImpl(BoardRepository boardRepository, BoardConverter converter) {
        this.boardRepository = boardRepository;
        this.converter = converter;
    }

    @Override
    public List<BoardDto> getAll() {
        return boardRepository.findAll()
                .stream()
                .map(board -> new BoardDto(board.getPlacements()))
                .collect(Collectors.toList());
    }

    @Override
    public BoardDto getById(Long id) {
        return boardRepository.findById(id)
                .map(board -> new BoardDto(board.getPlacements()))
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public BoardDto create(BoardDto dto) throws UnsupportedGridException {

        if (!isValidGrid(dto.getGrid())) {
            throw new UnsupportedGridException();
        }

        return new BoardDto(dto.getGrid());
    }

    @Override
    public BoardDto update(Long id, BoardDto dto) throws UnsupportedGridException {

        if (!isValidGrid(dto.getGrid())) {
            throw new UnsupportedGridException();
        }

        Board board = boardRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        converter.updateEntity(board, dto);

        Board updatedBoard = boardRepository.save(board);

        return converter.fromEntity(updatedBoard);
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

}
