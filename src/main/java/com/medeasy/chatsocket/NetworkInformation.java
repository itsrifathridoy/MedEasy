package com.medeasy.chatsocket;

import com.medeasy.util.NetworkUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class NetworkInformation implements Serializable {
    NetworkUtil networkUtil;
    ArrayList<Message> inbox;

    public NetworkInformation(NetworkUtil networkUtil, ArrayList<Message> inbox) {
        this.networkUtil = networkUtil;
        this.inbox = inbox;
    }
}
