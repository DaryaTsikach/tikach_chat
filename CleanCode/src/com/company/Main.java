package com.company;

import java.sql.Timestamp;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.IOException;
import org.json.JSONArray;




public class Main {
    public static void main(String[] args) throws IOException  {
        ArrayList<Message> messages =  new ArrayList<>();

        String choose = " ";
        Scanner in = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        File file = new File("log.json");
        File logFile = new File("logFile.txt");
        FileWriter writer = new FileWriter(logFile, true);
        Date date = new Date();
        Timestamp timestamp;
        System.out.println("Enter 'd' to download message from file," +
                    "\n 's' to save messages to file," +
                    "\n 'a' to add new message," +
                    "\n 't' to see a history," +
                    "\n 'r' to remove message from file" +
                    "\n 'e' to exit" +
                    "\n 'u' to find message by user" +
                    "\n 'w' to find message by word" +
                    "\n 'x' to find message by regular expression" +
                   "\n 'p' to see messages for a certain period" );

            while(true) {
                switch (choose = in.next()) {
                    case "d": //downloading from file
                            Message msg = new Message();
                            msg.readFromFile(file);
                            timestamp = new Timestamp(date.getTime());
                            writer.write("downloaded messages from file" + timestamp.toString()+'\n');
                            writer.flush();
                        break;

                    case "s": //saving to file
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
                        break;

                    case "a": //new message
                            System.out.println("Enter, please,id, author and message");
                            timestamp = new Timestamp(date.getTime());
                            Message message = new Message(br.readLine(), br.readLine(), br.readLine(), timestamp);
                            try{
                                messages.add(message);
                                writer.write("new message: " + message.toString()+'\n');
                            }catch (NullPointerException e){
                                e.printStackTrace();
                                writer.write("NullPointerException" + timestamp.toString()+'\n');
                                writer.flush();
                            }
                        break;

                    case "t": //sorting
                            Collections.sort(messages);
                            for (Message m : messages) {
                                System.out.println(m.toString());
                            }
                            timestamp = new Timestamp(date.getTime());
                            writer.write("messages sorted" + timestamp.toString()+'\n');
                            writer.flush();
                        break;

                    case "r": //deleting by ID
                            System.out.println("Enter the ID to remove the message");
                            String idRemove = br.readLine();
                            for(Message m: messages){
                                if(m.getId().equals(idRemove)){
                                    messages.remove(messages.indexOf(m));

                                    timestamp = new Timestamp(date.getTime());
                                    writer.write("message " + m.toString()
                                            + " deleted " + timestamp.toString()+'\n');
                                    writer.flush();

                                    System.out.println("removed!");
                                    break;
                                }
                            }
                        break;

                    case "u": //founding by user
                            System.out.println("Enter the name of user");
                            String authorRemove = br.readLine();
                            for(Message m: messages){
                                if(m.getAuthor().equals(authorRemove)){
                                    timestamp = new Timestamp(date.getTime());
                                    writer.write("message " + m.toString()
                                            + "was founded by user " + timestamp.toString()+'\n' );
                                    writer.flush();
                                    System.out.println(m.toString());
                                }
                            }
                        break;

                    case "w": //founding by word
                            System.out.println("Enter the word");
                            String word = br.readLine();
                            for(Message m: messages){
                                if(m.getMessage().contains(word)){
                                    timestamp = new Timestamp(date.getTime());
                                    writer.write("message " + m.toString()
                                                 + "was founded by word " + timestamp.toString()+'\n');
                                    writer.flush();
                                    System.out.println(m.toString());
                                }
                            }
                        break;

                    case "x": //founding by regular expression
                            System.out.println("Enter regular expression");
                            String regular = br.readLine();
                            for(Message m: messages){
                                if(m.getMessage().matches(regular)){
                                    timestamp = new Timestamp(date.getTime());
                                    writer.write("message " + m.toString() + "was founded by regular expression " +
                                                    timestamp.toString()+'\n');
                                    writer.flush();
                                    System.out.println(m.toString());
                                }
                            }
                        break;

                    case "p": //messages by period
                            System.out.println("Enter begin and end of period: yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String strBegin = br.readLine();
                            try {
                                date = dateFormat.parse(strBegin);
                            }catch (ParseException e){
                                e.printStackTrace();
                                timestamp = new Timestamp(date.getTime());
                                writer.write("ParseException" + timestamp.toString()+'\n');
                                writer.flush();
                            }
                            Timestamp perBegin = new Timestamp(date.getTime());
                            String strEnd = br.readLine();
                            try {
                                date = dateFormat.parse(strEnd);
                            }catch (ParseException e){
                                e.printStackTrace();
                            }
                            Timestamp perEnd = new Timestamp(date.getTime());
                            for(Message m: messages){
                                if((m.getTimestamp().compareTo(perBegin) == 1) && (m.getTimestamp().compareTo(perEnd) == -1)){
                                    timestamp = new Timestamp(date.getTime());
                                    writer.write("message " + m.toString() + "was founded by period" + perBegin.toString()
                                                + perEnd.toString() + ":"
                                                + timestamp.toString()+'\n'
                                                );
                                    writer.flush();
                                    System.out.println(m.toString());
                                }
                            }
                        break;

                    case "e": //exit
                            timestamp = new Timestamp(date.getTime());
                            writer.write("end chat" + timestamp.toString()+'\n');
                            writer.flush();
                            writer.close();
                            System.exit(0);
                        break;

                    default:
                        break;
                }



            }
    }
}