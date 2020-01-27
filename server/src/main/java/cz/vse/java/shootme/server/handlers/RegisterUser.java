package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.Skin;
import cz.vse.java.shootme.server.net.requests.RegisterRequest;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.responses.RegisterSuccessfulResponse;
import cz.vse.java.shootme.server.util.Password;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class RegisterUser {

    public RegisterUser(RegisterRequest register) {
        Database.transaction(register, em -> {
            User user = new User();
            user.setUsername(register.username);
            String hashedPassword = Password.hashPassword(register.password);
            user.setPassword(hashedPassword);

            Query query = em.createQuery("from Skin", Skin.class);
            Skin skin = (Skin) query.getResultStream().findFirst().orElse(null);
            user.setSkin(skin);

            em.persist(user);
            em.getTransaction().commit();

            register.respond(new RegisterSuccessfulResponse());
        });
    }
}
