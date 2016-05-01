package by.bsu.up.chat.storage;

import by.bsu.up.chat.common.models.Message;
import by.bsu.up.chat.logging.Logger;
import by.bsu.up.chat.logging.impl.Log;
import
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryMessageStorage implements MessageStorage {

    private static final String DEFAULT_PERSISTENCE_FILE = "messages.srg";
    private static final String LOG_JSON = "log.json";

    private static final Logger logger = Log.create(InMemoryMessageStorage.class);

    private List<Message> messages = new ArrayList<>();

    @Override
    public synchronized List<Message> getPortion(Portion portion) {
        int from = portion.getFromIndex();
        if (from < 0) {
            throw new IllegalArgumentException(String.format("Portion from index %d can not be less then 0", from));
        }
        int to = portion.getToIndex();
        if (to != -1 && to < portion.getFromIndex()) {
            throw new IllegalArgumentException(String.format("Porting last index %d can not be less then start index %d", to, from));
        }
        to = Math.max(to, messages.size());
        return messages.subList(from, to);
    }

    @Override
    public void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public boolean updateMessage(Message message) {
        for (Message m : messages)
            if (m.getId().equals(message.getId()) && !m.getState().equals("edit")) {
                addMessage(new Message(m.getId(), m.getAuthor(), System.currentTimeMillis(), message.getMessage(), "edit"));
                return true;
            }
        return false;
    }

    @Override
    public synchronized boolean removeMessage(String messageId) {
        for (Message m : messages)
            if (m.getId().equals(messageId) && !m.getStatus().equals("delete")) {
                addMessage(new Message(m.getId(), m.getAuthor(), System.currentTimeMillis(), "Message was deleted", "delete"));
                return true;
            }
        return false;
    }

    @Override
    public int size() {
        return messages.size();
    }

    @Override
    public void saveMessages() {
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            JSONArray jsonArray = new JSONArray();
            for(Message mess: messages){
                jsonArray.put(mess.createJsonObject());
            }
            fileWriter.write(jsonArray.toString());
            fileWriter.flush();

            for(int i = 0; i < jsonArray.length(); ++i) {
                jsonArray.remove(i);
            }
            timestamp = new Timestamp(date.getTime());
            writer.write("save" + messages.size()
                    + "messages to file" + timestamp.toString()+'\n');
            writer.flush();
        } catch (IOException e) {
            logger.error("Error while saving", e);
        }
    }

    @Override
    public void loadMessages() {
        try {
            Message m = new Message();
            messages = m.readFromFile(LOG_JSON);
        } catch (IOException e) {
            logger.error("Error while loading", e);
        }
    }
}
