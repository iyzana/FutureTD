package com.jaregames.futuretd.client;

import com.jaregames.futuretd.client.network.Client;
import com.jaregames.futuretd.client.window.GameWindow;
import com.jaregames.futuretd.server.FutureTDServerManager;
import lombok.extern.log4j.Log4j2;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Project: futuretd
 * <p/>
 * Created on 26.04.2016 at 19:06
 *
 * @author René
 */
@Log4j2
class FutureTD {
    public static void main(String... args) {
        log.info("Start time: " + ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));
        
        new Thread(FutureTDServerManager::new).start();
        
        Client client = new Client();
        log.info("Client created");
        while(!client.isConnected()){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("connected to Server");
        GameWindow.client = client;
        
        new GameWindow();
    }
}
