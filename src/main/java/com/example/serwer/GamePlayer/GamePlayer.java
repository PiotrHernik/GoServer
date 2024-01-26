package com.example.serwer.GamePlayer;

import com.example.serwer.Game_Logic.Game;
import com.example.serwer.MessagefromServer.Server_ServerMessage;

import java.io.IOException;

public abstract class GamePlayer implements Runnable
{
    protected Game game;
    protected int numberOfPoints;
    protected int number;

    public abstract void sendMessage(Server_ServerMessage message) throws IOException;

    public void addPoints(int numberOfPoints)
    {
        this.numberOfPoints += 1;
    }

    public int getNumberOfPoints()
    {
        return numberOfPoints;
    }

    public int getNumber()
    {
        return number;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }
}
