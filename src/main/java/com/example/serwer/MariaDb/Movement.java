package com.example.serwer.MariaDb;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "movements")
public class Movement
{
    @Id
    @Column(name = "id") private int id;
    @Column(name = "type") private String type;
    @Column(name = "x") private int x;
    @Column(name = "y") private int y;
    @Column(name = "game_id") private int game_id;

    @ManyToOne(cascade= {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "game_id", insertable = false, updatable = false)
    private GoGameMD game;

    public Movement() {
        super();
    }

    public Movement(String type, int x, int y, int game_id, GoGameMD game) {
        super();
        this.type = type;
        if (x >= 0)
            this.x = x;
        if (y >=0 )
            this.y = y;
        this.game_id = game_id;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPlayer() {
        return game_id;
    }

    public void setPlayer(int game_id) {
        this.game_id = game_id;
    }

    public GoGameMD getGame() {
        return game;
    }

    public void setGame(GoGameMD game) {
        this.game = game;
    }
}
