package com.michaelcyau.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.AssetLoader;

/**
 * Created by Michael on 9/8/2016.
 */
public class InstructionsScreen implements ScreenOverlay {

    private float transparency = 0;
    private float fadeSpeed = 1f;
    private boolean touched = false;
    private boolean started = false;
    private GameWorld gameWorld;
    private TextureRegion overlay = AssetLoader.instructions;

    public InstructionsScreen(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void update(float delta) {
        if (!started && !touched && transparency < 1) { // fade in
            if (transparency + (fadeSpeed * delta) < 1) {
                transparency += fadeSpeed * delta;
            } else {
                transparency = 1;
            }
        } else if (!started && !touched && transparency == 1) { // poll for screen touch
            if (gameWorld.getCurrentState() == GameWorld.GameState.READY) {
                AssetLoader.confirm.setVolume(AssetLoader.confirm.play(), 0.2f);
                touched = true;
            }
        } else if (!started && touched && transparency > 0) { // fade out
            if (transparency - (fadeSpeed * delta) > 0) {
                transparency -= fadeSpeed * delta;
            } else {
                transparency = 1;
                overlay = AssetLoader.instructionsBlack;
                started = true;
                gameWorld.setCurrentState(GameWorld.GameState.RUNNING);
                AssetLoader.bgMusic.play();
            }
        } else { // fade out from black (fade into gameplay)
            if (transparency - (fadeSpeed * delta) > 0) {
                transparency -= fadeSpeed * delta;
            } else {
                transparency = 0;
                gameWorld.removeOverlay();
            }
        }
    }

    public void render(SpriteBatch batcher) {
        float splashWidth = overlay.getRegionWidth();
        float splashHeight = overlay.getRegionHeight();
        float aspectRatio = splashHeight / splashWidth;

        float sizeDifference = (Gdx.graphics.getWidth() * aspectRatio) - Gdx.graphics.getHeight();

        batcher.begin();
        batcher.enableBlending();
        batcher.setColor(1, 1, 1, transparency);
        batcher.draw(overlay, 0, -(sizeDifference / 2), Gdx.graphics.getWidth(), Gdx.graphics.getWidth() * aspectRatio);
        batcher.setColor(1, 1, 1, 1);
        batcher.disableBlending();
        batcher.end();
    }

    public void render(ShapeRenderer renderer) {

    }
}
