package com.michaelcyau.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.AssetLoader;

public class Gift implements Collectible {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    public static float width = 11f; // 15.3f
    public static float height = 11f; // 12f

    private float initRotation = 10;
    private float maxRotation_a = 50;
    private float rotation;
    private float rotation_v;
    private float rotation_a;
    private float transparency = 1;
    private float fadeOutSpeed = 1.5f;
    private boolean dying = false;
    private Color color;

    private GameWorld gameWorld;

    private Circle boundingCircle;

    public Gift(float x, float y, GameWorld gameWorld) {
        position = new Vector2(x, y);
        velocity = new Vector2(MathUtils.randomSign() * 10f, -6f);
        acceleration = new Vector2(0, 0);
        this.gameWorld = gameWorld;
        boundingCircle = new Circle();
        color = new Color(1, 1, 1, transparency);
        rotation = -initRotation;
        rotation_a = maxRotation_a;
        rotation_v = 0;
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));
        boundingCircle.set(position.x, position.y, width / 2);
        if (!dying && ((velocity.x > 0 && position.x + width > (gameWorld.getWidth() * 0.94f)) || (velocity.x < 0 && position.x < (gameWorld.getWidth() * 0.03f)))) {
            velocity.x = -velocity.x;
        }
        color.a = transparency;
        swing(delta);
        validate(delta);
    }

    public void render(SpriteBatch batcher, float camTop) {
        batcher.setColor(color);
        batcher.draw(AssetLoader.gift, position.x, camTop - position.y - height, width / 2.0f, 0,
                width, height, 1, 1, rotation);
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

    public float getRotation() {
        return rotation;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public Color getColor() {
        return color;
    }

    public boolean isDying() {
        return dying;
    }

    public boolean isFacingRight() {
        return velocity.x > 0;
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
            gameWorld.removeCollectible(this);
        } else if (dying) {
            if (transparency - (fadeOutSpeed * delta) >= 0) {
                transparency -= (fadeOutSpeed * delta);
            } else {
                gameWorld.removeCollectible(this);
            }
        }
    }
}
