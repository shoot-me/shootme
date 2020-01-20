package cz.vse.java.shootme.common.net;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StateUpdate implements Serializable {

    public final String id = UUID.randomUUID().toString();

    public final List<EntityUpdate> entityUpdates;

    public final List<String> playerInfo;

    public StateUpdate(List<EntityUpdate> entityUpdates, List<String> playerInfo) {
        this.entityUpdates = entityUpdates;
        this.playerInfo = playerInfo;
    }

    public EntityUpdate findEntityUpdateById(String id) {
        for (EntityUpdate entityUpdate : entityUpdates) {
            if (entityUpdate.id.equals(id)) {
                return entityUpdate;
            }
        }

        return null;
    }

    public List<String> entityIds() {
        return entityUpdates.stream().map(e -> e.id).collect(Collectors.toList());
    }
}
