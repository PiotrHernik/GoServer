package com.example.serwer.MariaDb;


//import jakarta.persistence.Entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "game")
public class GoGameMD
{
    @Id
    @Column(name = "id") private int id;
    @Column(name = "date") private Date date;
    @Column(name = "type") private String type;
    @Column(name = "size") private int size;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private List<Movement> movements;

    public GoGameMD() {
        super();
    }

    public GoGameMD(int id, Date date, List<Movement> movements) {
        super();
        this.id = id;
        this.date = date;
        this.movements = movements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Movement> getMovements() {
        return movements;
    }
    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
