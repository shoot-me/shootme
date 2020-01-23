package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.requests.ChangeUsernameRequest;
import cz.vse.java.shootme.server.net.responses.ChangeUsernameResponse;

import javax.persistence.EntityManager;

public class ChangeUsername {

    //TODO username cannot be empty!!!!! =D

    public ChangeUsername(ChangeUsernameRequest changeUsernameRequest) {

        EntityManager em = Database.getEntityManager();


        em.getTransaction().begin();

        try {
            User user = changeUsernameRequest.getConnection().getUser();
            user.setUsername(changeUsernameRequest.username);

            em.merge(user);
            em.getTransaction().commit();

            changeUsernameRequest.respond(new ChangeUsernameResponse());
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            changeUsernameRequest.respondError("This username already exists!");
        }

        em.close();


    }
}
