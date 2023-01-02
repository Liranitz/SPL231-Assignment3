package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;

public class Message extends Frame {
    @Override
    public String getType() {
        return "MESSAGE";
    }
}
