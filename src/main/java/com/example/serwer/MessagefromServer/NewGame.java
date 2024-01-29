package com.example.serwer.MessagefromServer;

import java.io.Serial;

public class NewGame implements Server_ServerMessage
{
    @Serial
    private static final long serialVersionUID = 1L;

    private int[] gamesIdList;

    public NewGame(int[] gamesIdList) {
        this.gamesIdList = gamesIdList;
    }


}
