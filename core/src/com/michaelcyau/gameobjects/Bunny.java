package com.michaelcyau.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.AssetLoader;

public class Bunny {

    public static float width = 13f;
    public static float height = 12f;

    // NOTE: using a Y-up coordinate system. Y = 0 is at the bottom of the screen.
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private float targetX;
    // how quickly the bunny moves towards the cursor
    private int horizontalForce = 8;

    private float rotation;

    private boolean facingRight;

    private Circle boundingCircle;

    private GameWorld gameWorld;

    public Bunny(float x, float y, GameWorld gameWorld) {
        position = new Vector2(x, y);
        targetX = position.x;
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, -300);
        boundingCircle = new Circle();
        this.gameWorld = gameWorld;
    }

    public void update(float delta) {
        velocity.x = horizontalForce * (targetX - position.x - (width / 2));
        velocity.add(acceleration.cpy().scl(delta));

        position.add(velocity.cpy().scl(delta));
        if (position.y < 0) {
            position.y = 0;
            velocity.y = 0;
        }

        boundingCircle.set(position.x, position.y, 6.5f);

        checkForDeathSequence();
    }

    public void onclick() {
        if (position.y == 0) {
            velocity.y = 200;
        }
    }

    public void jump() {
        velocity.y = 200;
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

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getRotation() {
        return rotation;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public void setTargetX(int x) {
        targetX = x;
        facingRight = targetX > position.x + (width / 2);
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    private void checkForDeathSequence() {
        if (position.y == 0 && gameWorld.getBigIntScore().signum() == 1) {
            gameWorld.endGame();
        }
    }
}
