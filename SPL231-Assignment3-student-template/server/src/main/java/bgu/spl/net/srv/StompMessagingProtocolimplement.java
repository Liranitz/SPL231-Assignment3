package bgu.spl.net.srv;
import bgu.spl.net.api.*;
import bgu.spl.net.impl.echo.LineMessageEncoderDecoder;
import bgu.spl.net.srv.Frame;
import bgu.spl.net.srv.FrameForService.Connected;
import bgu.spl.net.srv.FramesForClient.Connect;

public class StompMessagingProtocolimplement implements StompMessagingProtocol<Frame>{
    private ConnectionImpl<Frame> connectionsImpl;
    private int curId;
    private String[] curArrayMessage;

    public void start(int connectionId, Connections connections) {
        this.connectionsImpl = (ConnectionImpl) connections;
        this.curId = connectionId;
        curArrayMessage = null;
    }

    @Override
    public void process(Frame message) {
        Frame ret = null;
        String curStringMessage = message.getType();
        //String stat = curArrayMessage[0];
        switch (curStringMessage){
            case "CONNECT":
                Connect cur = (Connect) message;
                if(ClientController.clientsByName.get(cur.getName()) != null){
                    // let password be ok
                    // change to log in
                    ret = new Connected();
                }
                else{
                    //ret = new Error();
                }

            // make a new client and send CONNECTED STOMP or send an error message
                // if he already exist and not logged in : log him in only if password is ok
                // else log him in
                // if not exist add to a list
            //connectionsImpl.send(curId ,);

            case "SUBSCRIBE":
                //

        }
        connectionsImpl.send(curId, ret);
    }

    private void messageToObject(String message){
       // curArrayMessage convert to object style
        // delete the last line
    }



    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
