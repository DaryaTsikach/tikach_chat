package com.company;

import java.sql.Timestamp;
import java.io.*;
import java.util.*;
import java.io.IOException;
import org.json.JSONArray;

public class Main {
    public static void main(String[] args) throws IOException  {
        String check = " ";
        Scanner in = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        File file = new File("log.json");
        FileWriter fileWriter = new FileWriter(file, true);
        ArrayList<Message> array =  new ArrayList<>();
        System.out.println("Enter 'd' to download message from file," +
                    "\n 's' to save messages to file," +
                    "\n 'a' to add new message," +
                    "\n 't' to see a history," +
                    "\n 'r' to remove message from file" +
                    "\n 'e' to exit");

            while(true) {
                switch (check = in.next()) {
                    case "d":
                            Message msg = new Message();
                            ArrayList<Message> arrayList = new ArrayList<>();
                            msg.readFromFile(file);
                        break;
                    case "s":
                        FileWriter wr = new FileWriter(file, false);
                        JSONArray jsonArray = new JSONArray();
                        for(Message mess: array){
                            jsonArray.put(mess.createJsonObject());
                        }
                        wr.write(jsonArray.toString());
                        wr.flush();
                        for(int i = 0; i < jsonArray.length(); ++i)
                        {
                            jsonArray.remove(i);
                        }
                        break;
                    case "a":
                        System.out.println("Enter, please,id, author and message");
                        Date date = new Date();
                        Timestamp timestamp = new Timestamp(date.getTime());
                        Message message = new Message(br.readLine(), br.readLine(), br.readLine(), timestamp);
                        array.add(message);
                        break;
                    case "t":
                        Collections.sort(array);
                            for (Message m : array) {
                                System.out.println(m.toString());
                            }
                        break;
                    case "r":
                        System.out.println("Enter the ID to remove the message");
                        String idRemove = br.readLine();
                        for(Message mess: array){
                            if(mess.getId().equals(idRemove)){
                                array.remove(array.indexOf(mess));
                                System.out.println("removed!");
                                break;
                            }
                        }
                        break;
                    case "e":
                        fileWriter.close();
                        System.exit(0);
                        break;
                    default:

                        break;
                }
            }
    }
}