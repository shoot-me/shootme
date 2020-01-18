package cz.vse.java.shootme.common;

import jeda00.eventbus.DefaultEventBus;

public class EventBus {

    private static jeda00.eventbus.EventBus eventBus;

    public synchronized static jeda00.eventbus.EventBus get() {
        if (eventBus == null) {
            eventBus = new DefaultEventBus();
        }

        return eventBus;
    }
}
