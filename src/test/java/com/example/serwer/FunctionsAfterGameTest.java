package com.example.serwer;

import com.example.serwer.Game_Logic.Game_Rules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionsAfterGameTest
{

    @Test
    public void countPointsTest() {
        Game_Rules Game_Rules  = new Game_Rules(19);

        assertTrue(Game_Rules.move(0, 2, 1));
        assertTrue(Game_Rules.move(1, 1, 1));
        assertTrue(Game_Rules.move(2, 1, 1));
        assertTrue(Game_Rules.move(3, 0, 1));

        assertTrue(Game_Rules.move(17, 0, 1));
        assertTrue(Game_Rules.move(18, 1, 1));

        assertTrue(Game_Rules.move(1, 18, 2));
        assertTrue(Game_Rules.move(1, 17, 2));
        assertTrue(Game_Rules.move(2, 16, 2));
        assertTrue(Game_Rules.move(3, 16, 2));
        assertTrue(Game_Rules.move(4, 16, 2));
        assertTrue(Game_Rules.move(5, 17, 2));
        assertTrue(Game_Rules.move(5, 18, 2));

        assertEquals(Game_Rules.countPoints(1), 5);
        assertEquals(Game_Rules.countPoints(2), 6);
    }

    @Test
    public void removeDeathGroupsTest() {
        Game_Rules Game_Rules  = new Game_Rules(19);

        assertTrue(Game_Rules.move(0, 0, 1));
        assertTrue(Game_Rules.move(2, 0, 2));
        assertTrue(Game_Rules.move(2, 1, 2));
        assertTrue(Game_Rules.move(2, 2, 2));
        assertTrue(Game_Rules.move(1, 2, 2));
        assertTrue(Game_Rules.move(0, 2, 2));
        assertTrue(Game_Rules.move(10, 10, 1));
        assertTrue(Game_Rules.move(12, 10, 1));

        Game_Rules.removeDeathStonesEndGame();
        assertEquals(Game_Rules.checkPlayer(0, 0), 0);


        Game_Rules  = new Game_Rules(19);
        assertTrue(Game_Rules.move(1, 0, 1));
        assertTrue(Game_Rules.move(1, 1, 1));
        assertTrue(Game_Rules.move(0, 1, 1));
        assertTrue(Game_Rules.move(1, 2, 1));
        assertTrue(Game_Rules.move(1, 3, 1));
        assertTrue(Game_Rules.move(0, 3, 1));

        assertTrue(Game_Rules.move(10, 3, 2));

        Game_Rules.removeDeathStonesEndGame();
        assertEquals(Game_Rules.checkPlayer(1, 3), 1);
        assertEquals(Game_Rules.checkPlayer(10, 3), 0);
    }
}
