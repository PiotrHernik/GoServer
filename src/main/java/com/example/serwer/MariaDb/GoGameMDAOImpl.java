package com.example.serwer.MariaDb;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class GoGameMDAOImpl implements GoGameMDAO
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveGame(GoGameMD goGame) {
        entityManager.merge(goGame);
    }

    @Override
    public GoGameMD getGame() {
        TypedQuery<GoGameMD> query = entityManager.createQuery(
                "SELECT g FROM GoGameMD g WHERE g.id = (SELECT MAX(id) FROM GoGameMD)",
                GoGameMD.class);
        return query.getSingleResult();
    }

    @Override
    public int[] getIdList() {
        TypedQuery<Integer> query = entityManager.createQuery(
                "SELECT g.id FROM GoGameMD g",
                Integer.class);
        List<Integer> idList = query.getResultList();
        int[] ids = new int[idList.size()];
        for (int i = 0; i < idList.size(); i++) {
            ids[i] = idList.get(i);
        }
        return ids;
    }

    @Override
    public GoGameMD getGameById(int id) {
        return entityManager.find(GoGameMD.class, id);
    }
}