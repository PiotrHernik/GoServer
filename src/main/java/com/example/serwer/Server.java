package com.example.serwer;
import java.io.IOException;
import java.net.ServerSocket;

import java.sql.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import com.example.serwer.ClientMessages.SetOptions;
import com.example.serwer.GamePlayer.BotPlayer;
import com.example.serwer.GamePlayer.GamePlayer;
import com.example.serwer.GamePlayer.RealPlayer;
import com.example.serwer.Game_Logic.Game;
import com.example.serwer.Game_Logic.Game_Rules;
import com.example.serwer.MariaDb.GoGameMD;
import com.example.serwer.MariaDb.Movement;
import com.example.serwer.MessagefromServer.NewGame;
import com.example.serwer.MessagefromServer.SentGameOptions;
import com.example.serwer.Service.GoGameMDService;
import com.example.serwer.Service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;



@Component
public class Server {

    @Autowired
    GoGameMDService goGameService;

    @Autowired
    MovementService movementService;

    @Autowired
    Game game;

    public Server() {
    }

    @EventListener
    public void prepareGame(ContextRefreshedEvent event) {
        ServerSocket listener = null;
        ServerConnection connection = null;
        GamePlayer player1 = null;
        GamePlayer player2 = null;
        int boardSize = 0;
        boolean ifHotseat = false;
        int gameId = 0;
        Movement[] movements = null;

        try {
            listener = new ServerSocket(59899);
            connection = new ServerConnection(listener);
            TimeUnit.SECONDS.sleep(1);
            connection.sendMessage(new NewGame(this.goGameService.getIdList()));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        player1 = new RealPlayer(connection, 1);

        assert connection != null;
        SetOptions message = (SetOptions) connection.getMessage();
        boardSize = message.getSize();

        switch (message.getMode().toLowerCase())
        {
            case "hotseat" ->
            {
                ifHotseat = true;
                player2 = new RealPlayer(connection, 2);
                try
                {
                    player1.sendMessage(new SentGameOptions(1, message.getSize(), message.getMode()));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            case "singleplayer" ->
            {
                player2 = new BotPlayer(2, boardSize);
                try
                {
                    player1.sendMessage(new SentGameOptions(1, message.getSize(), message.getMode()));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            case "multiplayer" ->
            {
                ServerConnection connection2 = new ServerConnection(listener);
                player2 = new RealPlayer(connection2, 2);
                try
                {
                    player1.sendMessage(new SentGameOptions(1, message.getSize(), message.getMode()));
                    player2.sendMessage(new SentGameOptions(2, message.getSize(), message.getMode()));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            case "load" ->
            {
                ifHotseat = true;
                player2 = new RealPlayer(connection, 2);
                gameId = message.getGameId();
                try
                {
                    player1.sendMessage(new SentGameOptions(1, this.goGameService.getGameById(gameId).getSize(), message.getMode()));
                    boardSize = this.goGameService.getGameById(gameId).getSize();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            default ->
            {
            }

        }

        ExecutorService pool = Executors.newFixedThreadPool(20);
        pool.execute(player1);

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        if (message.getMode().equalsIgnoreCase("Load")) {
            assert player2 != null;
            pool.execute(player2);

            game.setPlayer1(player1);
            game.setPlayer2(player2);
            game.setActualPlayer(player1);
            game.setGameLogic(new Game_Rules(boardSize));
            game.setHotseat(ifHotseat);

            game.setMovements(this.movementService.getMovementsById(gameId));
            player1.setGame(game);
            player2.setGame(game);

        } else {
            assert player2 != null;
            pool.execute(player2);
            GoGameMD goGame = this.saveGame(message);
            goGame = this.goGameService.getGame();
            game.setPlayer1(player1);
            game.setPlayer2(player2);
            game.setActualPlayer(player1);
            game.setGameLogic(new Game_Rules(boardSize));
            game.setHotseat(ifHotseat);
            game.setGoGame(goGame);
            player1.setGame(game);
            player2.setGame(game);
        }
    }

    private GoGameMD saveGame(SetOptions message) {

        GoGameMD goGame = new GoGameMD();
        goGame.setDate(new Date(System.currentTimeMillis()));
        goGame.setType(message.getMode());
        goGame.setSize(message.getSize());

        goGameService.saveGame(goGame);

        return goGame;
    }
}



