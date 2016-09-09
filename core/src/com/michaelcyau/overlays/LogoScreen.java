package com.michaelcyau.overlays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.AssetLoader;

public class LogoScreen implements ScreenOverlay {

    private float transparency = 0;
    private float fadeSpeed = 2;
    private float splashRunTime = 0;
    private float splashDuration = 1;
    private GameWorld gameWorld;
    private TextureRegion overlay = AssetLoader.splashScreen;

    public LogoScreen(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void update(float delta) {
        if (splashRunTime == 0 && transparency < 1) {
            if (transparency + (fadeSpeed * delta) < 1) {
                transparency += fadeSpeed * delta;
            } else {
                transparency = 1;
            }
        } else if (transparency == 1 && splashRunTime < splashDuration) {
            if (splashRunTime + delta < splashDuration) {
                splashRunTime += delta;
            } else {
                splashRunTime = splashDuration;
            }
        } else {
            if (transparency - (fadeSpeed * delta) > 0) {
                transparency -= fadeSpeed * delta;
            } else {
                transparency = 0;
                gameWorld.setOverlay(new InstructionsScreen(gameWorld));
                gameWorld.setCurrentState(GameWorld.GameState.INSTRUCTIONS);
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
