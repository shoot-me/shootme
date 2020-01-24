package cz.vse.java.shootme.server.statisticService;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.UserStatistics;

import javax.persistence.EntityManager;

public class StatKills {

    public StatKills(UserStatistics statistics){
        EntityManager em = Database.getEntityManager();
        em.getTransaction().begin();
        statistics.setTotalKills(statistics.getTotalKills() + 1);
        em.merge(statistics);
        em.getTransaction().commit();
        em.close();
    }
}
