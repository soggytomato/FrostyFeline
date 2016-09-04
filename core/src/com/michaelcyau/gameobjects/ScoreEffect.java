package com.michaelcyau.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.AssetLoader;

public class ScoreEffect {
    private Vector2 position;
    private Vector2 velocity;
    private String score;
    private GlyphLayout layout;

    private float maxLifetime = 0.5f; // in seconds
    private float lifetime = maxLifetime;

    private float transparency = 1;
    private float fadeOutSpeed = 2;

    private GameWorld gameWorld;

    public ScoreEffect (float x, float y, String score, GameWorld gameWorld) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        this.score = score;
        this.gameWorld = gameWorld;
        layout = new GlyphLayout();
        layout.setText(AssetLoader.font, score);
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
        if (lifetime != 0) {
            if (lifetime - delta < 0) {
                lifetime = 0;
                velocity.y = -20;
            } else {
                lifetime -= delta;
            }
        } else {
            if (transparency - (fadeOutSpeed * delta) > 0) {
                transparency -= fadeOutSpeed * delta;
            } else {
                gameWorld.removeScoreEffect(this);
            }
        }
    }

    public void render(SpriteBatch batcher, SpriteBatch uiBatcher, ShapeRenderer shapeRenderer) {
        renderScore(uiBatcher);
    }

    private void renderScore(SpriteBatch uiBatcher) {
        float scalingFactor = (float) Gdx.graphics.getWidth() / (float) gameWorld.getWidth();

        float width = layout.width;
        float height = layout.height;

        float adjustedPositionX = (position.x * scalingFactor) + ((Bell.width / 2) * scalingFactor) - (width / 2);
        float adjustedPositionY = (position.y * scalingFactor) - ((Bell.height / 2) * scalingFactor) + (height / 2);

        uiBatcher.begin();
        uiBatcher.enableBlending();
        Color c = AssetLoader.scoreFont.getColor();
        AssetLoader.scoreFont.setColor(c.r, c.g, c.b, transparency);
        AssetLoader.scoreFont.draw(uiBatcher, score,
                adjustedPositionX,
                adjustedPositionY + Gdx.graphics.getHeight() - (gameWorld.getWorldTop() * scalingFactor));
        AssetLoader.scoreFont.setColor(c.r, c.g, c.b, 1);
        uiBatcher.disableBlending();
        uiBatcher.end();
    }
}
