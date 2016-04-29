package com.jaregames.futuretd.client;

import com.jaregames.futuretd.client.Communication.EndSessionToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by René on 27.04.2016.
 */
public class Client implements Runnable {
    ObjectInputStream in;
    ObjectOutputStream out;
    Socket socketToServer;
    boolean sessionEnded;

    Client() {

        sessionEnded = false;
        this.run();
    }

    @Override
    public void run() {

        socketToServer = new Socket();

        try {
            socketToServer = new Socket("localhost", 2960);
            in = new ObjectInputStream(socketToServer.getInputStream());
            out = new ObjectOutputStream(socketToServer.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!sessionEnded) {
            Object o = null;
            try {
                o = in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (o != null) process(o);
        }
    }

    private void process(Object obj){

        if(obj instanceof EndSessionToken){
            sessionEnded = true;
        }

    }

    public static void main(String[] args) {
        new Client();
    }
}
