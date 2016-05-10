package com.jaregames.futuretd.client.Tower1;

/**
 * Created by El Bergmann on 10.05.2016.
 */
public interface Tower {
    //// TODO: 10.05.2016  

    /**
     * Damage per hit
     */
    public double dmg();

    /**
     * Rounds fired per minutes
     */
    public double rpm();

    /**
     * Range of tower as Radius
     */
    public double range();

    /**
     * Contains a short description of the tower
     */
    public void description();
}
