package com.iyushchuk.tictactoe.servicetest;


import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;
import com.iyushchuk.tictactoe.common.exceptions.GameDoesNotExistException;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.common.util.GridHelper;
import com.iyushchuk.tictactoe.domain.entities.Board;
import com.iyushchuk.tictactoe.domain.entities.Game;
import com.iyushchuk.tictactoe.domain.entities.Player;
import com.iyushchuk.tictactoe.domain.repositories.GameRepository;
import com.iyushchuk.tictactoe.services.BoardService;
import com.iyushchuk.tictactoe.services.PlayerService;
import com.iyushchuk.tictactoe.services.impl.GameServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerService playerService;

    @Mock
    private BoardService boardService;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);

        gameService = new GameServiceImpl(gameRepository, playerService, boardService);
    }

    @Test
    void getAllGames_ShouldReturnDtoList() {

        List<Game> games = GameState.ENDGAME_STATES.stream().map(state -> {
            Game game = new Game();
            game.setState(state);
            return game;
        }).collect(Collectors.toList());

        Mockito.when(gameRepository.findAll()).thenReturn(games);

        List<GameDto> actualResult = gameService.getAll();

        assertTrue(actualResult.stream().map(GameDto::getState).allMatch(GameState.ENDGAME_STATES::contains));

    }

    @Test
    void getGameByTag_ExistingTag_ShouldReturnGame() throws GameDoesNotExistException {

        Game game = new Game();
        String expectedTag = game.getTag();

        Mockito.when(gameRepository.findGameByTag(expectedTag)).thenReturn(Optional.of(game));

        assertEquals(expectedTag, gameService.findGameByTag(expectedTag).getTag());

    }

    @Test
    void getGameByTag_AbsentTag_ShouldThrowGameNotExists() {

        String absentTag = "non_existing_tag";

        Mockito.when(gameRepository.findGameByTag(absentTag)).thenReturn(Optional.empty());

        Assertions.assertThrows(GameDoesNotExistException.class, () -> {
            Game game = gameService.findGameByTag(absentTag);
        });
    }

    @Test
    void deleteGameByTag_AbsentTag_ShouldThrowGameNotExists() {

        String absentTag = "non_existing_tag";

        Mockito.when(gameRepository.findGameByTag(absentTag)).thenReturn(Optional.empty());

        Assertions.assertThrows(GameDoesNotExistException.class, () -> {
            gameService.delete(absentTag);
        });
    }

    @Test
    void getGameById_ShouldReturnGameDto() throws GameDoesNotExistException {

        Game game = new Game();
        game.setBoard(new Board());
        String gameTag = game.getTag();

        Mockito.when(gameRepository.findGameByTag(gameTag)).thenReturn(Optional.of(game));

        GameDto gameDto = gameService.getById(gameTag);

        assertEquals(game.getTag(), gameDto.getTag());

    }

    @Test
    void create_ShouldReturnGameDto() throws PlayerDoesNotExistException {

        Player player = new Player();
        String playerTag = "player_tag";
        player.setTag(playerTag);

        Game game = new Game();
        game.setBoard(new Board());
        game.setPlayerO(player);
        game.setPlayerX(player);
        String gameTag = game.getTag();

        GameDto expectedDto = GameDto.builder()
                .tag(gameTag)
                .xPlayer(player.getTag())
                .oPlayer(player.getTag())
                .state(GameState.X_MOVE)
                .build();

        Mockito.when(playerService.findPlayerByTag(playerTag)).thenReturn(player);
        Mockito.when(gameRepository.save(any(Game.class))).thenReturn(game);

        GameDto actualDto = gameService.create(expectedDto);

        assertEquals(expectedDto, actualDto);

    }

    @Test
    void update_ShouldReturnGameDto() throws ApplicationException {

        Player player = new Player();
        String playerTag = "player_tag";
        player.setTag(playerTag);

        Game game = new Game();
        game.setBoard(new Board());
        game.setPlayerO(player);
        game.setPlayerX(player);
        String gameTag = game.getTag();

        GameDto expectedDto = GameDto.builder()
                .tag(gameTag)
                .xPlayer(player.getTag())
                .oPlayer(player.getTag())
                .state(GameState.X_MOVE)
                .placements(game.getBoard().getPlacements())
                .fancyBoard(GridHelper.toFancyGrid(game.getBoard().getPlacements(), 10))
                .build();

        Mockito.when(gameRepository.findGameByTag(gameTag)).thenReturn(Optional.of(game));
        Mockito.when(playerService.findPlayerByTag(playerTag)).thenReturn(player);
        Mockito.when(gameRepository.save(any(Game.class))).thenReturn(game);

        GameDto actualDto = gameService.update(gameTag, expectedDto);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    void getGamesFroPlayer_WhenStatesProvided_ShouldReturnDtoList() {

        Player player = new Player();
        String playerTag = "player_tag";
        player.setTag(playerTag);

        List<Game> games = GameState.ENDGAME_STATES.stream().map(state -> {
            Game game = new Game();
            game.setState(state);
            return game;
        }).collect(Collectors.toList());

        Mockito.when(gameRepository.findGamesByPlayerOOrPlayerXAndStateIn(player, player, GameState.ENDGAME_STATES))
                .thenReturn(games);

        List<GameDto> actualResult = gameService.getGamesForPlayer(player, GameState.ENDGAME_STATES);

        assertTrue(actualResult.stream().map(GameDto::getState).allMatch(GameState.ENDGAME_STATES::contains));

    }

    @Test
    void getGamesFroPlayer_WhenStatesNotProvided_ShouldReturnDtoList() {

        Player player = new Player();
        String playerTag = "player_tag";
        player.setTag(playerTag);

        List<Game> games = GameState.ENDGAME_STATES.stream().map(state -> {
            Game game = new Game();
            game.setState(state);
            return game;
        }).collect(Collectors.toList());

        Mockito.when(gameRepository.findGamesByPlayerOOrPlayerX(player, player))
                .thenReturn(games);

        List<GameDto> actualResult = gameService.getGamesForPlayer(player, null);

        assertTrue(actualResult.stream().map(GameDto::getState).allMatch(GameState.ENDGAME_STATES::contains));

    }
}