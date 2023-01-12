package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;


public class ClientController {
    protected  ConcurrentHashMap<String, Client> clientsByName; // key=loginName of the client, value=clients- is it nessecery?????
    protected ConcurrentHashMap<Integer, Client> clientsByConnectionHandlerId; // key=connectionHandlerId, value=clients
    protected  ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> topics;// key=topics, value=map of Client (key=connectionHandlerId, value= subscriptionId
    protected  ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, String>> subscribeIdByconnectionsHandlerId; // key=connectionHandlerId, value= map of subscription unique to the connectionhnadleId(key=subscribeId value= the topic which subscribed
   // protected  ConcurrentHashMap<String, String> userAndPassword; // need to add every client that conncted already

public  ClientController (){
    this.clientsByConnectionHandlerId = new ConcurrentHashMap<>();
    this.clientsByName = new ConcurrentHashMap<>();
    this.subscribeIdByconnectionsHandlerId = new ConcurrentHashMap<>();
    this.topics = new ConcurrentHashMap<>();
}
}
