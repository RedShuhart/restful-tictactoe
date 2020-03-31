package com.iyushchuk.tictactoe.servicetest;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.domain.entities.Player;
import com.iyushchuk.tictactoe.services.GameService;
import com.iyushchuk.tictactoe.services.PlayerService;
import com.iyushchuk.tictactoe.services.impl.TicTacToeServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicTacToeServiceTest {
    @Mock
    private GameService gameService;
    @Mock
    private PlayerService playerService;

    @InjectMocks
    private TicTacToeServiceImpl ticTacToeService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);

        ticTacToeService = new TicTacToeServiceImpl(playerService, gameService);
    }


    @Test
    void findPlayersGames_ShouldReturnDtoList() throws PlayerDoesNotExistException {
        GameDto game1 = GameDto.builder().tag("game1").state(GameState.DRAW).build();
        GameDto game2 = GameDto.builder().tag("game2").state(GameState.X_WON).build();

        List<GameDto> expectedGames = List.of(game1, game2);

        String playerTag = "player_tag";

        Player player = new Player();
        player.setTag(playerTag);

        Mockito.when(playerService.findPlayerByTag(playerTag)).thenReturn(player);
        Mockito.when(gameService.getGamesForPlayer(player, GameState.ENDGAME_STATES)).thenReturn(expectedGames);

        List<GameDto> actualGames = ticTacToeService.findPlayersGames(playerTag, GameState.ENDGAME_STATES);

        assertEquals(expectedGames, actualGames);
    }
}
