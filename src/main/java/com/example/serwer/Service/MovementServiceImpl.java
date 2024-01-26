package com.example.serwer.Service;

import com.example.serwer.MariaDb.Movement;
import com.example.serwer.MariaDb.MovementDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovementServiceImpl implements MovementService {

    private MovementDAO movementDAO;

    @Autowired
    public MovementServiceImpl(MovementDAO movementDAO){
        this.movementDAO = movementDAO;
    }

    @Override
    @Transactional
    public void saveMovement(Movement movement) {
        this.movementDAO.saveMovement(movement);
    }

    public MovementDAO getMovementDAO() {
        return movementDAO;
    }

    public void setMovementDAO(MovementDAO movementDAO) {
        this.movementDAO = movementDAO;
    }

    @Override
    public Movement[] getMovementsById(int id) {
        return this.movementDAO.getMovementsById(id);
    }
}