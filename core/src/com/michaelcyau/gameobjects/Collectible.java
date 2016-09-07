package com.michaelcyau.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Michael on 2016-09-06.
 */
public interface Collectible {
    float getX();
    float getY();
    float getWidth();
    float getHeight();
    boolean isDying();
    void playSound();
    void update(float delta);
    void render(SpriteBatch batcher, float camTop);
    void die();
    Circle getBoundingCircle();
    Color getColor();
}
