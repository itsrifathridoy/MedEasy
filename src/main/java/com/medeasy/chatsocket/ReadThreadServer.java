package com.medeasy.chatsocket;

import com.medeasy.util.DatabaseConnection;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadThreadServer implements Runnable{
    NetworkInformation networkInformation;
    HashMap<String,NetworkInformation> clientList;
    public ReadThreadServer(NetworkInformation networkInformation, HashMap<String,NetworkInformation> clientList) {
        this.networkInformation = networkInformation;
        this.clientList=clientList;
        new Thread(this).start();
    }

    @Override
    public void run() {


        while (true)
        {
            try {
                Object data =  networkInformation.networkUtil.read();
                if(data instanceof Message)
                {
                    Message message = (Message) data;
                    String receiver = message.getTo();
                    String sender = message.getFrom();
                    String body = message.getText();
                    NetworkInformation receiverNetworkInfo = clientList.get(receiver);
                    NetworkInformation senderNetworkInfo = clientList.get(sender);
                    if(receiver.equalsIgnoreCase("server") && body.equalsIgnoreCase("inbox"))
                    {
                        ArrayList<Message> inbox = senderNetworkInfo.inbox;
                        senderNetworkInfo.networkUtil.write(inbox);
                    }
                    if(!receiver.equalsIgnoreCase(sender))
                    {
                        receiverNetworkInfo.inbox.add(message);
                        senderNetworkInfo.inbox.add(message);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(baos);


                        oos.writeObject(senderNetworkInfo.inbox);

                        oos.flush();
                        oos.close();

                        InputStream is = new ByteArrayInputStream(baos.toByteArray());
                        DatabaseConnection databaseConnection = new DatabaseConnection();
                        Connection connection = databaseConnection.getConnection();
                        PreparedStatement checkStmt = connection.prepareStatement("SELECT COUNT(*) FROM chatbox WHERE userID = ?");
                        checkStmt.setString(1, receiver);
                        ResultSet checkResult = checkStmt.executeQuery();
                        checkResult.next();
                        int count = checkResult.getInt(1);

                        // If the userID exists, update the inbox value
                        if (count > 0) {
                            PreparedStatement updateStmt = connection.prepareStatement("UPDATE chatbox SET inbox = ? WHERE userID = ?");
                            updateStmt.setBlob(1, is);
                            updateStmt.setString(2, receiver);
                            updateStmt.executeUpdate();
                        }
                        // If the userID does not exist, insert a new row with the userID and inbox values
                        else {
                            PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO chatbox (userID, inbox) SELECT ?, ? WHERE NOT EXISTS (SELECT * FROM chatbox WHERE userID = ?)");
                            insertStmt.setString(1, receiver);
                            insertStmt.setBlob(2,is);
                            insertStmt.setString(3, receiver);
                            insertStmt.executeUpdate();
                        }


                        receiverNetworkInfo.networkUtil.write(message);

                    }
                    if((receiverNetworkInfo==null || senderNetworkInfo == null) && !receiver.equalsIgnoreCase("server"))
                    {
                        throw new RuntimeException("Client Not Found");
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Client Disconnected..");
                break;
            } catch (RuntimeException e)
            {
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
