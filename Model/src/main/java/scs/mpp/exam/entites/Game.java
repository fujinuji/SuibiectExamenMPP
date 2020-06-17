package scs.mpp.exam.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Game implements Serializable {
    @Id
    private Integer id;
    @Column
    private String firstPlayer;
    @Column
    private String secondPlayer;
    @Column
    private String thirdPlayer;
    @Column
    private String firstPlayerPoints;
    @Column
    private String secondPlayerPoints;
    @Column
    private String thirdPlayerPoints;

    public Game(Integer id, String firstPlayer, String secondPlayer, String thirdPlayer, String firstPlayerPoints, String secondPlayerPoints, String thirdPlayerPoints) {
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.thirdPlayer = thirdPlayer;
        this.firstPlayerPoints = firstPlayerPoints;
        this.secondPlayerPoints = secondPlayerPoints;
        this.thirdPlayerPoints = thirdPlayerPoints;
    }

    public Game() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public String getThirdPlayer() {
        return thirdPlayer;
    }

    public void setThirdPlayer(String thirdPlayer) {
        this.thirdPlayer = thirdPlayer;
    }

    public String getFirstPlayerPoints() {
        return firstPlayerPoints;
    }

    public void setFirstPlayerPoints(String firstPlayerPoints) {
        this.firstPlayerPoints = firstPlayerPoints;
    }

    public String getSecondPlayerPoints() {
        return secondPlayerPoints;
    }

    public void setSecondPlayerPoints(String secondPlayerPoints) {
        this.secondPlayerPoints = secondPlayerPoints;
    }

    public String getThirdPlayerPoints() {
        return thirdPlayerPoints;
    }

    public void setThirdPlayerPoints(String thirdPlayerPoints) {
        this.thirdPlayerPoints = thirdPlayerPoints;
    }
}
