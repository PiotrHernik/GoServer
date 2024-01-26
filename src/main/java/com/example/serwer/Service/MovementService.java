package com.example.serwer.Service;

import com.example.serwer.MariaDb.Movement;
import com.example.serwer.MariaDb.MovementDAO;

public interface MovementService
{
    public void saveMovement(Movement movement);
    public MovementDAO getMovementDAO();
    public void setMovementDAO(MovementDAO movementDAO);
    public Movement[] getMovementsById(int id);
}
