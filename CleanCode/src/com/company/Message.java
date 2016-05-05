package com.company;

import java.io.*;
import java.sql.Timestamp;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class Message implements Comparable {
    private String id;
    private String author;
    private Timestamp timestamp;
    private String message;


    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message (String id, String author, String message, Timestamp timestamp) {
            Generator generator = new Generator();
        try{
            message = generator.messageLength(message);
            this.id = id;
            this.author = author;
            this.timestamp = timestamp;
            this.message = message;
        }catch (MessageException me){
            System.out.println("Message is longer than 140 symbols");
        }
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"author\":\"" + author + "\"" +
                ", \"timestamp\":\"" + timestamp + "\"" +
                ", \"message\":\"" + message + "\"" +
                '}';
    }


    @Override
    public int compareTo(Object o) {
        Message temp = (Message)o;
        return this.timestamp.compareTo(temp.getTimestamp());
    }
}
