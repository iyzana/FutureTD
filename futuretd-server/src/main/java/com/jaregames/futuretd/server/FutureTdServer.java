package com.jaregames.futuretd.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 21:28
 *
 * @author Jannis
 */
@SuppressWarnings("Duplicates")
public class FutureTdServer implements Runnable {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    private boolean gameStart;
    private boolean sessionEnded;

    private Queue<Serializable> inputQueue;
    private Queue<Serializable> outputQueue;

    FutureTdServer() {
        gameStart = false;
        clientSocket = new Socket();

        inputQueue = new LinkedList<>();
        outputQueue = new LinkedList<>();

        this.run();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(2960);
        } catch (IOException e) {
            e.printStackTrace();
            sessionEnded = true;
        }

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            clientSocket = serverSocket.accept();
            System.out.println("Client connected!");
            in = new ObjectInputStream(new ObjectInputStream(clientSocket.getInputStream()));
            out = new ObjectOutputStream(new ObjectOutputStream(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            sessionEnded = true;
        }

        while (!sessionEnded) {
            Object o = null;
            try {
                inputQueue.add((Serializable) in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (!outputQueue.isEmpty()) {
                try {
                    out.writeObject(outputQueue.poll());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Serializable s) {
        outputQueue.add(s);
    }

    public Queue<Serializable> getInputQueue(){
        return inputQueue;
    }

    public static void main(String[] args) {
        new FutureTdServer();
    }

}
