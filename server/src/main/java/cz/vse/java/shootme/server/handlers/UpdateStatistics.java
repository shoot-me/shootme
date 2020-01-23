package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.models.UserStatistics;
import cz.vse.java.shootme.server.net.Connection;
import cz.vse.java.shootme.server.net.requests.UpdateStatisticsRequest;

import javax.persistence.EntityManager;

public class UpdateStatistics {

    private User user;
    private UserStatistics statistics;

    public  UpdateStatistics(User user){
        this.user = user;
        statistics = user.getStatistics();
    }

    public void userJoinedGame(){

        EntityManager em = Database.getEntityManager();
        em.getTransaction().begin();
        statistics.setGamesPlayed(statistics.getGamesPlayed()+1);
        em.merge(statistics);
        em.getTransaction().commit();
        em.close();
    }

    public void playerWasKilled(){

    }

    public void playerKilled(){

    }

    public void playerLostGame(){

    }

    public void playerWonGame(){

    }

}
