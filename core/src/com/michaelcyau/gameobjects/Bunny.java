package com.michaelcyau.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.AssetLoader;

public class Bunny {

    public static float width = 17f;
    public static float height = 9.4f;

    // NOTE: using a Y-up coordinate system. Y = 0 is at the bottom of the screen.
    private Vector2 position; // x position is MIDDLE of cat. y position is bottom (and height)
    private Vector2 velocity;
    private Vector2 acceleration;
    private float walkingSpeed = 60;
    private float gravity = -320;
    private float targetX;
    // how quickly the bunny moves towards the cursor
    private float horizontalForce = 6f;

    private float angle = 0;
    private float jumpAngle = -45;
    private float maxAngle = 90;
    private float rotation = 75;

    private boolean facingRight;

    private Circle boundingCircle;

    private GameWorld gameWorld;

    public Bunny(float x, float y, GameWorld gameWorld) {
        position = new Vector2(x, y);
        targetX = position.x;
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, gravity);
        boundingCircle = new Circle();
        this.gameWorld = gameWorld;
    }

    public void update(float delta) {
        if (position.y == gameWorld.getFloor()) {
            velocity.x = (targetX > position.x ? walkingSpeed : -walkingSpeed);
            if (velocity.x > 0 && position.x + (velocity.x * delta) > targetX ||
                    velocity.x < 0 && position.x + (velocity.x * delta) < targetX) {
                velocity.x = 0;
            }
        } else {
            velocity.x = horizontalForce * (targetX - position.x);
            velocity.add(acceleration.cpy().scl(delta));
            if (angle + (rotation * delta) < maxAngle) {
                angle += (rotation * delta);
            } else {
                angle = maxAngle;
            }
        }

        position.add(velocity.cpy().scl(delta));
        if (position.y < gameWorld.getFloor()) {
            position.y = gameWorld.getFloor();
            velocity.y = 0;
            angle = 0;
        }

        boundingCircle.set(position.x - (width / 2), position.y, 7.5f);

        checkForDeathSequence();
    }

    public void render(SpriteBatch batcher, float camTop, float runTime) {
        if (position.y > 0 && angle < -15) {
            batcher.draw((facingRight ? AssetLoader.catFallAnimationR : AssetLoader.catFallAnimationL).getKeyFrame(runTime),
                    position.x - (width / 2), camTop - position.y - height, width / 2, height / 2, width, height, 1, 1,
                    facingRight ? angle : -angle);
        } else if (position.y > 0 && angle >= -15) {
            batcher.draw((facingRight ? AssetLoader.catJumpAnimationR : AssetLoader.catJumpAnimationL).getKeyFrame(runTime),
                    position.x - (width / 2), camTop - position.y - height, width / 2, height / 2, width, height, 1, 1,
                    facingRight ? angle : -angle);
        } else if (Math.abs(velocity.x) > 1) {
            batcher.draw((facingRight ? AssetLoader.catAnimationR : AssetLoader.catAnimationL).getKeyFrame(runTime),
                    position.x - (width / 2), camTop - position.y - height, width / 2, height / 2, width, height, 1, 1,
                    facingRight ? angle : -angle);
        } else {
            batcher.draw((facingRight ? AssetLoader.catStationaryAnimationR : AssetLoader.catStationaryAnimationL).getKeyFrame(runTime),
                    position.x - (width / 2), camTop - position.y - height, width / 2, height / 2, width, height, 1, 1,
                    facingRight ? angle : -angle);
        }

    }

    public void onclick() {
        if (position.y == gameWorld.getFloor()) {
            jump();
        }
    }

    public void jump() {
        velocity.y = 200;
        angle = jumpAngle;
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

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public void setTargetX(int x) {
        targetX = x;
        facingRight = targetX > position.x;
    }

    private void checkForDeathSequence() {
        if (position.y == gameWorld.getFloor() && gameWorld.getBigIntScore().signum() == 1) {
            gameWorld.endGame();
        }
    }
}
