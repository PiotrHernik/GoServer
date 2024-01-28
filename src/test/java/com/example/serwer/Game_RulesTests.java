package com.example.serwer;

import com.example.serwer.Game_Logic.Game_Rules;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


public class Game_RulesTests
{

    private Game_Rules gameRules;
    @Test
    public void testChainBreathMovement() {
        Game_Rules gameRules = new Game_Rules(19);

        assertTrue(gameRules.move(1, 0, 1));
        assertTrue(gameRules.move(0, 1, 1));
        assertTrue(gameRules.move(0, 0, 1));

        assertTrue(gameRules.move(0, 18, 1));
        assertTrue(gameRules.move(0, 16, 1));
        assertTrue(gameRules.move(1, 17, 1));
        assertTrue(gameRules.move(0, 17, 1));

        assertTrue(gameRules.move(5, 5, 1));
        assertTrue(gameRules.move(4, 4, 1));
        assertTrue(gameRules.move(3, 5, 1));
        assertTrue(gameRules.move(4, 6, 1));
        assertTrue(gameRules.move(4, 5, 1));
    }

    @Test
    public void testStoneCapture() {
        int[][] removedStones;
        Game_Rules gameRules = new Game_Rules(19);

        // Capture one stone
        assertTrue(gameRules.move(0, 0, 2));
        assertTrue(gameRules.move(1, 0, 1));
        assertTrue(gameRules.move(0, 1, 1));
        assertEquals(gameRules.checkPlayer(0, 0), 2);
        removedStones = gameRules.removeDeathStones(1, 0);
        assertEquals(removedStones.length, 1);
        assertEquals(gameRules.checkPlayer(0, 0), 0);
        assertEquals(gameRules.checkPlayer(1, 0), 1);
        assertEquals(gameRules.checkPlayer(0, 1), 1);

        // Capture two stones
        assertTrue(gameRules.move(1, 17, 2));
        assertTrue(gameRules.move(2, 17, 2));

        assertTrue(gameRules.move(1, 18, 1));
        assertTrue(gameRules.move(2, 18, 1));
        assertTrue(gameRules.move(3, 17, 1));
        assertTrue(gameRules.move(2, 16, 1));
        assertTrue(gameRules.move(1, 16, 1));
        assertTrue(gameRules.move(0, 17, 1));

        assertEquals(gameRules.checkPlayer(1, 17), 2);
        assertEquals(gameRules.checkPlayer(2, 17), 2);
        removedStones = gameRules.removeDeathStones(1, 18);
        assertEquals(removedStones.length, 2);
        assertEquals(gameRules.checkPlayer(1, 17), 0);
        assertEquals(gameRules.checkPlayer(2, 17), 0);

        // Capture four stones
        assertTrue(gameRules.move(2, 2, 2));
        assertTrue(gameRules.move(2, 3, 2));
        assertTrue(gameRules.move(1, 3, 2));
        assertTrue(gameRules.move(1, 4, 2));

        assertTrue(gameRules.move(2, 1, 1));
        assertTrue(gameRules.move(1, 2, 1));
        assertTrue(gameRules.move(0, 3, 1));
        assertTrue(gameRules.move(0, 4, 1));
        assertTrue(gameRules.move(1, 5, 1));
        assertTrue(gameRules.move(2, 4, 1));
        assertTrue(gameRules.move(3, 3, 1));
        assertTrue(gameRules.move(3, 2, 1));

        removedStones = gameRules.removeDeathStones(2, 1);
        assertEquals(removedStones.length, 4);
        assertEquals(gameRules.checkPlayer(2, 2), 0);
        assertEquals(gameRules.checkPlayer(2, 3), 0);
        assertEquals(gameRules.checkPlayer(1, 3), 0);
        assertEquals(gameRules.checkPlayer(1, 4), 0);
        assertEquals(gameRules.checkPlayer(2, 1), 1);
    }

    @Test
    public void testNoBreath() {
        Game_Rules gameRules = new Game_Rules(19);

        // One stone
        assertTrue(gameRules.move(1, 0, 1));
        assertTrue(gameRules.move(0, 1, 1));
        assertFalse(gameRules.move(0, 0, 2));

        // Three stones
        assertTrue(gameRules.move(1, 18, 1));
        assertTrue(gameRules.move(2, 18, 1));
        assertTrue(gameRules.move(3, 17, 1));
        assertTrue(gameRules.move(3, 16, 1));
        assertTrue(gameRules.move(2, 15, 1));
        assertTrue(gameRules.move(1, 16, 1));
        assertTrue(gameRules.move(0, 17, 1));

        assertTrue(gameRules.move(1, 17, 2));
        assertTrue(gameRules.move(2, 16, 2));
        assertFalse(gameRules.move(2, 17, 2));
    }

    @Test
    public void testKoRule() {
        int[][] removedStones;
        Game_Rules gameRules = new Game_Rules(19);

        // Stone placement after one turn, ko is still on the board
        assertTrue(gameRules.move(1, 0, 1));
        assertTrue(gameRules.move(0, 1, 1));
        assertTrue(gameRules.move(1, 2, 1));
        assertTrue(gameRules.move(2, 1, 1));
        assertTrue(gameRules.move(0, 2, 2));
        assertTrue(gameRules.move(1, 3, 2));
        assertTrue(gameRules.move(2, 2, 2));

        assertTrue(gameRules.move(1, 1, 2));
        removedStones = gameRules.removeDeathStones(1, 1);
        assertEquals(removedStones.length, 1);
        assertEquals(gameRules.checkPlayer(1, 2), 0);
        assertFalse(gameRules.move(1, 2, 1));
        assertTrue(gameRules.move(18, 2, 1));
        removedStones = gameRules.removeDeathStones(18, 2);
        assertEquals(removedStones.length, 0);
        assertTrue(gameRules.move(1, 2, 1));
        assertFalse(gameRules.move(1, 1, 2));

        // Stone placement after one turn, ko ends
        assertTrue(gameRules.move(10, 10, 1));
        assertTrue(gameRules.move(10, 11, 1));
        assertTrue(gameRules.move(11, 12, 1));
        assertTrue(gameRules.move(12, 11, 1));
        assertTrue(gameRules.move(10, 12, 2));
        assertTrue(gameRules.move(11, 13, 2));
        assertTrue(gameRules.move(12, 12, 2));

        assertTrue(gameRules.move(11, 11, 2));
        removedStones = gameRules.removeDeathStones(11, 11);
        assertEquals(removedStones.length, 1);
        assertEquals(gameRules.checkPlayer(11, 12), 0);
        assertFalse(gameRules.move(11, 12, 1));
        assertTrue(gameRules.move(17, 17, 1));
        removedStones = gameRules.removeDeathStones(17, 17);
        assertEquals(removedStones.length, 0);
        assertTrue(gameRules.move(11, 12, 2));
    }
}


