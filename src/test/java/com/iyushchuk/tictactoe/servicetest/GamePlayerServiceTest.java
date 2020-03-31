package com.iyushchuk.tictactoe.servicetest;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;
import com.iyushchuk.tictactoe.common.exceptions.NotAuthorizedToPlayGameException;
import com.iyushchuk.tictactoe.common.game.Coordinate;
import com.iyushchuk.tictactoe.domain.entities.Board;
import com.iyushchuk.tictactoe.services.GameService;
import com.iyushchuk.tictactoe.services.impl.GamePlayerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GamePlayerServiceTest {
    @Mock
    private GameService gameService;

    @InjectMocks
    private GamePlayerServiceImpl gamePlayerService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);

        gamePlayerService = new GamePlayerServiceImpl(gameService);
    }

    @Test
    void playGame_InvalidPlayer_ShouldThrowNotAuthorized() throws ApplicationException {

        String gameTag = "game_tag";

        GameDto gameDto = GameDto.builder()
                .tag("game_tag")
                .xPlayer("player_tag")
                .oPlayer("player_tag")
                .build();

        String unauthorizedPlayerTag = "unauthorizedPlayerTag";

        Mockito.when(gameService.getById(gameTag)).thenReturn(gameDto);

        Assertions.assertThrows(NotAuthorizedToPlayGameException.class, () -> {
            GameDto game = gamePlayerService.playGame(unauthorizedPlayerTag, gameTag, new Coordinate(1, 1));
        });
    }

    @Test
    void playGame_ShouldResolveToNextStateDto() throws ApplicationException {

        String gameTag = "game_tag";
        String playerTag = "player_tag";

        GameDto gameDto = GameDto.builder()
                .tag("game_tag")
                .xPlayer("player_tag")
                .oPlayer("player_tag")
                .state(GameState.X_MOVE)
                .placements(new Board().getPlacements())
                .build();

        Mockito.when(gameService.getById(gameTag)).thenReturn(gameDto);

        Mockito.when(gameService.update(eq(gameTag), any(GameDto.class))).thenReturn(gameDto.withState(GameState.O_MOVE));

        GameDto nextStateDto = gamePlayerService.playGame(playerTag, gameTag, new Coordinate(1, 1));

        assertEquals(GameState.O_MOVE, nextStateDto.getState());
    }
}
