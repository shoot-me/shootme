package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.net.Connection;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.LoginRequest;
import cz.vse.java.shootme.server.net.responses.LoginSuccessfulResponse;
import cz.vse.java.shootme.server.models.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LoginUser {

    public LoginUser(LoginRequest loginRequest) {

        EntityManager em = Database.getEntityManager();

        String hql = "from User U where U.username = '" + loginRequest.username + "'";

        Query query = em.createQuery(hql, User.class);

        User user = (User) query.getSingleResult();


        if (user == null) {
            loginRequest.respondError("Incorrect username or password.");
            return;
        }

        if (!user.getPassword().equals(loginRequest.password)) {
            loginRequest.respondError("Incorrect username or password.");
            return;
        }

        for (Connection connection : Server.get().getConnections()) {
            if (connection.getUser() != null && user.getUsername().equals(connection.getUser().getUsername())) {
                loginRequest.respondError("Already logged in!");
                return;
            }
        }

        loginRequest.getConnection().setUser(user);

        loginRequest.respond(new LoginSuccessfulResponse(loginRequest.getConnection().id));
    }

}
