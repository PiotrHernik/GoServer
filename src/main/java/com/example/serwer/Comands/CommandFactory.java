package com.example.serwer.Comands;

import com.example.serwer.ClientMessages.Server_ClientMessage;

public interface CommandFactory
{
    public Command getCommand(Server_ClientMessage message);
}
