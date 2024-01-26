package com.example.serwer.MariaDb;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("movementDAO")
@Transactional
public class MovementDAOImpl implements MovementDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveMovement(Movement movement) {
        entityManager.merge(movement);
    }

    @Override
    public Movement[] getMovementsById(int id) {
        TypedQuery<Movement> query = entityManager.createQuery(
                "SELECT m FROM Movement m WHERE m.game_id = :id", Movement.class);
        query.setParameter("id", id);
        List<Movement> resultList = query.getResultList();
        return resultList.toArray(new Movement[0]);
    }
}