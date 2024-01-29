package com.example.serwer;
import com.example.serwer.ClientMessages.Server_ClientMessage;
import com.example.serwer.MessagefromServer.NewGame;
import com.example.serwer.MessagefromServer.Server_ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class ServerConnection
{
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;


    public ServerConnection(ServerSocket serverSocket)
    {
        try
        {
            Socket socket = serverSocket.accept();

            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized Server_ClientMessage getMessage()
    {
        Server_ClientMessage clientMessage = null;
        try
        {
            clientMessage = (Server_ClientMessage) inputStream.readObject();
        }
        catch (ClassNotFoundException | IOException e)
        {
            System.out.println("Not found");
        }
        return clientMessage;
    }

    public void sendMessage(Server_ServerMessage message) throws IOException
    {
        outputStream.writeObject(message);
        outputStream.flush();

        System.out.println("hello");
    }


}
