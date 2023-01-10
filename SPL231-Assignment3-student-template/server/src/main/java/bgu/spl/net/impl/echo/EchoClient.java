package bgu.spl.net.impl.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) throws IOException {
        char ch = '\0';
        if (args.length == 0) {
            args = new String[3];
            args[0] = "127.0.0.1";

            /*args[1] = "UNSUBSCRIBE\n" +
                    "id :1\n" +
                    "destination : / dest" +
                    Character.toString(ch);*/
            /*args[1] =



           /* args[1] = "UNSUBSCRIBE\n" +
                    "id :78\n" +
                    Character.toString(ch);*/


            /*args[1] = "DISCONNECT\n" +
                    "receipt :113\n" +
                    Character.toString(ch);*/
        }
       /* if (args.length < 2) {
            System.out.println("you must supply two arguments: host, message");
            System.exit(1);
        }*/

        String []array = new String[10];
        array[0] = "CONNECT\n" +
                "accept - version :1.2\n" +
                "host : stomp . cs . bgu . ac . il\n" +
                "login : menni\n" +
                "passcode : films6" +
                Character.toString(ch);

        array[1] = "CONNECT\n" +
                "accept - version :1.2\n" +
                "host : stomp . cs . bgu . ac . il\n" +
                "login : menni\n" +
                "passcode : films6" +
                Character.toString(ch);
        array[2] = "SUBSCRIBE\n" +
                "destination :/ topic / a\n" +
                "id :78\n" +
                Character.toString(ch);;

        //BufferedReader and BufferedWriter automatically using UTF-8 encoding
        int count =0;
        try (Socket sock = new Socket(args[0], 7777);
             BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))) {
        while (true && count<10){
                System.out.println("sever start");
                args[1] = array[count];
                count++;
                out.write(args[1]);
                out.newLine();
                out.flush();

                System.out.println("awaiting response");
                StringBuilder messageBuilder = new StringBuilder();
                String line = in.readLine();
                for (int i=0; i<1; i++){
                    if (line != null)
                        messageBuilder.append(line + '\n');
                    line = in.readLine();
                }
                System.out.println("message from server: " + messageBuilder);
                }
        }
        catch (SocketException socketEror){
            System.out.println("Could not connect to server"); ///need to be something like that in the clien implimentation
        }

    }
}
