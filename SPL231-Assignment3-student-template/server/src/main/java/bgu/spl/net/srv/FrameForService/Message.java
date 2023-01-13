package bgu.spl.net.srv.FrameForService;

import bgu.spl.net.srv.Frame;

public class Message extends Frame {
    private int subscriptionId;
    private Integer messageId;
    private String destination;
    private String body ;


    public  Message(int subId, int messId, String topic, String body){
        this.subscriptionId = subId;
        this.messageId = messId;
        this.destination = topic;
        this.body = body;
        this.Type = "MESSAGE";

    }
    @Override
    public String getType() {
        return this.Type;
    }


    @Override
    public String toString() {
        return "MESSAGE\n"+
        "subscription:"+subscriptionId+ '\n' +
        "message-id:"+messageId+ '\n' +
        "destination:"+destination+ '\n' +
         body  + '\n' + 
        '\u0000';
    }
}
