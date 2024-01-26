package com.example.serwer.MessagefromServer;

public class NewGame implements Server_ServerMessage
{
    private static final long serialVersionUID = 1L;

    private int[] gamesIdList;

    public NewGame(int[] gamesIdList) {
        this.gamesIdList = gamesIdList;
    }

    public int[] getGamesIdList() {
        return gamesIdList;
    }

    public void setGamesIdList(int[] gamesIdList) {
        this.gamesIdList = gamesIdList;
    }
}
