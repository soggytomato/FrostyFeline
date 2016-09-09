package com.michaelcyau.overlays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.AssetLoader;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

public class GameOverScreen implements ScreenOverlay {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    public static float width = 11f; // 15.3f
    public static float height = 11f; // 12f

    private String score;
    private String highScore;

    private float maxBoxTransparency = 0.65f;
    private float maxTextTransparency = 1f;
    private float boxTransparency = 0;
    private float textTransparency = 0;
    private float fadeSpeed = 1f; // number of seconds

    private float fadeBoxPerSecond = fadeSpeed / maxBoxTransparency;
    private float fadeTextPerSecond = fadeSpeed / maxTextTransparency;

    private boolean touched = false;

    private TextureRegion overlay = AssetLoader.instructionsBlack;
    private GlyphLayout layout;

    private GameWorld gameWorld;

    public GameOverScreen(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        layout = new GlyphLayout();
    }

    public void update(float delta) {
        score = gameWorld.getScore();
        highScore = NumberFormat.getNumberInstance(Locale.US).format(new BigInteger(AssetLoader.getHighScore()));

        if (gameWorld.getCurrentState() == GameWorld.GameState.GAMEOVER) {
            if (boxTransparency + (fadeBoxPerSecond * delta) < maxBoxTransparency) {
                boxTransparency += fadeBoxPerSecond * delta;
            } else {
                boxTransparency = maxBoxTransparency;
            }

            if (textTransparency + (fadeTextPerSecond * delta) < maxTextTransparency) {
                textTransparency += fadeTextPerSecond * delta;
            } else {
                textTransparency = maxTextTransparency;
            }
        } else if (!touched) {
            touched = true;
            boxTransparency = 1;
        } else {
            if (boxTransparency - (fadeBoxPerSecond * delta) > 0) {
                boxTransparency -= fadeBoxPerSecond * delta;
            } else {
                boxTransparency = 0;
                gameWorld.removeOverlay();
            }
        }
    }

    public void render(SpriteBatch batcher) {
        if (gameWorld.getCurrentState() == GameWorld.GameState.GAMEOVER) {
            float splashWidth = overlay.getRegionWidth();
            float splashHeight = overlay.getRegionHeight();
            float aspectRatio = splashHeight / splashWidth;

            float sizeDifference = (Gdx.graphics.getWidth() * aspectRatio) - Gdx.graphics.getHeight();

            batcher.begin();
            batcher.enableBlending();
            batcher.setColor(1, 1, 1, boxTransparency);
            batcher.draw(overlay, 0, -(sizeDifference / 2), Gdx.graphics.getWidth(), Gdx.graphics.getWidth() * aspectRatio);

            AssetLoader.font.setColor(1, 1, 1, textTransparency);
            AssetLoader.scoreFont.setColor(1, 1, 1, textTransparency);
            String message1 = "Your Score";
            String message2 = score;
            String message3 = "Previous High Score";
            String message4 = highScore;
            String message5 = "Touch to play again";
            layout.setText(AssetLoader.scoreFont, message1);
            float height1 = layout.height;
            float width1 = layout.width;
            layout.setText(AssetLoader.font, message2);
            float height2 = layout.height;
            float width2 = layout.width;
            layout.setText(AssetLoader.scoreFont, message3);
            float height3 = layout.height;
            float width3 = layout.width;
            layout.setText(AssetLoader.font, message4);
            float height4 = layout.height;
            float width4 = layout.width;
            layout.setText(AssetLoader.font, message5);
            float height5 = layout.height;
            float width5 = layout.width;
            float totalHeight = height1 + height1 +
                    height2 + height1 +
                    height3 + height1 +
                    height4 + height1 +
                    height5;
            float offset = (Gdx.graphics.getHeight() - totalHeight) / 2;

            AssetLoader.scoreFont.draw(batcher, message1,
                    (Gdx.graphics.getWidth() - width1) / 2, Gdx.graphics.getHeight() - offset);
            offset += height1 * 2f;

            AssetLoader.font.draw(batcher, message2,
                    (Gdx.graphics.getWidth() - width2) / 2, Gdx.graphics.getHeight() - offset);
            offset += height1 * 2f;

            AssetLoader.scoreFont.draw(batcher, message3,
                    (Gdx.graphics.getWidth() - width3) / 2, Gdx.graphics.getHeight() - offset);
            offset += height1 * 2f;

            AssetLoader.font.draw(batcher, message4,
                    (Gdx.graphics.getWidth() - width4) / 2, Gdx.graphics.getHeight() - offset);
            offset += height1 * 2f;

            AssetLoader.font.draw(batcher, message5,
                    (Gdx.graphics.getWidth() - width5) / 2, Gdx.graphics.getHeight() - offset);

            AssetLoader.font.setColor(1, 1, 1, 1);
            AssetLoader.scoreFont.setColor(1, 1, 1, 1);
            batcher.setColor(1, 1, 1, 1);
            batcher.disableBlending();
            batcher.end();
        } else {
            batcher.begin();
            batcher.enableBlending();
            batcher.setColor(1, 1, 1, boxTransparency);
            batcher.draw(overlay, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batcher.setColor(1, 1, 1, 1);
            batcher.disableBlending();
            batcher.end();
        }

    }

    public void render(ShapeRenderer renderer) {

    }
}
