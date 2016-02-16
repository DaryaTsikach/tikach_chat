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
        String check = " ";
        Scanner in = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        File file = new File("log.json");
        FileWriter fileWriter = new FileWriter(file, true);
        ArrayList<Message> array =  new ArrayList<>();
        File logFile = new File("logFile.txt");
        FileWriter writer = new FileWriter(logFile, true);
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
                switch (check = in.next()) {
                    case "d":
                            Message msg = new Message();
                            ArrayList<Message> arrayList = new ArrayList<>();
                            msg.readFromFile(file);
                            Date dateD = new Date();
                            Timestamp timestampD = new Timestamp(dateD.getTime());
                            writer.write("downloaded messages from file" + timestampD.toString());
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
                        Date dateS = new Date();
                        Timestamp timestampS = new Timestamp(dateS.getTime());
                        writer.write("save" + array.size() + "messages to file" + timestampS.toString());
                        break;
                    case "a":
                        System.out.println("Enter, please,id, author and message");
                        Date date = new Date();
                        Timestamp timestamp = new Timestamp(date.getTime());
                        Message message = new Message(br.readLine(), br.readLine(), br.readLine(), timestamp);
                        array.add(message);
                        writer.write("new message: " + message.toString());
                        break;
                    case "t":
                        Collections.sort(array);
                            for (Message m : array) {
                                System.out.println(m.toString());
                            }
                        Date dateT = new Date();
                        Timestamp timestampT = new Timestamp(dateT.getTime());
                        writer.write("messages sorted" + timestampT.toString());
                        break;
                    case "r":
                        System.out.println("Enter the ID to remove the message");
                        String idRemove = br.readLine();
                        Message tempR = new Message();
                        for(Message mess: array){
                            if(mess.getId().equals(idRemove)){
                                tempR = mess;
                                array.remove(array.indexOf(mess));
                                System.out.println("removed!");
                                break;
                            }
                        }
                        Date dateR = new Date();
                        Timestamp timestampR = new Timestamp(dateR.getTime());
                        writer.write("message " + tempR.toString() + " deleted " + timestampR.toString());
                        break;
                    case "u":
                        System.out.println("Enter the name of user");
                        String name = br.readLine();
                        for(Message m: array){
                            if(m.getAuthor().equals(name)){
                                Date dateU = new Date();
                                Timestamp timestampU = new Timestamp(dateU.getTime());
                                writer.write("message " + m.toString() + "was founded by user " + timestampU.toString() );
                               System.out.println(m.toString());
                            }
                        }
                        break;
                    case "w":
                        System.out.println("Enter the word");
                        String word = br.readLine();
                        for(Message m:array){
                            if(m.getMessage().indexOf(word) >= 0){
                                Date dateW = new Date();
                                Timestamp timestampW = new Timestamp(dateW.getTime());
                                writer.write("message " + m.toString() + "was founded by word " + timestampW.toString());
                                System.out.println(m.toString());
                            }
                        }
                        break;
                    case "x":
                        System.out.println("Enter regular expression");
                        String reg = br.readLine();
                        for(Message m: array){
                            if(m.getMessage().matches(reg)){
                                Date dateX = new Date();
                                Timestamp timestampX = new Timestamp(dateX.getTime());
                                writer.write("message " + m.toString() + "was founded by regular expression " +
                                         timestampX.toString());
                                System.out.println(m.toString());
                            }
                        }
                        break;
                    case "p":
                        System.out.println("Enter the period(2 dates)");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dat1 = br.readLine();
                        Date d1 =  new Date();
                        try {
                           d1 = dateFormat.parse(dat1);
                        }catch (ParseException e){
                            e.printStackTrace();
                            Date dateExc = new Date();
                            Timestamp timestampExc = new Timestamp(dateExc.getTime());
                            writer.write("ParseException" + timestampExc.toString());
                        }
                        long tt1 = d1.getTime();
                        Timestamp perBegin = new Timestamp(tt1);
                        String dat2 = br.readLine();
                        Date d2 =  new Date();
                        try {
                            d2 = dateFormat.parse(dat2);
                        }catch (ParseException e){
                            e.printStackTrace();
                        }
                        long tt2 = d2.getTime();
                        Timestamp perEnd = new Timestamp(tt2);
                        for(Message m: array){
                            if((m.getTimestamp().compareTo(perBegin) == 1) && (m.getTimestamp().compareTo(perEnd) == -1)){
                                Date dateP = new Date();
                                Timestamp timestampP = new Timestamp(dateP.getTime());
                                writer.write("message " + m.toString() + "was founded by period" + perBegin.toString()
                                        + perEnd.toString() + ":"
                                        + timestampP.toString()
                                        );
                                System.out.println(m.toString());
                            }
                        }
                        break;
                    case "e":
                        fileWriter.close();
                        Date dateE = new Date();
                        Timestamp timestampE = new Timestamp(dateE.getTime());
                        writer.write("end chat" + timestampE.toString());
                        System.exit(0);
                        break;
                    default:
                        break;
                }



            }
    }
}