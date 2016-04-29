package com.jaregames.futuretd.server;

import com.jaregames.futuretd.server.Communication.EndSessionToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 21:28
 *
 * @author Jannis
 */
public class FutureTdServer implements Runnable {
    ServerSocket serverSocket;
    Socket clientSocket;
    boolean gameStart;
    boolean sessionEnded;

    FutureTdServer() {
        gameStart = false;
        clientSocket = new Socket();
        this.run();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(2960);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream in = null;
        ObjectOutputStream out;

        try {
            clientSocket = serverSocket.accept();
            System.out.println("Client connected!");
            in = new ObjectInputStream(new ObjectInputStream(clientSocket.getInputStream()));
            out = new ObjectOutputStream(new ObjectOutputStream(clientSocket.getOutputStream()));
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

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(Object obj) {

        if (obj instanceof EndSessionToken) {
            sessionEnded = true;
        }

    }

    public static void main(String[] args) {
        new FutureTdServer();
    }

}
