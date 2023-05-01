package com.medeasy.chatsocket;

import com.medeasy.util.NetworkUtil;

import java.io.IOException;
import java.util.Scanner;

public class WriteThreadClient implements Runnable{
    NetworkUtil networkUtil;
    String clientName;

    public WriteThreadClient(NetworkUtil networkUtil,String clientName) {
        this.networkUtil = networkUtil;
        this.clientName=clientName;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            String msg = sc.nextLine();
            String[] megArr = msg.split(",");
            Message message = new Message(clientName, megArr[0], megArr[1]);
            try {
                networkUtil.write(message);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
