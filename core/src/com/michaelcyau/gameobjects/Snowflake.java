package com.michaelcyau.gameobjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.michaelcyau.gameworld.GameWorld;

public class Snowflake {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private float radius;
    private float opacity;

    private GameWorld gameWorld;

    public Snowflake(int x, int y, GameWorld gameWorld) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, -(8f + MathUtils.random(6f)));
        acceleration = new Vector2(0, 0);
        radius = 0.5f + MathUtils.random(0.3f);
        opacity = 0.5f + MathUtils.random(0.4f);
        this.gameWorld = gameWorld;
    }

    public void update(float delta) {
        acceleration.x = -6f + MathUtils.random(12f);
        velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));

        validate(delta);
    }

    public void render(ShapeRenderer shapeRenderer, float camTop) {
        shapeRenderer.setColor(1, 1, 1, opacity);
        shapeRenderer.circle(position.x, camTop - position.y, radius);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getRadius() {
        return radius;
    }

    public float getOpacity() {
        return opacity;
    }

    private void validate(float delta) {
        boolean reset = false;
        if (position.y < gameWorld.getWorldTop() - (gameWorld.getHeight() * (1 + gameWorld.getBottomBuffer()))) {
            position.set(MathUtils.random(gameWorld.getWidth()), gameWorld.getWorldTop());
            reset = true;
        } else if (position.y > gameWorld.getWorldTop()) {
            position.set(MathUtils.random(gameWorld.getWidth()),
                    gameWorld.getWorldTop() - gameWorld.getHeight() + (MathUtils.random(gameWorld.getBunny().getVelocity().y) * delta));
            reset = true;
        }

        if (reset) {
            velocity.set(0, -(8f + MathUtils.random(6f)));
            acceleration.set(0, 0);
            radius = 0.5f + MathUtils.random(0.3f);
            opacity = 0.5f + MathUtils.random(0.4f);
        }
    }
}
