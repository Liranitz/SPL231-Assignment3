package bgu.spl.net.srv;

import bgu.spl.net.api.*;
import bgu.spl.net.impl.echo.LineMessageEncoderDecoder;
import bgu.spl.net.srv.Frame;
import bgu.spl.net.srv.FrameForService.Connected;
import bgu.spl.net.srv.FrameForService.Error;
import bgu.spl.net.srv.FrameForService.Message;
import bgu.spl.net.srv.FrameForService.Receipt;
import bgu.spl.net.srv.FramesForClient.*;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StompMessagingProtocolimplement implements StompMessagingProtocol<Frame> {
    private ConnectionImpl<Frame> connectionsImpl;
    // curId - connection handler ID
    private int connectionHandlerId;
    private String[] curArrayMessage;

    public void start(int connectionId, Connections connections) {
        this.connectionsImpl = (ConnectionImpl) connections;
        this.connectionHandlerId = connectionId;
        curArrayMessage = null;
    }

    @Override
    public void process(Frame message) {
        Frame ret = null;
        String type = message.getType();
        // ClientController.counterReciep++;
        switch (type) {
            case "CONNECT":
                Connect curFrame = (Connect) message;
                Client cl = ClientController.clientsByName.get(curFrame.getName());
                if (ClientController.clientsByConnectionHandlerId.contains(connectionHandlerId)) {// already have a user
                    Error eror = new Error("already have a user for this client");
                    ret = eror;
                }

                else if (cl == null) {// new user- need to registr him
                    Client newClient = new Client(curFrame.getName(), curFrame.getPassword()); // new user want to
                                                                                               // register
                    ClientController.clientsByName.put(curFrame.getName(), newClient);
                    ClientController.clientsByConnectionHandlerId.put(connectionHandlerId, newClient);
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
                    int unSubId = unsSub.getCurId();
                    String unSubTopic = ClientController.subscribeIdByconnectionsHandlerId.get(connectionHandlerId)
                            .get(unSubId);
                    ClientController.topics.get(unSubTopic).remove(connectionHandlerId); // remove from the specific
                                                                                         // channel map
                    ClientController.subscribeIdByconnectionsHandlerId.get(connectionHandlerId).remove(unSubId);// remove
                                                                                                                // from
                                                                                                                // the
                                                                                                                // map
                                                                                                                // subscripion
                                                                                                                // of
                                                                                                                // the
                                                                                                                // client
                    Receipt rec = new Receipt(unSubId);
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

                // 1. ensure that the client exist- is it nessecery????
                Client curClient = null;
                try {
                    curClient = ClientController.clientsByConnectionHandlerId.get(connectionHandlerId);
                } catch (NullPointerException exception) {
                    // return new error with the description that there is no client exist- or maybe
                    // this check do not occur here?
                }

                // 2. make a new channel (topic) in case that not exist
                String curTopic = sub.getDestination();
                if (!ClientController.topics.containsKey(curTopic)) {
                    ClientController.topics.put(curTopic, new ConcurrentHashMap<>());
                }

                // 3. add the client to the queue which represented by topics
                if (curClient != null)
                    ClientController.topics.get(curTopic).put(connectionHandlerId, curClient);

                // 4. add the the subscribeId to the list of the client subscription
                int subId = sub.getId();
                try {
                    ClientController.subscribeIdByconnectionsHandlerId.get(connectionHandlerId).put(subId, curTopic);
                } catch (NullPointerException exception) {// his first subscribed not have any subscrition- add the user
                                                          // to the map to the map
                    ConcurrentHashMap<Integer, String> subscriptions = new ConcurrentHashMap<>();
                    subscriptions.put(subId, curTopic);
                    ClientController.subscribeIdByconnectionsHandlerId.put(connectionHandlerId, subscriptions);
                }
                ret = new Receipt(subId);
                break;

            case "SEND":
                Send sendMessage = (Send) message;
                // gets a send frame from a client and now send message to everyone
                Message mes = (Message) message;
                // String curTopic = mes.getDestinatio();
                // int messageReceipt = new int..
                break;

            case "DISCONNECT": // need to remove from all topics
                Disconnect disconnectMessage = (Disconnect) message;
                ConcurrentHashMap<Integer, String> subscriptions = ClientController.subscribeIdByconnectionsHandlerId
                        .get(connectionHandlerId);
                for (ConcurrentHashMap<Integer, Client> topicMap : ClientController.topics.values()) { // remove the
                                                                                                       // user from each
                                                                                                       // topic he
                                                                                                       // belongs.
                    try {
                        topicMap.remove(connectionHandlerId);
                    } catch (NullPointerException exception) {
                        // not found in the list
                    }
                }
                ClientController.subscribeIdByconnectionsHandlerId.remove(connectionHandlerId); // remove from the
                                                                                                // subscription list
                Receipt rec = new Receipt(disconnectMessage.getId());
                ret = rec;
                break;

        }
        connectionsImpl.send(connectionHandlerId, ret);
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
