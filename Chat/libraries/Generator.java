package com.company;

/**
 * Created by Даша on 21.02.2016.
 */
public class Generator {
    public String messageLength(String message) throws MessageException {
        int MAX_LENGTH = 140;
        if (message.length() > MAX_LENGTH) {
            throw new MessageException("Message is longer than 140 symbols");
        }
        return message;
    }

}
