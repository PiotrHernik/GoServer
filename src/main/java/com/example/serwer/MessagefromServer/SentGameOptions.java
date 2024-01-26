package com.example.serwer.MessagefromServer;

public class SentGameOptions implements Server_ServerMessage
{
    private static final long serialVersionUID = 1L;
    private int player;
    private int size;
    private String mode; //hotSeat, singleplayer, multiplayer

    public SentGameOptions(int player, int size, String mode) {
        this.player = player;
        this.size = size;
        this.mode = mode;
    }

    public int getPlayer() {
        return this.player;
    }

    public int getSize() {
        return size;
    }

    public String getMode() {
        return mode;
    }
}
