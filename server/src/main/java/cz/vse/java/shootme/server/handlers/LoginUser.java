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

    public LoginUser(LoginRequest loginRequest) {
        EntityManager em = Database.getEntityManager();

        try {
            em.getTransaction().begin();

            String hql = "from User U where U.username = :username";

            Query query = em.createQuery(hql, User.class);
            query.setParameter("username", loginRequest.username);

            User user = (User) query.getResultStream().findFirst().orElse(null);

            if (user == null) {
                loginRequest.respondError("Incorrect username or password.");
                return;
            }

            if (!Password.checkPassword(loginRequest.password, user.getPassword())) {
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
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            em.close();
        }
    }
}
