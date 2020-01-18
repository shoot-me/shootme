package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.net.requests.RegisterRequest;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.responses.RegisterSuccessfulResponse;

public class RegisterUser {

    public RegisterUser(RegisterRequest register) {
        User user = new User();
        user.setUsername(register.username);
        user.setPassword(register.password);

        if (user.save()) {
            register.respond(new RegisterSuccessfulResponse());
        } else {
            register.respondError("Registration error!");
        }
    }

}
