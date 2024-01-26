package com.example.serwer.Game_Logic;

import com.example.serwer.ClientMessages.Server_ClientMessage;
import com.example.serwer.Comands.Command;
import com.example.serwer.Comands.CommandFactory;
import com.example.serwer.GamePlayer.GamePlayer;
import com.example.serwer.MariaDb.GoGameMD;
import com.example.serwer.MariaDb.Movement;
import org.springframework.stereotype.Component;


@Component
public class Game
{

    CommandFactory commandFactory;

    private GamePlayer player1;
    private GamePlayer player2;
    private GamePlayer actualPlayer;
    private boolean previousPass;
    private Game_Rules game_rules;
    private boolean hotseat;
    private GoGameMD goGame;
    private Movement[] movements;
    private int movementsIndex;

    public Game() {
        this.previousPass = false;
        this.movementsIndex = 0;
    }

    public synchronized void getMessage(Server_ClientMessage clientMessage, GamePlayer player) {
        Command command = this.commandFactory.getCommand(
                clientMessage);
        if (command != null)
            command.executeCommand(this, player, goGame);
    }

    public void changeActualPlayer() {
        if (this.actualPlayer.equals(this.player1)) {
            this.actualPlayer = this.player2;
        } else {
            this.actualPlayer = this.player1;
        }
    }

    public GamePlayer getPlayer1() {
        return player1;
    }

    public GamePlayer getPlayer2() {
        return player2;
    }

    public GamePlayer getActualPlayer() {
        return actualPlayer;
    }

    public boolean isPreviousPass() {
        return this.previousPass;
    }

    public void setPreviousPass(boolean previousPass) {
        this.previousPass = previousPass;
    }

    public Game_Rules getGameLogic() {
        return this.game_rules;
    }

    public boolean isHotseat() {
        return this.hotseat;
    }

    public void setHotseat(boolean hotseat) {
        this.hotseat = hotseat;
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public void setCommandFactory(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }
    public void setPlayer1(GamePlayer player1) {
        this.player1 = player1;
    }

    public void setPlayer2(GamePlayer player2) {
        this.player2 = player2;
    }

    public void setActualPlayer(GamePlayer actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    public void setGameLogic(Game_Rules game_rules) {
        this.game_rules = game_rules;
    }

    public Movement[] getMovements() {
        return movements;
    }

    public void setMovements(Movement[] movements) {
        this.movements = movements;
    }

    public int getMovementsIndex() {
        return movementsIndex;
    }

    public void setMovementsIndex(int movementsIndex) {
        this.movementsIndex = movementsIndex;
    }

    public void setGoGame(GoGameMD goGame) {
        this.goGame = goGame;
    }
}
