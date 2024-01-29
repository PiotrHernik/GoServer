package com.example.serwer.Service;

import com.example.serwer.MariaDb.Movement;
import com.example.serwer.MariaDb.MovementDAO;

public interface MovementService
{
     void saveMovement(Movement movement);
     MovementDAO getMovementDAO();
     void setMovementDAO(MovementDAO movementDAO);
     Movement[] getMovementsById(int id);
}
