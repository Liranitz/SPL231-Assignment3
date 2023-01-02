package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;

public class ClientController {
    public static ConcurrentHashMap<String, Client> clientsByName = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, Client> clientsByConnectionHandlerId = new ConcurrentHashMap<>();

    //public static
}
