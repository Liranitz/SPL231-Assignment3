package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;

public class Connect extends Frame {
    private String name;
    private String password;
    public Connect(){

    }
    @Override
    public String getType() {
        return "CONNECT";
    }

    public String getName(){
        return "";
    }

    public String getAcceptVersion(){
        return "version 1.2";
    }
    public String getPassword(){
        return "";
    }
}
