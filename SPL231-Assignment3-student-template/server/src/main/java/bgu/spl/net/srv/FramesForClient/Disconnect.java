package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;


public class Disconnect extends Frame {

    private int id;
    public Disconnect (int id){
        this.id = id;
    }
    @Override
    public String getType() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    public int getId() {
        return id;
    }
}
