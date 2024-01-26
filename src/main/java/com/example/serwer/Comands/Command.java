package com.example.serwer.Comands;

import com.example.serwer.ClientMessages.Server_ClientMessage;
import com.example.serwer.GamePlayer.GamePlayer;
import com.example.serwer.Game_Logic.Game;
import com.example.serwer.MariaDb.GoGameMD;

public abstract class Command
{
protected Server_ClientMessage clientMessage;

protected Command() {}

public abstract void executeCommand(Game game, GamePlayer player, GoGameMD goGameMD);

public Server_ClientMessage getClientMessage() {
        return clientMessage;
        }

public void setClientMessage(Server_ClientMessage clientMessage) {
        this.clientMessage = clientMessage;
        }
}
