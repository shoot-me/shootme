package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.net.requests.RegisterRequest;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.responses.RegisterSuccessfulResponse;

import javax.persistence.EntityManager;

public class RegisterUser {

    public RegisterUser(RegisterRequest register) {


        EntityManager em = Database.getEntityManager();

        em.getTransaction().begin();

        User user = new User();
        user.setUsername(register.username);
        user.setPassword(register.password);
        em.persist(user);

        em.getTransaction().commit();

        em.close();

        //TODO
         if (true) {
             register.respond(new RegisterSuccessfulResponse());
         } else {
             register.respondError("Registration error!");
         }
    }

}
