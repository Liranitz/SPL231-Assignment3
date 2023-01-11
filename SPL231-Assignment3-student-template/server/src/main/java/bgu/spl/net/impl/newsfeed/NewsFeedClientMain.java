package bgu.spl.net.impl.newsfeed;

import bgu.spl.net.impl.rci.RCIClient;
import bgu.spl.net.srv.ConnectionImpl;
import bgu.spl.net.srv.Frame;
import bgu.spl.net.srv.FramesForClient.Subscribe;
import bgu.spl.net.srv.StompMessagingProtocolimplement;

public class NewsFeedClientMain {

    public static void main(String[] args) throws Exception {
        //if (args.length == 0) ;

//        System.out.println("running clients");
        //runFirstClient(args[0]);
        //runSecondClient(args[0]);
        //runThirdClient(args[0]);


        StompMessagingProtocolimplement cur = new StompMessagingProtocolimplement();
        ConnectionImpl<Frame> curCon = new ConnectionImpl();
        Subscribe sub = new Subscribe(  "jermany_spain", 78 , 1);
        //Subscribe sub = new Subscribe(  "jermany_spain", 78);
        cur.start(7777 , curCon);
        cur.process(sub);
        System.out.println();

    }

    private static void runFirstClient(String host) throws Exception {
        try (RCIClient c = new RCIClient(host, 7777)) {
            c.send(new PublishNewsCommand(
                    "jobs",
                    "System Programmer, knowledge in C++, Java and Python required. call 0x134693F"));

            c.receive(); //ok

            c.send(new PublishNewsCommand(
                    "headlines",
                    "new SPL assignment is out soon!!"));

            c.receive(); //ok

            c.send(new PublishNewsCommand(
                    "headlines",
                    "THE CAKE IS A LIE!"));

            c.receive(); //ok
        }

    }

    private static void runSecondClient(String host) throws Exception {
        try (RCIClient c = new RCIClient(host, 7777)) {
            c.send(new FetchNewsCommand("jobs"));
            System.out.println("second client received: " + c.receive());
        }
    }

    private static void runThirdClient(String host) throws Exception {
        try (RCIClient c = new RCIClient(host, 7777)) {
            c.send(new FetchNewsCommand("headlines"));
            System.out.println("third client received: " + c.receive());
        }
    }
}
