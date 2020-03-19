package com.iyushchuk.tictactoe.services.impl;

import com.iyushchuk.tictactoe.common.dto.PlayerDto;
import com.iyushchuk.tictactoe.common.exceptions.PlayerAlreadyExistsException;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.domain.entities.Player;
import com.iyushchuk.tictactoe.domain.repositories.PlayerRepository;
import com.iyushchuk.tictactoe.services.PlayerService;
import com.iyushchuk.tictactoe.services.converters.PlayerConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerConverter converter;

    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerConverter converter) {
        this.playerRepository = playerRepository;
        this.converter = converter;
    }

    @Override
    public List<PlayerDto> getAll() {

        return converter.fromEntities(playerRepository.findAll());
    }

    @Override
    public PlayerDto getById(String tag) throws PlayerDoesNotExistException {

        return converter.fromEntity(findPlayerByTag(tag));
    }

    @Override
    public PlayerDto create(PlayerDto dto) throws PlayerAlreadyExistsException {

        checkUniqueness(dto.getTag());

        Player player = playerRepository.save(converter.fromDto(dto));

        return converter.fromEntity(player);
    }

    @Override
    public PlayerDto update(String tag, PlayerDto dto) throws PlayerDoesNotExistException {

        Player player = findPlayerByTag(tag);

        converter.updateEntity(player, dto);

        Player updatedPlayer = playerRepository.save(player);

        return converter.fromEntity(updatedPlayer);
    }

    @Override
    public void delete(String tag) throws PlayerDoesNotExistException {

        Player player = findPlayerByTag(tag);

        playerRepository.delete(player);
    }

    public Player findPlayerByTag(String tag) throws PlayerDoesNotExistException {

        return playerRepository.findPlayerByTag(tag)
                .orElseThrow(() -> new PlayerDoesNotExistException(tag));
    }

    private void checkUniqueness(String tag) throws PlayerAlreadyExistsException {

        Optional<Player> player = playerRepository.findPlayerByTag(tag);

        if (player.isPresent()) {
            throw new PlayerAlreadyExistsException(tag);
        }
    }
}
