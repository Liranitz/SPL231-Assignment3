package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;

public class Message extends Frame {
    private String subscription;
    private Integer messageId;
    private String destination;
    @Override
    //public
    public String getType() {
        return "MESSAGE";
    }

    @Override
    public String toString() {
        return null;
    }
}
