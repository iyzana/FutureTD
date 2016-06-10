package com.jaregames.futuretd.server;

import lombok.extern.log4j.Log4j2;

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
@Log4j2
public class FutureTdServer extends Thread {
    
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private ServerSocket serverSocket;
    private Socket clientSocket;
    
    private boolean gameStart;
    private boolean clientConnected;
    private boolean sessionEnded;
    
    private Queue<Serializable> inputQueue;
    
    FutureTdServer() {
        gameStart = false;
        clientConnected = false;
        clientSocket = new Socket();
        
        inputQueue = new LinkedList<>();
        
        start();
    }
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(2960);
        } catch (IOException e) {
            e.printStackTrace();
            sessionEnded = true;
        }
        
        try {
            clientSocket = serverSocket.accept();
            log.info("Client connected!: " + clientSocket.getInetAddress());
            
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(clientSocket.getInputStream());
            clientConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
            sessionEnded = true;
        }
        
        while (!sessionEnded) {
            try {
                inputQueue.add((Serializable) in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                sessionEnded = true;
            }
        }
        
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean send(Serializable s) {
        try {
            out.writeObject(s);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Queue<Serializable> getInputQueue() {
        return inputQueue;
    }
    
    public boolean isClientConnected() {
        return clientConnected;
    }
    
    public boolean isSessionEnded() {
        return sessionEnded;
    }
}
