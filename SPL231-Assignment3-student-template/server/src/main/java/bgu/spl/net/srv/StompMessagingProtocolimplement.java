package bgu.spl.net.srv;
import bgu.spl.net.api.*;
import bgu.spl.net.impl.echo.LineMessageEncoderDecoder;
import bgu.spl.net.srv.Frame;
import bgu.spl.net.srv.FrameForService.Connected;
import bgu.spl.net.srv.FrameForService.Error;
import bgu.spl.net.srv.FrameForService.Receipt;
import bgu.spl.net.srv.FramesForClient.Connect;
import bgu.spl.net.srv.FramesForClient.Subscribe;

import java.util.concurrent.ConcurrentLinkedQueue;

public class StompMessagingProtocolimplement implements StompMessagingProtocol<Frame>{
    private ConnectionImpl<Frame> connectionsImpl;
    // curId - connection handler ID
    private int curId;
    private String[] curArrayMessage;
    private

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
                Connect curFrame = (Connect) message;
                Client cl = ClientController.clientsByName.get(curFrame.getName());
                if (cl != null){
                   if(curFrame.getPassword() == cl.getPassword()){//check if the password is correct
                       if (!cl.getStat()){//check if the user logged in
                           cl.setStat(true);
                           ret = new Connected();
                       }
                       else{
                           ret = new Error ("The client is already logged in, log out before trying again");
                       }
                   }
                   else
                       ret = new Error ("Wrong password");
                }

                else{//new user
                    Client newClient = new Client(curFrame.getName(), curFrame.getPassword());
                    ClientController.clientsByName.put(curFrame.getName(), newClient);
                    ClientController.clientsByConnectionHandlerId.put(curId, newClient);
                    ret = new Connected();
                }


            case "SUBSCRIBE":
                //A client try to subscribe to a topic , if the topic doesn't exist, create one.
                Subscribe sub = (Subscribe) message;
                String curTopic = sub.getDestination();
                if (!ClientController.topics.containsKey(curTopic)) {
                    ClientController.topics.put(curTopic, new ConcurrentLinkedQueue<>());
                }
                //figure out what to do with the id
                int id = sub.getId();
                ClientController.topics.get(curTopic).add(ClientController.clientsByConnectionHandlerId.get(curId));
                ConcurrentLinkedQueue<Client> clientsQueue = ClientController.topics.get(sub.getDestination());

                if (clientsQueue != null){// if it is null??????
                    if (clientsQueue.contains())

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
