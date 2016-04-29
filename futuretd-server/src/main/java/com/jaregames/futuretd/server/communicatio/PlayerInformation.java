package com.jaregames.futuretd.server.communicatio;

import java.io.Serializable;

/**
 * Project: futuretd
 * <p/>
 * Created on 29.04.2016 at 21:28
 *
 * @author René
 */
public class PlayerInformation implements Serializable {
    String nickname;

    PlayerInformation(String nickname){
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
