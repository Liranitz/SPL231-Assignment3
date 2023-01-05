package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;


public class Send extends Frame {
    private String destination;
    public Send(String destination){
        this.destination = destination;
    }
    @Override
    public String getType() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
