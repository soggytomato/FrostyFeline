package com.michaelcyau.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.AssetLoader;

public class ScoreEffect {
    private Vector2 position;
    private String score;
    private GlyphLayout layout;

    private float transparency = 1;
    private float fadeOutSpeed = 1;

    private GameWorld gameWorld;

    public ScoreEffect (float x, float y, String score, GameWorld gameWorld) {
        position = new Vector2(x, y);
        this.score = score;
        this.gameWorld = gameWorld;
        layout = new GlyphLayout();
        layout.setText(AssetLoader.font, score);
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
        AssetLoader.font.draw(uiBatcher, score,
                adjustedPositionX,
                adjustedPositionY + Gdx.graphics.getHeight() - (gameWorld.getWorldTop() * scalingFactor));
        uiBatcher.disableBlending();
        uiBatcher.end();
    }
}
