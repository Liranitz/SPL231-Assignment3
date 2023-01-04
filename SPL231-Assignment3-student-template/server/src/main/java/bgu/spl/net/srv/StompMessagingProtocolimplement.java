package bgu.spl.net.srv;
import bgu.spl.net.api.*;
import bgu.spl.net.impl.echo.LineMessageEncoderDecoder;
import bgu.spl.net.srv.Frame;
import bgu.spl.net.srv.FrameForService.Connected;
import bgu.spl.net.srv.FrameForService.Error;
import bgu.spl.net.srv.FrameForService.Message;
import bgu.spl.net.srv.FrameForService.Receipt;
import bgu.spl.net.srv.FramesForClient.Connect;
import bgu.spl.net.srv.FramesForClient.Send;
import bgu.spl.net.srv.FramesForClient.Subscribe;

import java.util.concurrent.ConcurrentLinkedQueue;

public class StompMessagingProtocolimplement implements StompMessagingProtocol<Frame>{
    private ConnectionImpl<Frame> connectionsImpl;
    // curId - connection handler ID
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
        ClientController.counterReciept++;
        switch (curStringMessage) {
            case "CONNECT":
                Connect curFrame = (Connect) message;
                Client cl = ClientController.clientsByName.get(curFrame.getName());
                if (cl != null) {
                    if (curFrame.getPassword() == cl.getPassword()) {//check if the password is correct
                        if (!cl.getStat()) {//check if the user logged in
                            cl.setStat(true);
                            ret = new Connected();
                        } else {
                            ret = new Error("The client is already logged in, log out before trying again");
                        }
                    } else
                        ret = new Error("Wrong password");
                } else {//new user
                    Client newClient = new Client(curFrame.getName(), curFrame.getPassword());
                    ClientController.clientsByName.put(curFrame.getName(), newClient);
                    ClientController.clientsByConnectionHandlerId.put(curId, newClient);
                    ret = new Connected();
                }
                break;

                case "SUBSCRIBE":
                    //A client try to subscribe to a topic , if the topic doesn't exist, create one.
                    Subscribe sub = (Subscribe) message;
                    String curTopic = sub.getDestination();
                    if (!ClientController.topics.containsKey(curTopic)) {
                        ClientController.topics.put(curTopic, new ConcurrentLinkedQueue<>());
                    }
                    int id = sub.getId();
                    //add the client to the queue
                    Client curClient = null;
                    try {
                        curClient = ClientController.clientsByConnectionHandlerId.get(curId);
                    } catch (NullPointerException exception) {
                        //return new error with the description that there is no client exist
                    }
                    if (curClient != null)
                        try {
                            ClientController.topics.get(curTopic).add(curClient);
                        } catch (NullPointerException exception) {
                            //return new error with the description that there is no topic exist
                        }
                    //add the connection between the connection handler too the topic
                    ClientController.topics_by_connectionID.put(id, curTopic);
                    ret = new Receipt(ClientController.counterReciept);
                    break;

                case "SEND":
                    Send sendMessage = (Send) message;
                    // gets a send frame from a client and now send message to everyone
                    Message mes = (Message) message;
                    //String curTopic = mes.getDestinatio();
                    //int messageReceipt = new int..
                    break;


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
