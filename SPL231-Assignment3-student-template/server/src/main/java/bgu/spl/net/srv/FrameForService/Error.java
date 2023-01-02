package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;

public class Error extends Frame {
    @Override
    public String getType() {
        return "Error";
    }
}
