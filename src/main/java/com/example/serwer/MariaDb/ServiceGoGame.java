package com.example.serwer.MariaDb;

public interface ServiceGoGame
{
    public void saveGame(GoGameMD goGame);
    public GoGameMD getGame();
    public GoGameMD getGameById(int id);
    public int[] getIdList();
}
