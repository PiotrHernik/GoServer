package com.example.serwer.Comands;

import com.example.serwer.GamePlayer.GamePlayer;
import com.example.serwer.Game_Logic.Game;
import com.example.serwer.MariaDb.GoGameMD;
import com.example.serwer.MariaDb.Movement;
import com.example.serwer.MessagefromServer.EndGame;
import com.example.serwer.Service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class SurrenderCommand extends Command {

    @Autowired
    protected MovementService movementService;

    public SurrenderCommand() {}

    @Override
    public void executeCommand(Game game, GamePlayer player, GoGameMD goGame) {
        game.getGameLogic().removeDeathStonesEndGame();

        Movement movement = new Movement("surrender", -1, -1, goGame.getId(), goGame);
        this.movementService.saveMovement(movement);

        int player1Points = game.getGameLogic().countPoints(1) + game.getPlayer1().getNumberOfPoints();
        if (player1Points >= (goGame.getSize() * goGame.getSize())){
            player1Points -= (goGame.getSize() * goGame.getSize());
        }
        int player2Points = game.getGameLogic().countPoints(2) + game.getPlayer2().getNumberOfPoints();
        if (player2Points >= (goGame.getSize() * goGame.getSize())){
            player2Points -= (goGame.getSize() * goGame.getSize());
        }

        try {
            if (game.getPlayer1().equals(player)) {
                EndGame endGame = new EndGame(true, 1, player1Points, player2Points);
                game.getPlayer1().sendMessage(endGame);
                game.getPlayer2().sendMessage(endGame);
            } else {
                EndGame endGame = new EndGame(true, 2, player1Points, player2Points);
                game.getPlayer1().sendMessage(endGame);
                game.getPlayer2().sendMessage(endGame);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}