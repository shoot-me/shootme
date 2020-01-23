package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.net.requests.RegisterRequest;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.responses.RegisterSuccessfulResponse;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class RegisterUser {

    public RegisterUser(RegisterRequest register) {

        EntityManager em = Database.getEntityManager();
        em.getTransaction().begin();

        try {
            User user = new User();
            user.setUsername(register.username);
            user.setPassword(register.password);

            em.persist(user);
            em.getTransaction().commit();

            register.respond(new RegisterSuccessfulResponse());
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            register.respondError("Registration error!");
        }

        em.close();

    }

}
