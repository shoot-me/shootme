package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.requests.ChangePasswordRequest;
import cz.vse.java.shootme.server.net.responses.ChangePasswordResponse;
import cz.vse.java.shootme.server.util.Password;

import javax.persistence.EntityManager;

/**
 * Method to change user's password
 */
public class ChangePassword {


    public ChangePassword(ChangePasswordRequest request) {

        User user = request.getConnection().getUser();

        if (!Password.checkPassword(request.oldPassword, user.getPassword())) {
            request.respondError("Incorrect password.");
            return;
        }

        if (Password.checkPassword(request.newPassword, user.getPassword())) {
            request.respondError("Old password can not be the same.");
            return;
        }

        boolean ok = Database.transaction(request, em -> {

            user.setPassword(Password.hashPassword(request.newPassword));

            em.merge(user);


        });

        if (ok) {
            request.respondSuccess("Password changed succesfully");
        }


    }
}
