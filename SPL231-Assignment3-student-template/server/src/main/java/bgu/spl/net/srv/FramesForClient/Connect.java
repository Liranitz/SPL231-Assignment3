package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;

public class Connect extends Frame {
    private String name;
    private String password;
    private String AcceptVersion;
    public Connect(String version){
        this.AcceptVersion = version;
    }
    @Override
    public String getType() {
        return "CONNECT";
    }

    public String getName(){
        return name;
    }

    public String getAcceptVersion(){
        return AcceptVersion;
    }
    public String getPassword(){
        return password;
    }
}
