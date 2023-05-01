package com.medeasy.chatsocket;

import com.medeasy.util.NetworkUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    private ServerSocket serverSocket;
    public HashMap<String,NetworkInformation> clientNetworkInformationMap;
    Server() {

        try {
            serverSocket = new ServerSocket(33333);
            System.out.println("Server has started...");
            clientNetworkInformationMap = new HashMap<>();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server has accepted a connection...");
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }


    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        String clientName = (String) networkUtil.read();
        ArrayList<Message> inbox=new ArrayList<>();
        NetworkInformation networkInformation = new NetworkInformation(networkUtil,inbox);
        clientNetworkInformationMap.put(clientName,networkInformation);
        new ReadThreadServer(networkInformation,clientNetworkInformationMap);
    }

    public static void main(String args[]) {
        Server server = new Server();
    }
}
