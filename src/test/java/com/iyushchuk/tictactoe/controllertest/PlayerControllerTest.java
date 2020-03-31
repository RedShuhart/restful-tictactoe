package com.iyushchuk.tictactoe.controllertest;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.GameDto;
import com.iyushchuk.tictactoe.common.dto.PlayerDto;
import com.iyushchuk.tictactoe.common.exceptions.PlayerAlreadyExistsException;
import com.iyushchuk.tictactoe.common.exceptions.PlayerDoesNotExistException;
import com.iyushchuk.tictactoe.controllers.ApplicationExceptionHandlerController;
import com.iyushchuk.tictactoe.controllers.PlayerController;
import com.iyushchuk.tictactoe.services.CrudService;
import com.iyushchuk.tictactoe.services.TicTacToeService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlayerControllerTest {

    @Mock
    private CrudService<PlayerDto, String> crudService;

    @Mock
    private TicTacToeService ticTacToeService;

    @InjectMocks
    private PlayerController playerController;

    @InjectMocks
    private ApplicationExceptionHandlerController exceptionHandlerController;

    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(playerController)
                .setControllerAdvice(exceptionHandlerController)
                .build();
    }

    @Test
    void getPlayers_ShouldReturnOkAndDtoList() throws Exception {
        List<PlayerDto> players = List.of(new PlayerDto("tag1", "name1"), new PlayerDto("tag2", "name2"));

        when(crudService.getAll()).thenReturn(players);

        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tag", is("tag1")))
                .andExpect(jsonPath("$[0].name", is("name1")))
                .andExpect(jsonPath("$[1].tag", is("tag2")))
                .andExpect(jsonPath("$[1].name", is("name2")));
    }

    @Test
    void getPlayerById_ShouldReturnOkAndDto() throws Exception {
        PlayerDto player = new PlayerDto("tag1", "name1");

        when(crudService.getById("tag1")).thenReturn(player);

        mockMvc.perform(get("/players/{id}", "tag1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tag", is("tag1")))
                .andExpect(jsonPath("$.name", is("name1")));
    }

    @Test
    void getPlayerById_ShouldThrowNotExists() throws Exception {

        when(crudService.getById("tag3")).thenThrow(new PlayerDoesNotExistException("tag3"));

        mockMvc.perform(get("/players/{id}", "tag3"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPlayer_ShouldReturnOkAndDto() throws Exception {

        PlayerDto playerDto = new PlayerDto("tag4", "name4");

        when(crudService.create(playerDto)).thenReturn(playerDto);

        mockMvc.perform(
                post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(playerDto))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tag", is("tag4")))
                .andExpect(jsonPath("$.name", is("name4")));
    }

    @Test
    void updatePlayer_ShouldReturnOkAndDto() throws Exception {
        PlayerDto playerDto = new PlayerDto("tag7", "name7");

        when(crudService.update("tag7", playerDto)).thenReturn(playerDto);

        mockMvc.perform(
                put("/players/{id}", "tag7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(playerDto))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tag", is("tag7")))
                .andExpect(jsonPath("$.name", is("name7")));
    }


    @Test
    void deletePlayer_ShouldReturnOK() throws Exception {

        doNothing().when(crudService).delete("tag8");

        mockMvc.perform(delete("/players/{id}", "tag8"))
                .andExpect(status().isOk());
    }

    @Test
    void createPlayer_ShouldThrowPlayerAlreadyExists() throws Exception {

        PlayerDto playerDto = new PlayerDto("tag5", "name5");

        when(crudService.create(playerDto)).thenThrow(new PlayerAlreadyExistsException("tag5"));

        mockMvc.perform(
                post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(playerDto))
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPlayersGames_ShouldReturnDtoList() throws Exception {

        GameDto game1 = GameDto.builder().xPlayer("tag6").state(GameState.DRAW).build();
        GameDto game2 = GameDto.builder().xPlayer("tag6").state(GameState.DRAW).build();

        List<GameDto> games = List.of(game1, game2);

        List<GameState> gameStates = List.of(GameState.DRAW);

        when(ticTacToeService.findPlayersGames("tag6", gameStates)).thenReturn(games);

        mockMvc.perform(
                get("/players/{id}/games", "tag6")
                        .param("state", GameState.DRAW.toString())
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].xplayer", is("tag6")))
                .andExpect(jsonPath("$[0].state", is("DRAW")))
                .andExpect(jsonPath("$[1].xplayer", is("tag6")))
                .andExpect(jsonPath("$[1].state", is("DRAW")));
    }

}
