package cz.vse.java.shootme.server.net.requests;

public class UpdateSkinRequest extends Request {

    public final String name;

    public UpdateSkinRequest(String name) {
        this.name = name;
    }

}
