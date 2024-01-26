package com.example.serwer.MariaDb;

public interface ServiceMovement
{
    public void saveMovement(Movement movement);
    public MovementDAO getMovementDAO();
    public void setMovementDAO(MovementDAO movementDAO);
    public Movement[] getMovementsById(int id);
}
