package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.requests.ChangeUsernameRequest;
import cz.vse.java.shootme.server.net.responses.ChangeUsernameResponse;

import javax.persistence.EntityManager;

public class ChangeUsername {

    public ChangeUsername(ChangeUsernameRequest request) {
        Database.transaction(request, em -> {
            User user = em.merge(request.getConnection().getUser());
            user.setUsername(request.username);
            em.persist(user);

            return user;
        }).ifPresent(user -> {
            request.getConnection().setUser(user);

            request.respondSuccess("Username changed succesfully");
        });
    }
}
