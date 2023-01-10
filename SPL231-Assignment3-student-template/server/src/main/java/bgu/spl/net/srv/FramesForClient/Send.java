package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;


public class Send extends Frame {
    private String destination;
    private String body;
    public Send(String destination, String body){
        this.destination = destination;
        this.body = body;
    }
    @Override
    public String getType() {
        return "SEND";
    }

    
    public String getDestination() {
        return destination;
    }
    
    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return null;
    }
}
