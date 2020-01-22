package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.net.requests.RegisterRequest;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.responses.RegisterSuccessfulResponse;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class RegisterUser {

    public RegisterUser(RegisterRequest register) {


        EntityManager em = Database.getEntityManager();

        Query query = em.createQuery("from User u where u.username = '" + register.username + "'");

        List result = query.getResultList();

        if (result.isEmpty()) {

            em.getTransaction().begin();

            User user = new User();
            user.setUsername(register.username);
            user.setPassword(register.password);
            em.persist(user);

            em.getTransaction().commit();

            em.close();


            //TODO zjistit jestli byla transakce úspěsná

            if (true) {

                register.respond(new RegisterSuccessfulResponse());
            } else {
                register.respondError("Registration error!");
                return;
            }


        } else {
            register.respondError("This username already exists!");
        }

    }

}
