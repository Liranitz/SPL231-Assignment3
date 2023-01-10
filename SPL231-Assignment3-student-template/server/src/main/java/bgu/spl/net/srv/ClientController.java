package bgu.spl.net.srv;

import bgu.spl.net.srv.FramesForClient.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientController {
    protected static ConcurrentHashMap<String, Client> clientsByName = new ConcurrentHashMap<>(); // key=loginName of
                                                                                                  // the client, value=
                                                                                                  // clients- is it
                                                                                                  // nessecery?????
    protected static ConcurrentHashMap<Integer, Client> clientsByConnectionHandlerId = new ConcurrentHashMap<>(); // key=connectionHandlerId,
                                                                                                                  // value=
                                                                                                                  // clients
    protected static ConcurrentHashMap<String, ConcurrentHashMap<Integer, Client>> topics = new ConcurrentHashMap<>();// key=topics,
                                                                                                                      // value=
                                                                                                                      // map
                                                                                                                      // of
                                                                                                                      // Client
                                                                                                                      // (key=connectionHandlerId,
                                                                                                                      // value=
                                                                                                                      // client
    protected static ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, String>> subscribeIdByconnectionsHandlerId = new ConcurrentHashMap<>(); // key=connectionHandlerId,
                                                                                                                                                   // value=
                                                                                                                                                   // map
                                                                                                                                                   // of
                                                                                                                                                   // subscription
                                                                                                                                                   // unique
                                                                                                                                                   // to
                                                                                                                                                   // the
                                                                                                                                                   // connectionhnadleId(key=subscribeId
                                                                                                                                                   // value=
                                                                                                                                                   // the
                                                                                                                                                   // topic
                                                                                                                                                   // which
                                                                                                                                                   // subscribed
    protected static ConcurrentHashMap<String, String> userAndPassword = new ConcurrentHashMap<>(); // need to add evert
                                                                                                    // client that
                                                                                                    // conncted already
}
