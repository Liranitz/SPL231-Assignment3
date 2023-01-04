package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.beans.Encoder;

public class EncoderDecoderImplement implements MessageEncoderDecoder<Frame> {

    @Override
    public Frame decodeNextByte(byte nextByte) {
        return null;
    }

    @Override
    public byte[] encode(Frame message) {
        return new byte[0];
    }
}
