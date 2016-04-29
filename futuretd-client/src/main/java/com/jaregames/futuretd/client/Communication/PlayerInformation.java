package com.jaregames.futuretd.client.communication;

import java.io.Serializable;

/**
 * Created by René on 29.04.2016.
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
