package com.example.serwer.MariaDb;

public interface GoGameMDAO
{
    void saveGame(GoGameMD goGame);
   GoGameMD getGame();
     GoGameMD getGameById(int id);
     int[] getIdList();
}
