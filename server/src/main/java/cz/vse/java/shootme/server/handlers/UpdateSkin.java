package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.Skin;
import cz.vse.java.shootme.server.models.User;
import cz.vse.java.shootme.server.net.requests.UpdateSkinRequest;

import javax.persistence.Query;

/**
 * Method to set skin for user
 */
public class UpdateSkin {

    public UpdateSkin(UpdateSkinRequest request) {
        Database.transaction(request, em -> {
            Query query = em.createQuery("from Skin where name = :name");
            query.setParameter("name", request.name);

            Skin skin = (Skin) query.getResultStream().findFirst().orElse(null);
            if (skin == null) {
                request.respondError("No such skin!");
                return;
            }

            User user = request.getConnection().getUser();

            user.setSkin(skin);

            em.merge(user);

            request.respondSuccess("Skin changed successfully.");
        });
    }

}
