package cz.vse.java.shootme.server.net.responses;

import java.io.Serializable;
import java.util.UUID;

public abstract class Response implements Serializable {

    public final String id = UUID.randomUUID().toString();

}
