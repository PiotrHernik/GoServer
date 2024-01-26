package com.example.serwer.MariaDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceGoGameImpl implements ServiceGoGame
{
    @Autowired
    private GoGameMDAO goGameMDAO;
    @Override
    @Transactional
    public void saveGame(GoGameMD goGame)
    {
        this.goGameMDAO.saveGame(goGame);
    }

    @Override
    @Transactional
    public GoGameMD getGame()
    {
        return this.goGameMDAO.getGame();
    }

    @Override
    @Transactional
    public GoGameMD getGameById(int id)
    {
        return this.goGameMDAO.getGameById(id);
    }

    @Override
    public int[] getIdList()
    {
        return this.goGameMDAO.getIdList();
    }
}
