package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.UserStatistics;
import cz.vse.java.shootme.server.net.requests.RegisterRequest;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.responses.RegisterSuccessfulResponse;
import cz.vse.java.shootme.server.util.Password;

import javax.persistence.EntityManager;

public class RegisterUser {

    public RegisterUser(RegisterRequest register) {

        EntityManager em = Database.getEntityManager();
        em.getTransaction().begin();

        int userId = 0;


        try {
            User user = new User();
            user.setUsername(register.username);
            String hashedPassword = Password.hashPassword(register.password);
            user.setPassword(hashedPassword);

            em.persist(user);
            em.getTransaction().commit();

            userId = user.getId();



            register.respond(new RegisterSuccessfulResponse());
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            register.respondError("Registration error!");
        }

        em.close();

        System.out.println(userId);


        CreateStatistics createStatistics = new CreateStatistics(userId);


    }

}
