package com.michaelcyau.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.michaelcyau.gameobjects.Bell;
import com.michaelcyau.gameobjects.Collectible;
import com.michaelcyau.gameobjects.Gift;
import com.michaelcyau.gameobjects.Bunny;
import com.michaelcyau.gameeffects.ScoreEffect;
import com.michaelcyau.gameobjects.Snowflake;
import com.michaelcyau.helpers.AssetLoader;
import com.michaelcyau.overlays.ScreenOverlay;
import com.michaelcyau.screens.GameScreen;

import java.util.List;

public class GameRenderer {

    private GameWorld gameWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher, uiBatcher;
    private float camTop;
    private float panSpeed = 4f; // how quickly the camera follows the bunny.

    // During freefall, the camera will drag behind the bunny, and therefore the bunny
    // will appear further and further down the screen. This is problematic because
    // snowflakes can only be generated at some finite distance above the bunny's position,
    // and thus will seemingly 'disappear' if the camera is too far away.
    // The following value specifies how far past the bottom of the screen the bunny can go
    // before the camera starts moving exactly at the speed of the bunny and stops dragging
    // behind. It is represented as a multiple of the screen height.
    private float bunnyMaxBottomPan = 0.5f;

    private Bunny bunny;
    private List<Snowflake> snowflakes;
    private List<Collectible> collectibles;
    private List<ScoreEffect> scoreEffects;

    private ScreenOverlay overlay;

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

        camTop = gameWorld.getWorldTop();
    }

    public void render(float delta, float runTime) {
        moveCamera(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (gameWorld.getCurrentState()) {
            case GAMEOVER:
                // continue
            case RUNNING:
                renderBackground();
                renderSnowflakes();
                renderCollectibles();
                renderBunny();
                renderScoreEffects();
                renderScore();
                break;
            default:
                break;
        }

        overlay = gameWorld.getOverlay();
        if (overlay != null) {
            overlay.render(uiBatcher);
        }
    }

    private void moveCamera(float delta) {
        float y = gameWorld.getBunny().getY();
        if (y > 0 && y < camTop - (gameWorld.getHeight() * (1 + bunnyMaxBottomPan))) {
            camTop += gameWorld.getBunny().getVelocity().y * delta;
        } else {
            camTop += panSpeed * (gameWorld.getWorldTop() - camTop) * delta;
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
        float offset = Gdx.graphics.getWidth() * 0.02f;
        uiBatcher.begin();
        uiBatcher.enableBlending();
        AssetLoader.font.draw(uiBatcher, gameWorld.getScore(), offset, Gdx.graphics.getHeight() - offset);
        uiBatcher.disableBlending();
        uiBatcher.end();
    }

    private void renderBackground() {
        batcher.begin();
        batcher.draw(AssetLoader.bg, 0, camTop - gameWorld.getWidth(), gameWorld.getWidth(), gameWorld.getWidth());
        batcher.end();
    }
}
