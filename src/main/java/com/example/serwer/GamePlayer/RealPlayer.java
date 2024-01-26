package com.example.serwer.GamePlayer;

import com.example.serwer.ClientMessages.Server_ClientMessage;
import com.example.serwer.ServerConnection;
import com.example.serwer.MessagefromServer.Server_ServerMessage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RealPlayer extends GamePlayer
{
    private ServerConnection connection;

    public RealPlayer(ServerConnection connection, int number)
    {
        this.connection = connection;
                this.number = number;
                this.numberOfPoints = getNumberOfPoints();
    }
    @Override
    public void sendMessage(Server_ServerMessage message) throws IOException
    {
        this.connection.sendMessage(message);
    }

    @Override
    public void run()
    {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

       Server_ClientMessage clientMessage = null;
        do {
            clientMessage = this.connection.getMessage();
            this.game.getMessage(clientMessage, this);

        } while (clientMessage != null);
    }
    public ServerConnection getConnection()
    {
        return this.connection;
    }
}
