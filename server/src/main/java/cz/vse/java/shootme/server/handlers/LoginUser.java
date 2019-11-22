package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.common.requests.LoginRequest;
import cz.vse.java.shootme.common.responses.LoginSuccessfulResponse;
import cz.vse.java.shootme.server.models.User;

import java.util.UUID;

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

        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.save();

        loginRequest.respond(new LoginSuccessfulResponse(token));
    }

}
