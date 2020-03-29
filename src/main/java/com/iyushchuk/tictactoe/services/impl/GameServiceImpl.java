package com.iyushchuk.tictactoe.services.impl;

import com.iyushchuk.tictactoe.common.GameState;
import com.iyushchuk.tictactoe.common.dto.BoardDto;
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
import com.iyushchuk.tictactoe.services.GameService;
import com.iyushchuk.tictactoe.services.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;
    private final BoardService boardService;

    public GameServiceImpl(
            GameRepository gameRepository,
            PlayerService playerService,
            BoardService boardService
    ) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
        this.boardService = boardService;
    }

    @Override
    public List<GameDto> getAll() {
        return gameRepository.findAll()
                .stream()
                .map(this::toGameDto)
                .collect(Collectors.toList());
    }

    @Override
    public GameDto getById(String tag) throws GameDoesNotExistException {
        Game game = findGameByTag(tag);

        GameDto dto = toGameDto(game);

        enrichWithGrid(dto, game.getBoard().getPlacements());

        return dto;
    }

    @Override
    public GameDto create(GameDto dto) throws PlayerDoesNotExistException {

        Player xPlayer = playerService.findPlayerByTag(dto.getXPlayer());

        Player oPlayer = playerService.findPlayerByTag(dto.getOPlayer());

        Game game = new Game();

        game.setPlayerO(oPlayer);
        game.setPlayerX(xPlayer);
        game.setBoard(new Board());

        return toGameDto(gameRepository.save(game));
    }

    @Override
    public GameDto update(String tag, GameDto dto) throws ApplicationException {

        Game game = findGameByTag(tag);

        Player xPlayer = playerService.findPlayerByTag(dto.getXPlayer());

        Player oPlayer = playerService.findPlayerByTag(dto.getOPlayer());

        game.setPlayerX(xPlayer);
        game.setPlayerO(oPlayer);
        game.setState(dto.getState());

        boardService.update(game.getId(), new BoardDto(dto.getPlacements()));

        Game updatedGame = gameRepository.save(game);

        GameDto updatedGameDto = toGameDto(updatedGame);
        enrichWithGrid(updatedGameDto, game.getBoard().getPlacements());

        return updatedGameDto;
    }

    @Override
    public void delete(String tag) throws GameDoesNotExistException {
        Game game = gameRepository.findGameByTag(tag)
                .orElseThrow(() -> new GameDoesNotExistException(tag));

        gameRepository.delete(game);
    }

    @Override
    public List<GameDto> getGamesForPlayer(Player player, List<GameState> states) {

        List<Game> games = states != null
                ? gameRepository.findGamesByPlayerOOrPlayerXAndStateIn(player, player, states)
                : gameRepository.findGamesByPlayerOOrPlayerX(player, player);

            return games.stream()
                    .map(this::toGameDto)
                    .distinct()
                    .collect(Collectors.toList());

    }

    public Game findGameByTag(String tag) throws GameDoesNotExistException {
        return gameRepository.findGameByTag(tag)
                .orElseThrow(() -> new GameDoesNotExistException(tag));
    }


    private void enrichWithGrid(GameDto dto, String placements) {
        dto.setPlacements(placements);
        dto.setFancyBoard(GridHelper.toFancyGrid(placements, 10));
    }


    private GameDto toGameDto(Game entity) {
        return GameDto.builder()
                .xPlayer(entity.getPlayerX() != null ? entity.getPlayerX().getTag() : "")
                .oPlayer(entity.getPlayerO() != null ? entity.getPlayerO().getTag() : "")
                .tag(entity.getTag())
                .state(entity.getState())
                .build();
    }
}
