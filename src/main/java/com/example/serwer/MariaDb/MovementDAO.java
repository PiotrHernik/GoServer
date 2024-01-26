package com.example.serwer.MariaDb;

public interface MovementDAO
{
    public void saveMovement(Movement movement);
    public Movement[] getMovementsById(int id);
}
