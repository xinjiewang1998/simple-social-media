package com.example.myapplication;

/**
 * Store the information receive from firebase.
 */
public class Chat {
    //Field
    private String sender;
    private String receiver;
    private String msg;

    //Constructor
    public Chat(String sender, String receiver, String msg) {
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
    }

    public Chat() {
    }

    // getter and setter
    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMsg() {
        return msg;
    }
}
