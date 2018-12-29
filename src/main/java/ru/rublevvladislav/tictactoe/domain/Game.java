package ru.rublevvladislav.tictactoe.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private Date date;

    private String winner;

    private String gameMode;

    public Game() {
    }

    public Game(Date date, String winner) {
        this.date = date;
        this.winner = winner;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setWinner(String winner){
        this.winner = winner;
    }

    public String getWinner(){
        return winner;
    }

    public void setGameMode(String gameMode){
        this.gameMode = gameMode;
    }

    public String getGameMode(){
        return gameMode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
