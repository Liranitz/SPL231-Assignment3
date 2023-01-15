package bgu.spl.net.srv;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionImpl<T> implements Connections<T>{
    protected ConcurrentHashMap<Integer , ConnectionHandler<T>> connection_Map; // conne
    protected ClientController clientController;

    public ConnectionImpl(){
        this.connection_Map = new ConcurrentHashMap<>();
        clientController = new ClientController();
    }


    @Override
    public boolean send(int connectionId, T msg) { // connect function
        ConnectionHandler<T> temp = connection_Map.get(connectionId);
        if (temp != null) {
            temp.send(msg);
            if(msg instanceof Error){
                try{
                temp.close();
                }
                catch(IOException exp){
                    exp.printStackTrace();
                }
            }
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

    public void addNewConnectionHandler(int key, ConnectionHandler<T> handler) {
        connection_Map.putIfAbsent(key, handler);
    }
}
