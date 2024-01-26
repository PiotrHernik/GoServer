package com.example.serwer.Service;

import com.example.serwer.MariaDb.GoGameMD;
import com.example.serwer.MariaDb.GoGameMDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoGameMdServiceImpl implements GoGameMDService
{

    @Autowired
    private GoGameMDAO goGameDAO;
    @Override
    @Transactional
    public void saveGame(GoGameMD goGame)
    {
        this.goGameDAO.saveGame(goGame);
    }

    @Override
    @Transactional
    public GoGameMD getGame()
    {
        return this.goGameDAO.getGame();
    }

    @Override

    public GoGameMD getGameById(int id)
    {
        return this.goGameDAO.getGameById(id);
    }

    @Override
    @Transactional
    public int[] getIdList()
    {
        return this.goGameDAO.getIdList();
    }
}
