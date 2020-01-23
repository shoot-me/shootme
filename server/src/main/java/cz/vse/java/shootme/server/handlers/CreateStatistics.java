package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.UserStatistics;

import javax.persistence.EntityManager;

public class CreateStatistics {


    public CreateStatistics(int userId) {

        try {
            EntityManager em = Database.getEntityManager();
            em.getTransaction().begin();

            UserStatistics userStatistics = new UserStatistics();
            userStatistics.setUserId(userId);

            em.persist(userStatistics);
            em.getTransaction().commit();
            em.close();
        } catch (Exception e ){
            e.printStackTrace();
        }

    }
}
