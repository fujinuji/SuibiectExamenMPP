package scs.mpp.exam.entites;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Round implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String playerName;
    @Column
    private String answer1;
    @Column
    private String answer2;
    @Column
    private String answer3;
    @Column
    private String gameId;
    @Column
    private Integer round;
    @Column
    private Integer points;

    public Round(Integer id, String playerName, String answer1, String answer2, String answer3, String gameId, Integer round, Integer points) {
        this.id = id;
        this.playerName = playerName;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.gameId = gameId;
        this.round = round;
        this.points = points;
    }

    public Round() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getGameId() {
        return gameId;
    }

    public Integer getRound() {
        return round;
    }

    public Integer getPoints() {
        return points;
    }
}
