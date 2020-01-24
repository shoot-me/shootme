package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.models.UserStatistics;
import cz.vse.java.shootme.server.net.requests.UpdateStatisticsRequest;
import cz.vse.java.shootme.server.net.responses.UpdateStatisticsResponse;
import cz.vse.java.shootme.server.statisticService.StatJoinGame;

import javax.persistence.EntityManager;

public class UpdateStatistics {

    private User user;
    private UserStatistics statistics;

    public UpdateStatistics(UpdateStatisticsRequest updateStatisticsRequest) {
        user = updateStatisticsRequest.getConnection().getUser();
        statistics = user.getStatistics();
        if (updateStatisticsRequest.type.equals("joinGame")){
            new StatJoinGame(statistics);
            updateStatisticsRequest.respond(new UpdateStatisticsResponse());
        } else {
            updateStatisticsRequest.respondError("Cannot update statistics");
        }


    }

    public UpdateStatistics(User user) {
        this.user = user;
        statistics = user.getStatistics();
    }

    public void userJoinedGame() {

        EntityManager em = Database.getEntityManager();
        em.getTransaction().begin();
        statistics.setGamesPlayed(statistics.getGamesPlayed() + 1);
        em.merge(statistics);
        em.getTransaction().commit();
        em.close();


    }

    public void playerWasKilled() {
        EntityManager em = Database.getEntityManager();
        em.getTransaction().begin();
        statistics.setTotalKilled(statistics.getTotalKills() + 1);
        em.merge(statistics);
        em.getTransaction().commit();
        em.close();

    }

    public void playerKilled() {
        EntityManager em = Database.getEntityManager();
        em.getTransaction().begin();
        statistics.setTotalKills(statistics.getTotalKills() + 1);
        em.merge(statistics);
        em.getTransaction().commit();
        em.close();

    }

    public void playerLostGame() {

        EntityManager em = Database.getEntityManager();
        em.getTransaction().begin();
        statistics.setGamesLost(statistics.getGamesLost() + 1);
        em.merge(statistics);
        em.getTransaction().commit();
        em.close();
    }

    public void playerWonGame() {

        EntityManager em = Database.getEntityManager();
        em.getTransaction().begin();
        statistics.setGamesWon(statistics.getGamesWon() + 1);
        em.merge(statistics);
        em.getTransaction().commit();
        em.close();
    }

}
