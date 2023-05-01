package com.medeasy.chatsocket;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String from;
    private String to;
    private String text;
    private LocalDateTime dateTime;

    public Message() {
    }

    public Message(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.dateTime = LocalDateTime.now();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }


    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", text='" + text + '\'' +
                '}'+ "time: " + dateTime ;
    }
}
