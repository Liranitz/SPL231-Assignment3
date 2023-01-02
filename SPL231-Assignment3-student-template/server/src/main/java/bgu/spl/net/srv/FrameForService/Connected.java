package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;

public class Connected extends Frame {
    private String message;
    public Connected() {
        this.Type = "Connected";
        message = "Login successful";
    }
    @Override
    public String getType() {
        return Type;
    }
    public String getMessage(){
        return message;
    }
}

