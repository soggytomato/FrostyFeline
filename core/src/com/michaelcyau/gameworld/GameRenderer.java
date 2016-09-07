package com.michaelcyau.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.michaelcyau.gameobjects.Bell;
import com.michaelcyau.gameobjects.Collectible;
import com.michaelcyau.gameobjects.Gift;
import com.michaelcyau.gameobjects.Bunny;
import com.michaelcyau.gameeffects.ScoreEffect;
import com.michaelcyau.gameobjects.Snowflake;
import com.michaelcyau.helpers.AssetLoader;

import java.util.List;

public class GameRenderer {

    private GameWorld gameWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher, uiBatcher;
    private float camTop;

    private Bunny bunny;
    private List<Snowflake> snowflakes;
    private List<Collectible> collectibles;
    private List<ScoreEffect> scoreEffects;

    public GameRenderer (GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        bunny = gameWorld.getBunny();
        snowflakes = gameWorld.getSnowflakes();
        collectibles = gameWorld.getCollectibles();
        scoreEffects = gameWorld.getScoreEffects();

        cam = new OrthographicCamera();
        cam.setToOrtho(true, gameWorld.getWidth(), gameWorld.getHeight());

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        uiBatcher = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    public void render(float runTime) {
        camTop = gameWorld.getWorldTop();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (gameWorld.getCurrentState()) {
            case GAMEOVER:
                renderGameOverlay();
                // continue
            case RUNNING:
                renderSnowflakes();
                renderCollectibles();
                renderBunny();
                renderScoreEffects();
                renderScore();
                break;
            case INSTRUCTIONS:
                renderInstructions();
                break;
            case SPLASH:
                renderSplash();
                break;
            default:
                break;
        }
    }

    private void renderBunny() {
        batcher.begin();
        batcher.enableBlending();
        bunny.render(batcher, camTop);
        batcher.disableBlending();
        batcher.end();
    }

    private void renderSnowflakes() {
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeType.Filled);
        for (Snowflake snowflake: snowflakes) {
            snowflake.render(shapeRenderer, camTop);
        }
        shapeRenderer.end();
        Gdx.graphics.getGL20().glDisable(GL20.GL_BLEND);
    }

    private void renderCollectibles() {
        batcher.begin();
        batcher.enableBlending();
        for (Collectible col: collectibles) {
            col.render(batcher, camTop);
        }
        batcher.setColor(1, 1, 1, 1);
        batcher.disableBlending();
        batcher.end();
    }

    private void renderScoreEffects() {
        for (ScoreEffect scoreEffect: scoreEffects) {
            scoreEffect.render(batcher, uiBatcher, camTop);
        }
    }

    private void renderScore() {
        int offset = (int) (Gdx.graphics.getWidth() * 0.02f);
        uiBatcher.begin();
        uiBatcher.enableBlending();
        AssetLoader.font.draw(uiBatcher, gameWorld.getScore(), offset, Gdx.graphics.getHeight() - offset);
        uiBatcher.disableBlending();
        uiBatcher.end();
    }

    private void renderSplash() {
        float splashWidth = AssetLoader.splashScreen.getRegionWidth();
        float splashHeight = AssetLoader.splashScreen.getRegionHeight();
        float aspectRatio = splashHeight / splashWidth;

        float sizeDifference = (Gdx.graphics.getWidth() * aspectRatio) - Gdx.graphics.getHeight();

        uiBatcher.begin();
        uiBatcher.enableBlending();
        uiBatcher.setColor(1, 1, 1, gameWorld.getSplashTransparency());
        uiBatcher.draw(AssetLoader.splashScreen, 0, -(sizeDifference / 2), Gdx.graphics.getWidth(), Gdx.graphics.getWidth() * aspectRatio);
        uiBatcher.setColor(1, 1, 1, 1);
        uiBatcher.disableBlending();
        uiBatcher.end();
    }

    private void renderInstructions() {
        float splashWidth = AssetLoader.instructions.getRegionWidth();
        float splashHeight = AssetLoader.instructions.getRegionHeight();
        float aspectRatio = splashHeight / splashWidth;

        float sizeDifference = (Gdx.graphics.getWidth() * aspectRatio) - Gdx.graphics.getHeight();

        uiBatcher.begin();
        uiBatcher.enableBlending();
        uiBatcher.setColor(1, 1, 1, gameWorld.getSplashTransparency());
        uiBatcher.draw(AssetLoader.instructions, 0, -(sizeDifference / 2), Gdx.graphics.getWidth(), Gdx.graphics.getWidth() * aspectRatio);
        uiBatcher.setColor(1, 1, 1, 1);
        uiBatcher.disableBlending();
        uiBatcher.end();
    }

    private void renderGameOverlay() {

    }
}
