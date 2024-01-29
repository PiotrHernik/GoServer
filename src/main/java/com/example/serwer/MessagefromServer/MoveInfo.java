package com.example.serwer.MessagefromServer;

import java.io.Serial;

public class MoveInfo implements Server_ServerMessage
{
    @Serial
    private static final long serialVersionUID = 1L;
    private int player;
    private int x;
    private int y;
    private int[][] emptyPlaces;
    private boolean correctMove;


    public MoveInfo(int player, boolean correctMove, int x, int y, int[][] emptyPlaces) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.correctMove = correctMove;
        this.emptyPlaces = emptyPlaces;
    }

    public int getPlayer() {
        return this.player;
    }
    public boolean isCorrectMove() {
        return correctMove;
    }
}
