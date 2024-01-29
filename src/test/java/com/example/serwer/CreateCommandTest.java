package com.example.serwer;

import com.example.serwer.ClientMessages.Move;
import com.example.serwer.ClientMessages.Next;
import com.example.serwer.ClientMessages.Pass;
import com.example.serwer.ClientMessages.Surrender;
import com.example.serwer.Comands.*;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class CreateCommandTest
{
    @Mock
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCommand_shouldReturnCorrectCommandForDifferentMessages() {
        // Arrange
        ChoseCommand choseCommand = new ChoseCommand();
        choseCommand.applicationContext = applicationContext;

        // Mockowanie zwracanych komend przez ApplicationContext
        MoveCommand moveCommand = new MoveCommand();
        PassCommand passCommand = new PassCommand();
        when(applicationContext.getBean(MoveCommand.class)).thenReturn(moveCommand);
        when(applicationContext.getBean(PassCommand.class)).thenReturn(passCommand);

        // Act
        Command moveResult = choseCommand.getCommand(new Move(0, 0));
        Command passResult = choseCommand.getCommand(new Pass());

        // Assert
        assertTrue(moveResult instanceof MoveCommand);
        assertTrue(passResult instanceof PassCommand);
    }
    }


