package com.jaregames.futuretd.client;

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
public class FutureTD {
    public static void main(String... args) {
        log.info("Start time: " + ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));
        
        new GameWindow();
    }
}
