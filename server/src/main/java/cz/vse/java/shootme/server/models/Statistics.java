package cz.vse.java.shootme.server.models;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "games_played", columnDefinition = "int default 0")
    private int gamesPlayed;

    @Column(name = "games_won", columnDefinition = "int default 0")
    private int gamesWon;

    @Column(name = "games_lost", columnDefinition = "int default 0")
    private int gamesLost;

    @Column(name = "total_kills", columnDefinition = "int default 0")
    private int totalKills;

    @Column(name = "total_killed", columnDefinition = "int default 0")
    private int totalKilled;

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
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
