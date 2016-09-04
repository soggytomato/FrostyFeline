package com.michaelcyau.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;

public class Bell {

    public static float width = 10;
    public static float height = 10;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float maxRotation = 10;
    private float maxRotation_a = 20;
    private float rotation;
    private float rotation_v;
    private float rotation_a;

    private float transparency = 1;
    private float fadeOutSpeed = 2;
    private float fadeOutLocation = 0.2f;
    private boolean dying = false;

    private GameWorld gameWorld;

    private Circle boundingCircle;

    public Bell(float x, float y, GameWorld gameWorld) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, -6f);
        acceleration = new Vector2(0, 0);
        this.gameWorld = gameWorld;
        // this is probably more complicated that it needs to be
        rotation = -maxRotation + MathUtils.random(2 * maxRotation);
        rotation_a = rotation > 0 ? -maxRotation_a : maxRotation_a;
        rotation_v = rotation_a * (1 - Math.abs(rotation / maxRotation));
        boundingCircle = new Circle();
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));

        swing(delta);

        boundingCircle.set(position.x, position.y, width / 2);

        validate(delta);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getRotation() {
        return rotation;
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

    private void swing(float delta) {
        float oldRotation = rotation;
        rotation_v += rotation_a * delta;
        rotation += rotation_v * delta;
        if (oldRotation <= 0 && rotation > 0 || oldRotation >= 0 && rotation < 0) {
            rotation_a = -rotation_a;
        }
    }

    private void validate(float delta) {
        if (position.y < gameWorld.getWorldTop() - (gameWorld.getHeight() * (1 + gameWorld.getBottomBuffer()))) {
            gameWorld.removeBell(this);
        } else if (position.y < gameWorld.getHeight() * fadeOutLocation || dying) {
            if (transparency - (fadeOutSpeed * delta) >= 0) {
                transparency -= (fadeOutSpeed * delta);
            } else {
                gameWorld.removeBell(this);
            }
            dying = true;
        }
    }
}
