package com.example.serwer.Comands;


import com.example.serwer.ClientMessages.Move;
import com.example.serwer.GamePlayer.GamePlayer;
import com.example.serwer.Game_Logic.Game;
import com.example.serwer.MariaDb.GoGameMD;
import com.example.serwer.MariaDb.Movement;
import com.example.serwer.MessagefromServer.EndGame;
import com.example.serwer.MessagefromServer.MoveInfo;
import com.example.serwer.MessagefromServer.OpponentPass;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLOutput;

@Component
public class NextCommand extends Command {

    @Override
    public void executeCommand(Game game, GamePlayer player, GoGameMD goGame) {
        System.out.println("Jestem w execute NextCommand");

        Movement movement = game.getNextMovement();

        System.out.println("Tralalaa");
        if (movement.getType().contentEquals("move")) {
            if (!game.getActualPlayer().equals(player) && !game.isHotseat())
                return;

            if (game.getGameLogic().move(movement.getX(), movement.getY(), game.getActualPlayer().getNumber())) {
                try {

                    int[][] emptyPlaces = game.getGameLogic().removeDeathStones(movement.getX(), movement.getY());
                    game.getActualPlayer().addPoints(emptyPlaces.length);
                    MoveInfo moveInfo = new MoveInfo(game.getActualPlayer().getNumber(), true, movement.getX(), movement.getY(), emptyPlaces);
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

        } else if (movement.getType().contentEquals("pass")) {

            if (game.isPreviousPass()) {
                game.getGameLogic().removeDeathStonesEndGame();

                int player1Points = game.getGameLogic().countPoints(1) + game.getPlayer1().getNumberOfPoints();
                int player2Points = game.getGameLogic().countPoints(2) + game.getPlayer2().getNumberOfPoints();

                try {
                    game.getPlayer1().sendMessage(new EndGame(false, 0, player1Points, player2Points));
                    game.getPlayer2().sendMessage(new EndGame(false, 0, player1Points, player2Points));
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

        } else if (movement.getType().contentEquals("surrender")) {
            game.getGameLogic().removeDeathStonesEndGame();

            int player1Points = game.getGameLogic().countPoints(1) + game.getPlayer1().getNumberOfPoints();
            int player2Points = game.getGameLogic().countPoints(2) + game.getPlayer2().getNumberOfPoints();

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

}