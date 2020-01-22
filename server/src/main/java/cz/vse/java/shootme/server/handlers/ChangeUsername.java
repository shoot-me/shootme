package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.requests.ChangeUsernameRequest;
import cz.vse.java.shootme.server.net.responses.ChangeUsernameResponse;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ChangeUsername {


    public ChangeUsername(ChangeUsernameRequest changeUsernameRequest) {

        EntityManager em = Database.getEntityManager();

        Query query = em.createQuery("from User u where u.username = '" + changeUsernameRequest.username + "'");

        List result = query.getResultList();

        if (result.isEmpty()) {

            em.getTransaction().begin();

            User user = changeUsernameRequest.getConnection().getUser();
            user.setUsername(changeUsernameRequest.username);

            em.merge(user);
            em.getTransaction().commit();
            em.close();

            changeUsernameRequest.respond(new ChangeUsernameResponse());
        } else {
            changeUsernameRequest.respondError("This username already exists!");
        }


    }
}
