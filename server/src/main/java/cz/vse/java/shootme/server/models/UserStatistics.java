package cz.vse.java.shootme.server.models;

import javax.persistence.*;

@Entity
@Table(name = "userstatistics")
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "games_played")
    private int gamesPlayed;

    @Column(name = "games_won")
    private int gamesWon;

    @Column(name = "games_lost")
    private int gamesLost;

    @Column(name = "total_kills")
    private int totalKills;

    @Column(name = "total_killed")
    private int totalKilled;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getId() {
        return id;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public int getTotalKilled() {
        return totalKilled;
    }

    public void setTotalKilled(int totalKilled) {
        this.totalKilled = totalKilled;
    }


}
