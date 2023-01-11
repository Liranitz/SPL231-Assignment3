package bgu.spl.net.srv;

import bgu.spl.net.api.*;
import bgu.spl.net.impl.echo.LineMessageEncoderDecoder;
import bgu.spl.net.srv.Frame;
import bgu.spl.net.srv.FrameForService.Connected;
import bgu.spl.net.srv.FrameForService.Error;
import bgu.spl.net.srv.FrameForService.Message;
import bgu.spl.net.srv.FrameForService.Reciept;
import bgu.spl.net.srv.FramesForClient.*;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StompMessagingProtocolimplement implements StompMessagingProtocol<Frame> {
    private ConnectionImpl<Frame> connectionsImpl;
    // curId - connection handler ID
    private int connectionHandlerId;
    private String[] curArrayMessage;
    private ClientController clientController;
    private int messageId;
    

    public void start(int connectionId, Connections connections) {
        this.connectionsImpl = (ConnectionImpl) connections;
        this.connectionHandlerId = connectionId;
        curArrayMessage = null;
        this.messageId = 1;      
    }

    public void initializeController(ClientController clientController) {
        this.clientController = clientController;
    }



    @Override
    public void process(Frame message) {
        Frame ret = null;
        int i=1;
        String type = message.getType();
        // ClientController.counterReciep++;
        switch (type) {
            case "CONNECT":
                Connect curFrame = (Connect) message;
                Client cl = clientController.clientsByName.get(curFrame.getName());
                if (clientController.clientsByConnectionHandlerId.containsKey(connectionHandlerId)) {// already have a user
                    Error eror = new Error("already have a user for this client");
                    ret = eror;
                }

                else if (cl == null) {// new user- need to registr him
                    Client newClient = new Client(curFrame.getName(), curFrame.getPassword()); // new user want to
                                                                                               // register
                    clientController.clientsByName.put(curFrame.getName(), newClient);
                    clientController.clientsByConnectionHandlerId.put(connectionHandlerId, newClient);
                    ret = new Connected();
                }

                else {// the client already exist
                    if (!curFrame.getPassword().equals(cl.getPassword())) // check if the password is correct
                        ret = new Error("Wrong password");// wrong passowrd
                    else if (cl.isConnected())// check if already connected
                        ret = new Error("User already logged in");// alredy logged in
                    else {// login
                        cl.setStat(true);
                        ret = new Connected();
                    }
                }
                break;

                

            case "UNSUBSCRIBE":
                Unsubscribe unsSub = (Unsubscribe) message;
                try {
                    int unSubId = unsSub.getSubId();
                    String unSubTopic = clientController.subscribeIdByconnectionsHandlerId.get(connectionHandlerId)
                            .get(unSubId);
                    clientController.topics.get(unSubTopic).remove(connectionHandlerId); // remove from the specific
                                                                                         // channel map
                    clientController.subscribeIdByconnectionsHandlerId.get(connectionHandlerId).remove(unSubId);// remove
                    Reciept rec = new Reciept(unsSub.getRecId());
                    ret = rec;
                } catch (Exception ex) {
                    Error eror = new Error("You did not subscribe to that destination");// nee more detailed
                                                                                        // erorr????????
                    ret = eror;
                }
                break;
            case "SUBSCRIBE":
                // A client try to subscribe to a topic , if the topic doesn't exist, create
                // one.
                Subscribe sub = (Subscribe) message;
                int subId = sub.getsubId();
                int recId = sub.getrecId();

                // 1. ensure that the client exist- is it nessecery????
                Client curClient = null;
                try {
                    curClient = clientController.clientsByConnectionHandlerId.get(connectionHandlerId);
                } catch (NullPointerException exception) {
                    // return new error with the description that there is no client exist- or maybe
                    // this check do not occur here?
                }

                // 2. make a new channel (topic) in case that not exist
                String curTopic = sub.getDestination();
                if (!clientController.topics.containsKey(curTopic)) {
                    clientController.topics.put(curTopic, new ConcurrentHashMap<>());
                }

                // 3. add the client to the queue which represented by topics
                if (curClient != null)
                    clientController.topics.get(curTopic).put(connectionHandlerId, subId);

                // 4. add the the subscribeId to the list of the client subscription
                try {
                    clientController.subscribeIdByconnectionsHandlerId.get(connectionHandlerId).put(subId, curTopic);
                } catch (NullPointerException exception) {// his first subscribed not have any subscrition- add the user
                                                          // to the map to the map
                    ConcurrentHashMap<Integer, String> subscriptions = new ConcurrentHashMap<>();
                    subscriptions.put(subId, curTopic);
                    clientController.subscribeIdByconnectionsHandlerId.put(connectionHandlerId, subscriptions);
                }
                ret = new Reciept(recId);
                break;

            case "SEND": // gets a send frame from a client
                   Send sendMessage = (Send) message;
                    String topic = sendMessage.getDestination();
                    String body = sendMessage.getBody();
                   ConcurrentHashMap<Integer, Integer> clientsOfTopic = clientController.topics.get(topic);
                //check that the client has subscribes to this topic
                if (!(clientsOfTopic.containsKey(connectionHandlerId))){
                    ret = new Error("please subscribe to this topic before you sendin a message");
                }

                else{
                     //and now send message to everyone who subscribed this topic
                    for (Integer handlerId : clientsOfTopic.keySet()) {
                        Message messageToSend = new Message( clientsOfTopic.get(handlerId), this.messageId, topic, body);
                        connectionsImpl.send(handlerId, messageToSend);
                    }
                    messageId++;
                    // String curTopic = mes.getDestinatio();
                    // int messageReceipt = new int..
                }                
                break;

            case "DISCONNECT": // need to remove from all topics
                Disconnect disconnectMessage = (Disconnect) message;
                ConcurrentHashMap<Integer, String> subscriptions = clientController.subscribeIdByconnectionsHandlerId
                        .get(connectionHandlerId);
                for (ConcurrentHashMap<Integer, Integer> topicMap : clientController.topics.values()) { // remove the
                                                                                                       // user from each
                                                                                                       // topic he
                                                                                                       // belongs.
                    try {
                        topicMap.remove(connectionHandlerId);
                    } catch (NullPointerException exception) {
                        // not found in the list
                    }
                }
                clientController.subscribeIdByconnectionsHandlerId.remove(connectionHandlerId); // remove from the
                                                                                                // subscription list
                Reciept rec = new Reciept(disconnectMessage.getId());
                ret = rec;
                break;

        }
        if (ret != null){
            connectionsImpl.send(connectionHandlerId, ret);
        }
        
    }

    private void messageToObject(String message) {
        // curArrayMessage convert to object style
        // delete the last line
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
