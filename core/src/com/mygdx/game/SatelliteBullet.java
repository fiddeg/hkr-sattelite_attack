package com.mygdx.game;

/**
 * Created by Fidde on 2016-11-26.
 */
public class SatelliteBullet extends Bullet{
    public SatelliteBullet(String texture, float positionX, float positionY, float angle, float direction){
        super(texture,positionX,positionY,angle,direction);
        getSprite().setOriginCenter();
    }
}
