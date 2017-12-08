package com.itugroup15.channelxAPI.model;

/**
 * Created by berke on 8.12.2017.
 */

public class Message{

    private String message;
    private String time;
    private String senderEmail; // TODO will change to user nick

    public Message(String message, String time, String senderEmail) {
        this.message = message;
        this.time = time;
        this.senderEmail = senderEmail;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

}
