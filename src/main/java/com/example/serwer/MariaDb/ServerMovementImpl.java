package com.example.serwer.MariaDb;

import org.springframework.stereotype.Service;

@Service
public class ServerMovementImpl implements ServiceMovement
{
private MovementDAO movementDAO;

    public ServerMovementImpl(MovementDAO movementDAO){
        this.movementDAO = movementDAO;
    }
    @Override
    public void saveMovement(Movement movement)
    {
        this.movementDAO.saveMovement(movement);
    }

    @Override
    public MovementDAO getMovementDAO()
    {
        return movementDAO;
    }

    @Override
    public void setMovementDAO(MovementDAO movementDAO)
    {
        this.movementDAO = movementDAO;
    }

    @Override
    public Movement[] getMovementsById(int id)
    {
        return this.movementDAO.getMovementsById(id);
    }
}
