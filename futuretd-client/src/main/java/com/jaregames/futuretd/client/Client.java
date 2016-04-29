package com.jaregames.futuretd.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by René on 27.04.2016.
 */
@SuppressWarnings("Duplicates")
public class Client implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private LinkedList<Serializable> inputQueue;
    private LinkedList<Serializable> outputQueue;

    private Socket socketToServer;
    private boolean sessionEnded;

    Client() {

        sessionEnded = false;
        this.run();
    }

    @Override
    public void run() {
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
                inputQueue.add((Serializable) in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(!outputQueue.isEmpty()){
                try {
                    out.writeObject(outputQueue.getFirst());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void send(Serializable s){
        outputQueue.add(s);
    }

    public LinkedList getInputQueue(){
        return inputQueue;
    }

    public static void main(String[] args) {
        new Client();
    }
}
