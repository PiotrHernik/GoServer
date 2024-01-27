package com.example.serwer.Comands;


import com.example.serwer.ClientMessages.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ChoseCommand implements CommandFactory
{
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public Command getCommand(Server_ClientMessage message) {

        if (message instanceof Move) {
            MoveCommand moveCommand = applicationContext.getBean(MoveCommand.class);
            moveCommand.setClientMessage(message);
            return moveCommand;
        } else if (message instanceof Surrender) {
            SurrenderCommand surrenderCommand = applicationContext.getBean(SurrenderCommand.class);
            surrenderCommand.setClientMessage(message);
            return surrenderCommand;
        } else if (message instanceof Pass) {
            PassCommand passCommand = applicationContext.getBean(PassCommand.class);
            passCommand.setClientMessage(message);
            return passCommand;
        } else if (message instanceof Next)
        {

            return new NextCommand();

        }
        return null;
}
}
