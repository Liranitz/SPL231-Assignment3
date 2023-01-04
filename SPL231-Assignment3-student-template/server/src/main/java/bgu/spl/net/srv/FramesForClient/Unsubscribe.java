package bgu.spl.net.srv.FramesForClient;

import bgu.spl.net.srv.Frame;


public class Unsubscribe extends Frame {
    private int curId;

    public  Unsubscribe(int id){
        this.curId = id;
    }
    public int getCurId() {
        return curId;
    }

    @Override
    public String getType() {
        return null;
    }

}
