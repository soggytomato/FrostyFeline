package com.michaelcyau.gameobjects;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Michael on 2016-09-06.
 */
public interface Collectible {
    float getX();
    float getY();
    float getWidth();
    float getHeight();
    void playSound();
    Color getColor();
}
