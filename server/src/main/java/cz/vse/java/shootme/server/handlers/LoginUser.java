package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.net.Connection;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.LoginRequest;
import cz.vse.java.shootme.server.net.responses.LoginSuccessfulResponse;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.util.Password;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LoginUser {

    public LoginUser(LoginRequest request) {
        Database.transaction(request, em -> {
            Query query = em.createQuery("from User U where U.username = :username", User.class);
            query.setParameter("username", request.username);

            User user = (User) query.getResultStream().findFirst().orElse(null);

            if (user == null) {
                request.respondError("Incorrect username or password.");
                return;
            }

            if (!Password.checkPassword(request.password, user.getPassword())) {
                request.respondError("Incorrect username or password.");
                return;
            }

            for (Connection connection : Server.get().getConnections()) {
                if (connection.getUser() != null && user.getUsername().equals(connection.getUser().getUsername())) {
                    request.respondError("Already logged in!");
                    return;
                }
            }

            request.getConnection().setUser(user);

            request.respond(new LoginSuccessfulResponse(request.getConnection().id));
        });
    }
}
