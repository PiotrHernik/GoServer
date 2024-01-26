package com.example.serwer.Comands;

import com.example.serwer.ClientMessages.Move;
import com.example.serwer.GamePlayer.GamePlayer;
import com.example.serwer.Game_Logic.Game;
import com.example.serwer.MariaDb.GoGameMD;
import com.example.serwer.MariaDb.Movement;
import com.example.serwer.MessagefromServer.MoveInfo;
import com.example.serwer.Service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MoveCommand extends Command
{

    @Autowired
    protected MovementService movementService;
    public MoveCommand (){}

    @Override
    public void executeCommand(Game game, GamePlayer player, GoGameMD goGameMD)
    {
        Move message = (Move) this.clientMessage;

        if (!game.getActualPlayer().equals(player) && !game.isHotseat())
            return;

        if (game.getGameLogic().move(message.getX(), message.getY(), game.getActualPlayer().getNumber())) {
            try {
                Movement movement = new Movement("move", message.getX(), message.getY(), goGameMD.getId(), goGameMD);
                this.movementService.saveMovement(movement);

                int[][] emptyPlaces = game.getGameLogic().removeDeathStones(message.getX(), message.getY());
                game.getActualPlayer().addPoints(emptyPlaces.length);
                MoveInfo moveInfo = new MoveInfo(game.getActualPlayer().getNumber(), true, message.getX(), message.getY(), emptyPlaces);
                game.getActualPlayer().sendMessage(moveInfo);
                game.changeActualPlayer();
                game.getActualPlayer().sendMessage(moveInfo);
                game.setPreviousPass(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                game.getActualPlayer().sendMessage(new MoveInfo(0, false, 0, 0, null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


