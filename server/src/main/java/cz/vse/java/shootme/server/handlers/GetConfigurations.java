package cz.vse.java.shootme.server.handlers;

import cz.vse.java.shootme.server.Database;
import cz.vse.java.shootme.server.models.Configuration;
import cz.vse.java.shootme.server.net.requests.GetConfigurationsRequest;
import cz.vse.java.shootme.server.net.responses.GetConfigurationsResponse;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GetConfigurations {

    public GetConfigurations(GetConfigurationsRequest request) {
        EntityManager em = Database.getEntityManager();

        Query query = em.createQuery("from Configuration", Configuration.class);

        List<Configuration> configurations = (List<Configuration>) query.getResultList();

        GetConfigurationsResponse response = new GetConfigurationsResponse();

        for (Configuration configuration : configurations) {
            response.configurations.put(configuration.getId(), configuration.getName() + ": " + configuration.getDuration());
        }

        em.close();

        request.respond(response);
    }

}
