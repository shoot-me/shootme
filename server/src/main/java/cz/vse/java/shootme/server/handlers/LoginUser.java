package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.net.Connection;
import cz.vse.java.shootme.server.net.Server;
import cz.vse.java.shootme.server.net.requests.LoginRequest;
import cz.vse.java.shootme.server.net.responses.LoginSuccessfulResponse;
import cz.vse.java.shootme.server.models.User;

public class LoginUser {

    public LoginUser(LoginRequest loginRequest) {
        User user = User.query().where("username", loginRequest.username).first();

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
