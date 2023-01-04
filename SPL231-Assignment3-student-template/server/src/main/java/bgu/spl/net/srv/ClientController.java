package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientController {
    public static ConcurrentHashMap<String, Client> clientsByName = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, Client> clientsByConnectionHandlerId = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, ConcurrentLinkedQueue<Client>> topics = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, String> topics_by_connectionID = new ConcurrentHashMap<>();
    public static volatile Integer counterReciept = 0;
}
