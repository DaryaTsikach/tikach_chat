package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import org.json.*;
import com.fasterxml.jackson.core.*;

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
                ", \"timestamp\":\"" + timestamp.toString() +
                ", \"message\":\"" + message + "\"" +
                '}';
    }

    public void readFromFile(File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        try {
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(file);
            while(!parser.isClosed()) {
                JsonToken jsonToken = parser.nextToken();
                if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                    String fieldName = parser.getCurrentName();
                    jsonToken = parser.nextToken();
                    if ("id".equals(fieldName)) {
                        this.setId(parser.getValueAsString());
                        this.setId(parser.getValueAsString());
                    } else if ("author".equals(fieldName)) {
                        this.setAuthor(parser.getValueAsString());
                    }
                    else if("timestamp".equals(fieldName)){
                        this.setTimestamp(Timestamp.valueOf(parser.getValueAsString()));
                        System.out.println(this.toString());
                    }
                    else if("message".equals(fieldName)){
                        this.setMessage(parser.getValueAsString());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileWriter.write("FileNotFoundException");
        } catch (IOException e) {
            e.printStackTrace();
            fileWriter.write("IOException");
        } catch (ClassCastException e) {
            e.printStackTrace();
            fileWriter.write("ClassCastException");
        }catch (NullPointerException e){
            e.printStackTrace();
            fileWriter.write("NullPointerException");
        }
    }

    public JSONObject createJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("author", this.author);
            jsonObject.put("timestamp", this.timestamp);
            jsonObject.put("message", this.message);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public int compareTo(Object o) {
        Message temp = (Message)o;
        return this.timestamp.compareTo(temp.getTimestamp());
    }
}
