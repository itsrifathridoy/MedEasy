package com.medeasy.chatsocket;

import com.medeasy.util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;

public class ReadThreadClient implements Runnable{
    NetworkUtil networkUtil;
    String clientName;

    public ReadThreadClient(NetworkUtil networkUtil,String clientName) {
        this.networkUtil = networkUtil;
        this.clientName=clientName;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object data  = networkUtil.read();
                if(data instanceof ArrayList<?>)
                {
                    for(Message msg:(ArrayList<Message>) data)
                    {
                        if(clientName.equals(msg.getFrom()))
                        {
                            System.out.println("Me: " + msg.getText());
                        }
                        else
                        {
                            System.out.println(msg.getFrom()+": "+ msg.getText());
                        }
                    }
                }
                if(data instanceof Message) {
                    Message message = (Message) data;
                    System.out.println(message.getFrom() + ": " + message.getText());
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
