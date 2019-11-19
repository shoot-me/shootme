package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.common.requests.Registration;
import cz.vse.java.shootme.common.responses.Error;
import cz.vse.java.shootme.common.responses.Ok;
import cz.vse.java.shootme.server.models.User;

public class RegisterUser {

    public RegisterUser(Registration register) {
        User user = new User();
        user.setName(register.name);
        user.setPassword(register.password);
        boolean ok = user.save();

        if (ok) {
            register.respond(new Ok());
        } else {
            register.respond(new Error("Error"));
        }
    }

}
