package com.michaelcyau.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;

public class Bird {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float width = 15.3f;
    private float height = 12f;

    private float transparency = 1;
    private float fadeOutSpeed = 2;
    private float fadeOutLocation = 0.3f;
    private boolean dying = false;

    private GameWorld gameWorld;

    private Circle boundingCircle;

    public Bird(float x, float y, GameWorld gameWorld) {
        position = new Vector2(x, y);
        velocity = new Vector2(MathUtils.randomSign() * 50f, 0);
        acceleration = new Vector2(0, 0);
        this.gameWorld = gameWorld;
        boundingCircle = new Circle();
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));

        boundingCircle.set(position.x, position.y, width / 2);

        validate(delta);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getTransparency() {
        return transparency;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public boolean isDying() {
        return dying;
    }

    public void die() {
        dying = true;
        acceleration.y = 400;
        velocity.y = -150;
    }

    private void validate(float delta) {
        if (position.y < gameWorld.getWorldTop() - (gameWorld.getHeight() * (1 + gameWorld.getBottomBuffer()))) {
            gameWorld.removeBird(this);
        }
    }
}
