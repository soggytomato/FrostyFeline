package com.michaelcyau.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.AssetLoader;

public class Bird implements Collectible {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    public static float width = 15.3f;
    public static float height = 12f;

    private float transparency = 1;
    private float fadeOutSpeed = 1.5f;
    private boolean dying = false;

    private GameWorld gameWorld;

    private Circle boundingCircle;

    public Bird(float x, float y, GameWorld gameWorld) {
        position = new Vector2(x, y);
        velocity = new Vector2(MathUtils.randomSign() * 12f, -6f);
        acceleration = new Vector2(0, 0);
        this.gameWorld = gameWorld;
        boundingCircle = new Circle();
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));
        boundingCircle.set(position.x, position.y, width / 2);
        if (!dying && ((velocity.x > 0 && position.x + width > (gameWorld.getWidth() * 0.94f)) || (velocity.x < 0 && position.x < (gameWorld.getWidth() * 0.03f)))) {
            velocity.x = -velocity.x;
        }
        validate(delta);
    }

    public void playSound() {
        AssetLoader.doubleScore.play();
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

    public boolean isFacingRight() {
        return velocity.x > 0;
    }

    public void die() {
        dying = true;
        acceleration.set(velocity.x > 0 ? 300 : -300, 300);
        velocity.y = -150;
    }

    private void validate(float delta) {
        if (position.y < gameWorld.getWorldTop() - (gameWorld.getHeight() * (1 + gameWorld.getBottomBuffer()))) {
            gameWorld.removeBird(this);
        } else if (dying) {
            if (transparency - (fadeOutSpeed * delta) >= 0) {
                transparency -= (fadeOutSpeed * delta);
            } else {
                gameWorld.removeBird(this);
            }
        }
    }
}
