package com.jaregames.futuretd.client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Project: futuretd
 * <p/>
 * Created on 27.04.2016 at 21:27
 *
 * @author René
 */
@SuppressWarnings("Duplicates")
public class Client extends Thread {
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Queue<Serializable> inputQueue;

    private boolean connected;

    private Socket socketToServer;
    private boolean sessionEnded;

    public Client() {
        connected = false;
        inputQueue = new LinkedList<>();

        sessionEnded = false;
        this.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("lets connect!!!");
            socketToServer = new Socket("localhost", 2960);
            System.out.println("connected");
            out = new ObjectOutputStream(socketToServer.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socketToServer.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!sessionEnded) {
            Object o = null;
            try {
                inputQueue.add((Serializable) in.readObject());
            }catch(OptionalDataException e){

            }catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            socketToServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean send(Serializable s){
            try {
                out.writeObject(s);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        return true;

    }

    public Queue<Serializable> getInputQueue(){
        return inputQueue;
    }

    public static void main(String[] args) {
        new Client();
    }

    public boolean isConnected() {
        return connected;
    }
}
