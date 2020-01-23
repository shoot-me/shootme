package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.requests.ChangePasswordRequest;
import cz.vse.java.shootme.server.net.responses.ChangePasswordResponse;
import cz.vse.java.shootme.server.util.Password;

import javax.persistence.EntityManager;

public class ChangePassword {


    public ChangePassword(ChangePasswordRequest changePasswordRequest) {

        User user = changePasswordRequest.getConnection().getUser();

        if (!Password.checkPassword(changePasswordRequest.oldPassword, user.getPassword())) {
            changePasswordRequest.respondError("Incorrect password.");
            return;
        }

        if (Password.checkPassword(changePasswordRequest.newPassword, user.getPassword())) {
            changePasswordRequest.respondError("Old password can not be the same.");
            return;
        }

        EntityManager em = Database.getEntityManager();
        em.getTransaction().begin();

        try {
            user.setPassword(Password.hashPassword(changePasswordRequest.newPassword));

            em.merge(user);
            em.getTransaction().commit();

            changePasswordRequest.respond(new ChangePasswordResponse());
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            changePasswordRequest.respondError("Could not change password!");
        }

        em.close();

    }
}
