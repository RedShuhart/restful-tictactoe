package com.iyushchuk.tictactoe.servicetest;

import com.iyushchuk.tictactoe.common.dto.PlayerDto;
import com.iyushchuk.tictactoe.common.exceptions.PlayerAlreadyExistsException;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.domain.entities.Player;
import com.iyushchuk.tictactoe.domain.repositories.PlayerRepository;
import com.iyushchuk.tictactoe.services.converters.PlayerConverter;
import com.iyushchuk.tictactoe.services.impl.PlayerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerConverter converter;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        converter = new PlayerConverter();
        playerService = new PlayerServiceImpl(playerRepository, converter);
    }

    @Test
    public void getAll_ShouldReturnDtoList() {

        List<Player> players = List.of(
                new Player("tag1", "name1"),
                new Player("tag2", "name2")
        );

        Mockito.when(playerRepository.findAll()).thenReturn(players);

        List<PlayerDto> actualResult = playerService.getAll();

        List<PlayerDto> expectedResult = List.of(
                new PlayerDto("tag1", "name1"),
                new PlayerDto("tag2", "name2")
        );

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getById_ExistingTag_ShouldReturnDto() throws PlayerDoesNotExistException {

        Player player = new Player("tag1", "name1");

        Mockito.when(playerRepository.findPlayerByTag("tag1")).thenReturn(Optional.of(player));

        PlayerDto expectedResult = new PlayerDto("tag1", "name1");

        PlayerDto actualResult = playerService.getById("tag1");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getById_AbsentTag_ShouldThrowPlayerNotExists() {

        String nonExistingTag = "nonExistingTag";

        Mockito.when(playerRepository.findPlayerByTag(nonExistingTag)).thenReturn(Optional.empty());

        Assertions.assertThrows(PlayerDoesNotExistException.class, () -> {
            PlayerDto player = playerService.getById(nonExistingTag);
        });
    }

    @Test
    public void create_ShouldReturnDto() throws PlayerAlreadyExistsException {

        Player player = new Player("tag1", "name1");

        PlayerDto expectedResult = new PlayerDto("tag1", "name1");

        Mockito.when(playerRepository.save(any(Player.class))).thenReturn(player);

        PlayerDto actualResult = playerService.create(expectedResult);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void create_ExistingTag_ShouldThrowAlreadyExists() {
        String existingTag = "existing_tag";

        PlayerDto existingPlayer = new PlayerDto(existingTag, "name1");

        Player player = new Player(existingTag, "name1");

        Mockito.when(playerRepository.findPlayerByTag(existingTag)).thenReturn(Optional.of(player));

        Assertions.assertThrows(PlayerAlreadyExistsException.class, () -> {
            playerService.create(existingPlayer);
        });
    }

}
