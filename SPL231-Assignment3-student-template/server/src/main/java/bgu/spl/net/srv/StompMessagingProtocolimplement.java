package bgu.spl.net.srv;

import bgu.spl.net.api.*;

import bgu.spl.net.srv.FrameForService.Connected;
import bgu.spl.net.srv.FrameForService.Error;
import bgu.spl.net.srv.FrameForService.Message;
import bgu.spl.net.srv.FrameForService.Reciept;
import bgu.spl.net.srv.FramesForClient.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class StompMessagingProtocolimplement implements StompMessagingProtocol<Frame> {
    private ConnectionImpl<Frame> connectionsImpl;
    private int connectionHandlerId;
    private ClientController clientController;
    private int messageId;
  
   

    public void start(int connectionId, Connections<Frame> connections) {
        this.connectionsImpl = (ConnectionImpl<Frame>) connections;
        this.connectionHandlerId = connectionId;
        this.clientController = this.connectionsImpl.clientController;      
    }


    @Override
    public void process(Frame message) {
        Frame ret = null;
        String type = message.getType();
        // ClientController.counterReciep++;
        switch (type) {
            case "CONNECT":
                Connect curFrame = (Connect) message;
                String loggInName = curFrame.getName();
                Client client = clientController.clientsByName.get(loggInName);
                if (clientController.clientsByConnectionHandlerId.containsKey(connectionHandlerId)) {// already have a user
                    Error eror = new Error("already have a user for this client");
                    ret = eror;
                } else if (client == null) {// new user- need to registr him
                    Client newClient = new Client(loggInName, curFrame.getPassword()); // new user want to register
                    clientController.clientsByName.put(loggInName, newClient);
                    clientController.clientsByConnectionHandlerId.put(connectionHandlerId, newClient);
                    ret = new Connected();
                }

                else {// the client already exist
                    if (!curFrame.getPassword().equals(client.getPassword())) // check if the password is correct
                        ret = new Error("Wrong password");// wrong passowrd
                    else if (client.isConnected())// check if already connected
                        ret = new Error("User already logged in-(from another socket)");// allready logged in
                    else {// login and add his new connection handler to the map
                        client.setLogIn(true);
                        clientController.clientsByConnectionHandlerId.put(connectionHandlerId, client);
                        ret = new Connected();
                    }
                }
                break;

            case "UNSUBSCRIBE":
                Unsubscribe unsSub = (Unsubscribe) message;
                try {
                    int unSubId = unsSub.getSubId();
                    String unSubTopic = clientController.subscribeIdByconnectionsHandlerId.get(connectionHandlerId).get(unSubId);
                    clientController.topics.get(unSubTopic).remove(connectionHandlerId); // remove from the specific
                                                                                         // channel map
                    clientController.subscribeIdByconnectionsHandlerId.get(connectionHandlerId).remove(unSubId);// remove
                    Reciept rec = new Reciept(unsSub.getRecId());
                    ret = rec;
                } catch (Exception ex) {
                    Error eror = new Error("You did not subscribe to that destination\n\n" + message.toString());// nee more detailed
                                                                                        // erorr????????
                    ret = eror;
                }
                break;
            case "SUBSCRIBE":
                // A client try to subscribe to a topic , if the topic doesn't exist, create one.
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
                ConcurrentHashMap<Integer, Integer> clientsOfTopic = null;
                try {
                    clientsOfTopic = clientController.topics.get(topic);
                }
                catch (Exception exception){
                    ret = new Error("message : this topic is not exist\n\n" + message.toString()  );
                    int x = connectionHandlerId;
                    ConnectionHandler<Frame> cur = connectionsImpl.connection_Map.get(x);
                    try{
                    cur.close();
                    }
                    catch(IOException exp){
                        exp.printStackTrace();
                    }
                }

                 

                if (clientsOfTopic == null){
                    ret = new Error("message : this topic is not exist\n\n" + message.toString());
                }
                else if (!clientsOfTopic.containsKey(connectionHandlerId) ){
                    ret = new Error("you are not subscribe to this topic" + message.toString());
                }

                else {
                      // and now send message to everyone who subscribed this topic
                      for (Integer handlerId : clientsOfTopic.keySet()){
                         // if(handlerId != this.connectionHandlerId){
                            Message messageToSend = new Message(clientsOfTopic.get(handlerId), this.messageId, topic, body);
                            connectionsImpl.send(handlerId, messageToSend);
                           // }     
                      }                                                               
                messageId++;
                    }
                    // String curTopic = mes.getDestinatio();
                    // int messageReceipt = new int..
                    break;                

            case "DISCONNECT": // need to remove from all topics
                Disconnect disconnectMessage = (Disconnect) message;
                for (ConcurrentHashMap<Integer, Integer> topicMap : clientController.topics.values()) { // remove the user from each topic he belongs.
                    try {
                        topicMap.remove(connectionHandlerId);
                    } catch (NullPointerException exception) {
                        // not found in the list
                    }
                }
                clientController.subscribeIdByconnectionsHandlerId.remove(connectionHandlerId); // remove from the subscription list
                clientController.clientsByConnectionHandlerId.get(connectionHandlerId).setLogIn(false); // set his status not connected
                clientController.clientsByConnectionHandlerId.remove(connectionHandlerId);// remove from the list of the connection handler id
                
                Reciept rec = new Reciept(disconnectMessage.getId());
                ret = rec;
                break;

        }
        if (ret != null) {
            connectionsImpl.send(connectionHandlerId, ret);
        }

        

    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
