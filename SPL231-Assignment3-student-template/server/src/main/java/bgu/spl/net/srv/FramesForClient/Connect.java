package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;

public class Connect extends Frame {
    private String nameLogin;
    private String password;
    private String host;
    private String AcceptVersion;
    public Connect(String version, String host, String name, String passcode ){
        this.AcceptVersion = version;
        this.host = host;
        this.nameLogin = name;
        this.password = passcode;
    }
    @Override
    public String getType() {
        return "CONNECT";
    }

    public String getName(){
        return nameLogin;
    }

    public String getAcceptVersion(){
        return AcceptVersion;
    }
    public String getPassword(){
        return password;
    }

    @Override
    public String toString() {
        return null;
    }
}
