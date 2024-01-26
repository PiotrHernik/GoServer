package com.example.serwer.Service;

import com.example.serwer.MariaDb.GoGameMD;

public interface GoGameMDService
{
    public void saveGame(GoGameMD goGame);
    public GoGameMD getGame();
    public GoGameMD getGameById(int id);
    public int[] getIdList();
}
