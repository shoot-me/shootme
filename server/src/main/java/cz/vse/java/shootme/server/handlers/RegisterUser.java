package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.common.requests.RegisterRequest;
import cz.vse.java.shootme.common.responses.ErrorResponse;
import cz.vse.java.shootme.common.responses.OkResponse;
import cz.vse.java.shootme.server.models.User;

public class RegisterUser {

    public RegisterUser(RegisterRequest register) {
        User user = new User();
        user.setUsername(register.username);
        user.setPassword(register.password);

        if (user.save()) {
            register.respondOk();
        } else {
            register.respondError("Registration error!");
        }
    }

}
