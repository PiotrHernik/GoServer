package com.example.serwer.Game_Logic;

import org.springframework.stereotype.Component;


public class Game_Rules
{
    private int[][] board; // 0 - empty, 1 - first player, 2 - second player
    private int size;
    private int koX;
    private int koY;


    public Game_Rules(int size) {
        this.board = new int[size][size];
        this.size = size;
        this.koX = -1;
        this.koY = -1;
    }


    public boolean move(int x, int y, int player) {
        if (this.validateMove(x, y, player)) {
            this.board[x][y] = player;
            return true;
        }

        return false;
    }


    public boolean validateMove(int x, int y, int player) {
        if (this.board[x][y] != 0)
            return false;

        if (this.koX == x && this.koY == y)
            return false;

        boolean answer = this.checkBreath(x, y, player);
        this.cleanBoardAfterChecking(x, y, -1, 0, player);

        if (!answer) {
            int opponent = ((player == 1) ? 2 : 1);
            answer = this.checkRemoveOtherStones(x, y, player);
            this.cleanBoardAfterChecking(x, y, -1, 0, opponent);
        }

        if (answer)
            return true;

        return false;
    }

    public int[][] removeDeathStones(int x, int y) {

        System.out.println("Jestesmy w deathstones    " + x + "    " + y);//x, y - new stone's coordinate
        int deathStoneNumber = ((this.board[x][y] == 1) ? 2 : 1);

        if (x + 1 < this.size && this.board[x + 1][y] == deathStoneNumber && this.checkBreath(x + 1, y, deathStoneNumber))
            this.cleanBoardAfterChecking(x + 1, y, -1, deathStoneNumber, deathStoneNumber);

        if (x - 1 >= 0 && this.board[x - 1][y] == deathStoneNumber && this.checkBreath(x - 1, y, deathStoneNumber))
            this.cleanBoardAfterChecking(x - 1, y, -1, deathStoneNumber, deathStoneNumber);

        if (y + 1 < this.size && this.board[x][y + 1] == deathStoneNumber && this.checkBreath(x, y + 1, deathStoneNumber))
            this.cleanBoardAfterChecking(x, y + 1, -1, deathStoneNumber, deathStoneNumber);

        if (y - 1 >= 0 && this.board[x][y - 1] == deathStoneNumber && this.checkBreath(x, y - 1, deathStoneNumber))
            this.cleanBoardAfterChecking(x, y - 1, -1, deathStoneNumber, deathStoneNumber);

        int[][] emptyPlaces = this.getEmptyPlaces();
        for (int i = 0; i < emptyPlaces.length; i++ )
        {
            for (int j = 0; j < emptyPlaces[i].length; j++ )
            {
                System.out.println("Empty PLaes :  " + emptyPlaces[i][j]);
            }
        }
        if (emptyPlaces.length == 1) {
            this.koX = emptyPlaces[0][0];
            this.koY = emptyPlaces[0][1];
        } else {
            this.koX = -1;
            this.koY = -1;
        }

        this.removeAllDeathStones();

        return emptyPlaces;
    }

    public int checkPlayer(int x, int y) {
        if (x < 0 || y < 0 || x >= this.size || y >= this.size)
            return -1;
        return this.board[x][y];
    }


