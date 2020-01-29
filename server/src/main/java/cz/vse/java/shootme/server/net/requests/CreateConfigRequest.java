package cz.vse.java.shootme.server.net.requests;

public class CreateConfigRequest extends Request {

    public final String name;

    public final int duration;

    public CreateConfigRequest(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

}
