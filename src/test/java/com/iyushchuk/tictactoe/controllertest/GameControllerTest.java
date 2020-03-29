package com.iyushchuk.tictactoe.controllertest;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.dto.PlayerDto;
import com.iyushchuk.tictactoe.common.exceptions.GameDoesNotExistException;
import com.iyushchuk.tictactoe.common.exceptions.NotAuthorizedToPlayGameException;
import com.iyushchuk.tictactoe.common.exceptions.PlayerAlreadyExistsException;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.common.game.Coordinate;
import com.iyushchuk.tictactoe.controllers.ApplicationExceptionHandlerController;
import com.iyushchuk.tictactoe.controllers.GameController;
import com.iyushchuk.tictactoe.services.CrudService;
import com.iyushchuk.tictactoe.services.GamePlayerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.iyushchuk.tictactoe.controllertest.ControllerTestUtil.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameControllerTest {

    @Mock
    private CrudService<GameDto, String> crudService;

    @Mock
    private GamePlayerService gamePlayerService;

    @InjectMocks
    private GameController gameController;

    @InjectMocks
    private ApplicationExceptionHandlerController exceptionHandlerController;

    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(gameController)
                .setControllerAdvice(exceptionHandlerController)
                .build();
    }

    @Test
    void getListOfGames_ShouldReturnOkAndDtoList() throws Exception {
        GameDto game1 = GameDto.builder().xPlayer("player1").state(GameState.DRAW).build();
        GameDto game2 = GameDto.builder().xPlayer("player1").state(GameState.X_WON).build();

        List<GameDto> games = List.of(game1, game2);

        when(crudService.getAll()).thenReturn(games);

        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].xplayer", is("player1")))
                .andExpect(jsonPath("$[0].state", is("DRAW")))
                .andExpect(jsonPath("$[1].xplayer", is("player1")))
                .andExpect(jsonPath("$[1].state", is("X_WON")));
    }

    @Test
    void getGame_ShouldReturnOkAndDto() throws Exception {
        GameDto game = GameDto.builder().xPlayer("player1").state(GameState.DRAW).build();

        when(crudService.getById("tag1")).thenReturn(game);

        mockMvc.perform(get("/games/{id}", "tag1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.xplayer", is("player1")))
                .andExpect(jsonPath("$.state", is("DRAW")));
    }

    @Test
    void getGame_ShouldThrowNotExists() throws Exception {

        when(crudService.getById("non_existing_tag")).thenThrow(new GameDoesNotExistException("non_existing_tag"));

        mockMvc.perform(get("/games/{id}", "non_existing_tag"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createGame_ShouldReturnOKAndDto() throws Exception {

        GameDto game = GameDto.builder().xPlayer("player1").oPlayer("player2").build();

        when(crudService.create(game)).thenReturn(game);

        mockMvc.perform(
                post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(game))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.xplayer", is("player1")))
                .andExpect(jsonPath("$.oplayer", is("player2")));
    }

    @Test
    void playTurn_ShouldReturnNextState() throws Exception {
        Coordinate move = new Coordinate(1, 1);

        GameDto game = GameDto.builder().xPlayer("player_tag").tag("game_tag").state(GameState.DRAW).build();

        when(gamePlayerService.playGame("player_tag", "game_tag", move)).thenReturn(game);

        mockMvc.perform(
                patch("/games/{tag}", "game_tag")
                        .header("playerTag", "player_tag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(move))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tag", is("game_tag")))
                .andExpect(jsonPath("$.state", is("DRAW")))
                .andExpect(jsonPath("$.xplayer", is("player_tag")));
    }


    @Test
    void playTurn_ShouldThrowNotAuthorized() throws Exception {

        Coordinate move = new Coordinate(1, 1);

        when(gamePlayerService.playGame("wrong_player", "game_tag", move))
                .thenThrow(new NotAuthorizedToPlayGameException("wrong_player", "game_tag"));

        mockMvc.perform(
                patch("/games/{tag}", "game_tag")
                        .header("playerTag", "wrong_player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(move))
        )
                .andExpect(status().isUnauthorized());
    }


    @Test
    void deleteGame_ShouldReturnOk() throws Exception {

        doNothing().when(crudService).delete("tag");

        mockMvc.perform(delete("/games/{id}", "tag"))
                .andExpect(status().isOk());
    }


}