    public void removeDeathStonesEndGame() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j] == 0) {
                    this.markTerritory(i, j);
                    this.checkAllGroups();
                    this.markDeathStones(-5);
                    this.cleanBoardAfterChecking(i, j, -2, 0, 0);
                }
            }

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                if (this.board[i][j] == -5)
                    this.board[i][j] = 0;

    }


    public int countPoints(int player) {

        int points = 0;
        int opponent = ((player == 1) ? 2 : 1);

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                if (this.board[i][j] == 0 && checkTerritory(i, j, opponent))
                    this.cleanBoardAfterChecking(i, j, -1, 0, 0);

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                if (this.board[i][j] == -1) {
                    this.board[i][j] = 0;
                    points++;
                }

        return points;
    }

    private boolean checkBreath(int x, int y, int player) {
        this.board[x][y] = -1;

        if (this.checkEmptyPlace(x, y))
            return true;

        if (x + 1 < this.size && this.board[x + 1][y] == player && this.checkBreath(x + 1, y, player))
            return true;
        if (x - 1 >= 0 && this.board[x - 1][y] == player && this.checkBreath(x - 1, y, player))
            return true;
        if (y + 1 < this.size && this.board[x][y + 1] == player && this.checkBreath(x, y + 1, player))
            return true;
        return y - 1 >= 0 && this.board[x][y - 1] == player && this.checkBreath(x, y - 1, player);
    }

    private boolean checkEmptyPlace(int x, int y) {
        if (x + 1 < this.size && this.board[x + 1][y] == 0)
            return true;
        if (x - 1 >= 0 && this.board[x - 1][y] == 0)
            return true;
        if (y + 1 < this.size && this.board[x][y + 1] == 0)
            return true;
        return y - 1 >= 0 && this.board[x][y - 1] == 0;
    }

    private void cleanBoardAfterChecking(int x, int y,int actualValue, int startValue, int otherValue) {
        this.opponentStonesRecursionCheck(x, y, actualValue, otherValue);
        this.board[x][y] = startValue;
    }

    private void opponentStonesRecursionCheck(int x, int y, int actualValue, int value) {
        this.board[x][y] = value;

        if (x + 1 < this.size && this.board[x + 1][y] == actualValue)
            this.opponentStonesRecursionCheck(x + 1, y, actualValue, value);
        if (x - 1 >= 0 && this.board[x - 1][y] == actualValue)
            this.opponentStonesRecursionCheck(x - 1, y, actualValue, value);
        if (y + 1 < this.size && this.board[x][y + 1] == actualValue)
            this.opponentStonesRecursionCheck(x, y + 1, actualValue, value);
        if (y - 1 >= 0 && this.board[x][y - 1] == actualValue)
            this.opponentStonesRecursionCheck(x, y - 1, actualValue, value);
    }

    private boolean checkRemoveOtherStones(int x, int y, int player) {
        int opponent = ((player == 1) ? 2 : 1);
        this.board[x][y] = player;

        if (x + 1 < this.size && this.board[x + 1][y] == opponent) {
            if (!this.checkBreath(x + 1, y, opponent))
                return true;

            this.cleanBoardAfterChecking(x + 1, y, -1, opponent, opponent);
        }

        if (x - 1 >= 0 && this.board[x - 1][y] == opponent) {
            if (!this.checkBreath(x - 1, y, opponent))
                return true;

            this.cleanBoardAfterChecking(x - 1, y, -1, opponent, opponent);
        }

        if (y + 1 < this.size && this.board[x][y + 1] == opponent) {
            if (!this.checkBreath(x, y + 1, opponent))
                return true;

            this.cleanBoardAfterChecking(x, y + 1, -1, opponent, opponent);
        }

        if (y - 1 >= 0 && this.board[x][y - 1] == opponent) {
            if (!this.checkBreath(x, y - 1, opponent))
                return true;

            this.cleanBoardAfterChecking(x, y - 1, -1, opponent, opponent);
        }

        return false;
    }

    private int[][] getEmptyPlaces() {
        int amount = 0;

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                if (this.board[i][j] == -1)
                    amount++;

        if (amount == 0)
            return new int[0][0];

        int actualAmount = 0;
        int[][] table = new int[amount][2];

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                if (this.board[i][j] == -1) {
                    table[actualAmount][0] = i;
                    table[actualAmount][1] = j;
                    actualAmount++;
                }

        return table;
    }

    private void removeAllDeathStones() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                if (this.board[i][j] == -1)
                    this.board[i][j] = 0;
    }

    private void markTerritory(int x, int y) {
        this.board[x][y] = -2;

        if (x + 1 < this.size && this.board[x + 1][y] == 0)
            markTerritory(x + 1, y);
        if (x - 1 >= 0 && this.board[x - 1][y] == 0)
            markTerritory(x - 1, y);
        if (y + 1 < this.size && this.board[x][y + 1] == 0 )
            markTerritory(x, y + 1);
        if (y - 1 >= 0 && this.board[x][y - 1] == 0)
            markTerritory(x, y - 1);
    }

    private void checkAllGroups() {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++)
                if (this.board[i][j] == 1 && this.checkBreath(i, j, 1)) {
                    this.cleanBoardAfterChecking(i, j, -1, 1, 1);
                } else if (this.board[i][j] == 2 && this.checkBreath(i, j, 2)) {
                    this.cleanBoardAfterChecking(i, j, -1, 2, 2);
                }
    }

    private void markDeathStones(int value) {
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j] == -1)
                    this.board[i][j] = value;
            }
    }

    private boolean checkTerritory(int x, int y, int opponent) {
        this.board[x][y] = -1;
        boolean answer = !this.checkOpponent(x, y, opponent);

        if (x + 1 < this.size && this.board[x + 1][y] == 0 && this.checkTerritory(x + 1, y, opponent))
            answer = false;
        if (x - 1 >= 0 && this.board[x - 1][y] == 0 && this.checkTerritory(x - 1, y, opponent))
            answer = false;
        if (y + 1 < this.size && this.board[x][y + 1] == 0 && this.checkTerritory(x, y + 1, opponent))
            answer = false;
        if (y - 1 >= 0 && this.board[x][y - 1] == 0 && this.checkTerritory(x, y - 1, opponent))
            answer = false;

        return !answer;
    }

    private boolean checkOpponent(int x, int y, int opponent) {
        if (x + 1 < this.size && this.board[x + 1][y] == opponent)
            return true;
        if (x - 1 >= 0 && this.board[x - 1][y] == opponent)
            return true;
        if (y + 1 < this.size && this.board[x][y + 1] == opponent)
            return true;
        return y - 1 >= 0 && this.board[x][y - 1] == opponent;
    }
}
