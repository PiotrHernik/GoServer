package com.example.serwer.Service;

import com.example.serwer.MariaDb.GoGameMD;

public interface GoGameMDService
{
    void saveGame(GoGameMD goGame);
     GoGameMD getGame();
     GoGameMD getGameById(int id);
     int[] getIdList();
}
