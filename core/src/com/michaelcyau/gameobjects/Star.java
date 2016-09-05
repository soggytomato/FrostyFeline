package com.michaelcyau.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.AssetLoader;

public class Star {
    public static float width = 3.5f;
    public static float height = 3.5f;

    private Vector2 position; // center position
    private Vector2 velocity;
    private Vector2 initVelocity;
    private Vector2 acceleration;
    private float initDistance = 6;
    private float rotation = 0;
    private float rotation_v = 200;

    private boolean dying = false;
    private float driftSpeed;
    private float transparency = 1;
    private float fadeOutSpeed;

    private GameWorld gameWorld;

    public Star(float x, float y, float angle, GameWorld gameWorld,
                float initSpeed, float initAccel, float driftSpeed, float fadeOutSpeed) {
        position = new Vector2(x + initDistance * MathUtils.cosDeg(angle), y + initDistance * MathUtils.sinDeg(angle));
        velocity = new Vector2(initSpeed * MathUtils.cosDeg(angle), initSpeed * MathUtils.sinDeg(angle));
        initVelocity = velocity.cpy();
        acceleration = new Vector2(initAccel * MathUtils.cosDeg(angle), initAccel * MathUtils.sinDeg(angle));
        this.gameWorld = gameWorld;
        this.driftSpeed = driftSpeed;
        this.fadeOutSpeed = fadeOutSpeed;
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        if (!dying && (velocity.x <= 0 && initVelocity.x > 0 || velocity.x >= 0 && initVelocity.x < 0)) {
            velocity.set(0, driftSpeed);
            acceleration.set(0, 0);
            dying = true;
        }
        if (dying) {
            if (transparency - (fadeOutSpeed * delta) > 0) {
                transparency -= fadeOutSpeed * delta;
            } else {
                transparency = 0;
            }
        }
        position.add(velocity.cpy().scl(delta));
        rotation += rotation_v * delta;
    }

    public void render(SpriteBatch batcher) {
        batcher.begin();
        batcher.enableBlending();
        Color c = batcher.getColor();
        batcher.setColor(c.r, c.g, c.b, transparency);
        batcher.draw(AssetLoader.star, position.x - (width / 2), gameWorld.getWorldTop() - position.y - (height / 2), width / 2, height / 2,
                width, height, 1, 1, rotation);
        batcher.setColor(c.r, c.g, c.b, 1);
        batcher.disableBlending();
        batcher.end();
    }
}
