package com.example.serwer.MessagefromServer;

import java.io.Serial;

public class SentGameOptions implements Server_ServerMessage
{
    @Serial
    private static final long serialVersionUID = 1L;
    private int player;
    private int size;
    private String mode;
    public SentGameOptions(int player, int size, String mode) {
        this.player = player;
        this.size = size;
        this.mode = mode;
    }


}
