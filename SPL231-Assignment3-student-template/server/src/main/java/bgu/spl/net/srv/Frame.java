package bgu.spl.net.srv;

public abstract class Frame {
    protected String Type; // each one has is one name
    public abstract String getType();
    public abstract String toString();
}