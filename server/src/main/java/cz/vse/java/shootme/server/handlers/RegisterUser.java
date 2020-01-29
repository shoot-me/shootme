package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.Skin;
import cz.vse.java.shootme.server.net.requests.RegisterRequest;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.responses.RegisterSuccessfulResponse;
import cz.vse.java.shootme.server.util.Password;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Method to register user
 */
public class RegisterUser {

    public RegisterUser(RegisterRequest register) {
        Skin skin = Database.transaction(em -> {
            Query query = em.createQuery("from Skin", Skin.class);
            return (Skin) query.getResultStream().findFirst().orElse(null);
        }).get();

        boolean ok = Database.transaction(em -> {
            User user = new User();
            user.setUsername(register.username);
            String hashedPassword = Password.hashPassword(register.password);
            user.setPassword(hashedPassword);


            user.setSkin(skin);

            em.persist(user);
        });

        if (ok) {
            register.respondSuccess("Register succesfull");
        } else {
            register.respondError("Register failed");
        }

    }


}
