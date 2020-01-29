package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.Configuration;
import cz.vse.java.shootme.server.net.requests.CreateConfigRequest;

public class CreateConfig {

    public CreateConfig(CreateConfigRequest request) {
        boolean ok = Database.transaction(em -> {
            Configuration configuration = new Configuration();
            configuration.setName(request.name);
            configuration.setDuration(request.duration);
            em.persist(configuration);
        });

        if (ok) {
            request.respondSuccess("Configuration created successfully!");
        } else {
            request.respondError("Configuration was not created!");
        }
    }

}
