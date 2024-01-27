package com.example.serwer.Comands;

import com.example.serwer.GamePlayer.GamePlayer;
import com.example.serwer.Game_Logic.Game;
import com.example.serwer.MariaDb.GoGameMD;
import com.example.serwer.MariaDb.Movement;
import com.example.serwer.MessagefromServer.EndGame;
import com.example.serwer.MessagefromServer.OpponentPass;
import com.example.serwer.MessagefromServer.Server_ServerMessage;
import com.example.serwer.Service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PassCommand extends Command
{
    @Autowired
    protected MovementService movementService;
    @Override
    public void executeCommand(Game game, GamePlayer player, GoGameMD goGameMD)
    {
        if (!game.getActualPlayer().equals(player)) {
            return;
        }

        Movement movement = new Movement("pass", -1, -1, goGameMD.getId(), goGameMD);
        this.movementService.saveMovement(movement);

        if (game.isPreviousPass()) {
            game.getGameLogic().removeDeathStonesEndGame();

            int player1Points = game.getGameLogic().countPoints(1) + game.getPlayer1().getNumberOfPoints();
            int player2Points = game.getGameLogic().countPoints(2) + game.getPlayer2().getNumberOfPoints();

            try {
                game.getPlayer1().sendMessage( new EndGame(false, 0, player1Points, player2Points));
                game.getPlayer2().sendMessage( new EndGame(false, 0, player1Points, player2Points));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            game.setPreviousPass(true);
            game.changeActualPlayer();
            try {
                game.getActualPlayer().sendMessage(new OpponentPass());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
