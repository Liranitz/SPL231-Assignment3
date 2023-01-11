package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.srv.FrameForService.Error;
import bgu.spl.net.srv.FramesForClient.*;

import java.beans.Encoder;
import java.nio.charset.StandardCharsets;
import java.rmi.server.ExportException;
import java.util.Arrays;

public class EncoderDecoderImplement implements MessageEncoderDecoder<Frame> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    private int len = 0;

    @Override
    public Frame decodeNextByte(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == '\u0000') {
            return parseStringToFrame(popString());
        }

        pushByte(nextByte);
        return null; //not a line yet
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }

    private String popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }



    public Frame parseStringToFrame(String nextString) {
        System.out.println(nextString);
        if (nextString != null && nextString != "null") {
            String[] frameParts = nextString.split("\n");
            int index = 0;
            String type = frameParts[0];

            try {
             if (type.equals("CONNECT"))
                return new Connect(frameParts[1].split(":")[1], frameParts[2].split(":")[1], frameParts[3].split(":")[1], frameParts[4].split(":")[1]);

            if (type.equals("DISCONNECT"))
                return new Disconnect(Integer.parseInt(frameParts[1].split(":")[1]));

            if (type.equals("SEND")){
                String destination = frameParts[1].split(":")[1];
                int startIndexOfBody = nextString.indexOf("\n", nextString.indexOf("\n") + 1) + 1;
                String body = nextString.substring(startIndexOfBody);
                return new Send(destination, body);
                
               
            }
                

            if (type.equals("SUBSCRIBE"))
                return new Subscribe( frameParts[1].split(":")[1], Integer.parseInt(frameParts[2].split(":")[1]), Integer.parseInt(frameParts[3].split(":")[1]));

            if (type.equals("UNSUBSCRIBE"))
                return new Unsubscribe(Integer.parseInt(frameParts[1].split(":")[1]), Integer.parseInt(frameParts[2].split(":")[1]));
            }
            catch(Exception exp){// צריך להרחיב על הארור??????
                return new Error("wrong message");
            }
            }

            
        return null; //need to return erorr??????????????
        }


            @Override
            public byte[] encode (Frame message){
                return (message.toString() + "\n").getBytes();
            }

    public byte[] encode (String message){
        byte[] a = message.toString().getBytes();
        byte[] b = (message.toString() + "\n").getBytes();
        return (message.toString() + "\n").getBytes();
    }

        }

