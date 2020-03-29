package com.iyushchuk.tictactoe.controllertest;

import com.iyushchuk.tictactoe.common.dto.PlayerDto;
import com.iyushchuk.tictactoe.controllers.ApplicationExceptionHandlerController;
import com.iyushchuk.tictactoe.controllers.GameController;
import com.iyushchuk.tictactoe.services.CrudService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameControllerTest {

    @Mock
    private CrudService<PlayerDto, String> crudService;

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

}