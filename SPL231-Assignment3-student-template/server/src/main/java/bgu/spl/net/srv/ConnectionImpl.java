package bgu.spl.net.srv;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionImpl<T> implements Connections<T>{
    private ConcurrentHashMap<Integer , ConnectionHandler> connection_Map; // conne

    public ConnectionImpl(){
        this.connection_Map = new ConcurrentHashMap<>();
    }


    @Override
    public boolean send(int connectionId, T msg) { // connect function
        ConnectionHandler temp = connection_Map.get(connectionId);
        if (temp != null) {
            temp.send(msg.toString());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void send(String channel, T msg) { // regular

    }

    @Override
    public void disconnect(int connectionId) { //

    }

    public void addNewConnectionHandler(int key, ConnectionHandler handler) {
        connection_Map.putIfAbsent(key, handler);
    }
}
