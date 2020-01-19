package cz.vse.java.shootme.common.net;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StateUpdate implements Serializable {

    public final String id = UUID.randomUUID().toString();

    public final List<EntityUpdate> addedEntities;

    public final List<EntityUpdate> updatedEntities;

    public final List<EntityUpdate> removedEntities;

    public StateUpdate(List<EntityUpdate> addedEntities, List<EntityUpdate> updatedEntities, List<EntityUpdate> removedEntities) {
        this.addedEntities = addedEntities;
        this.updatedEntities = updatedEntities;
        this.removedEntities = removedEntities;
    }

    public List<String> addedIds() {
        return addedEntities.stream().map(e -> e.id).collect(Collectors.toList());
    }

    public List<String> updatedIds() {
        return updatedEntities.stream().map(e -> e.id).collect(Collectors.toList());
    }

    public List<String> removedIds() {
        return removedEntities.stream().map(e -> e.id).collect(Collectors.toList());
    }
}
