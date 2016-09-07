package com.michaelcyau.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.michaelcyau.gameobjects.Bell;
import com.michaelcyau.gameobjects.Bird;
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
    private List<Bell> bells;
    private List<Bird> birds;
    private List<ScoreEffect> scoreEffects;

    public GameRenderer (GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        bunny = gameWorld.getBunny();
        snowflakes = gameWorld.getSnowflakes();
        bells = gameWorld.getBells();
        birds = gameWorld.getBirds();
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

        if (gameWorld.isRunning()) {
            renderSnowflakes();
            renderBells();
            renderBirds(runTime);
            renderBunny(runTime);
            renderScoreEffects();
            renderScore();
        } else if (gameWorld.isGameOver()) {

        } else if (gameWorld.isSplash()) {
            renderSplash();
        } else if (gameWorld.isInstructions()) {
            renderInstructions();
        }
    }

    private void renderBunny(float runTime) {
        boolean facingRight = bunny.isFacingRight();
        batcher.begin();
        batcher.enableBlending();

        batcher.draw((facingRight ? AssetLoader.bunnyRight : AssetLoader.bunnyLeft),
                bunny.getX(), camTop - bunny.getY() - bunny.getHeight(), bunny.getWidth(), bunny.getHeight());

//        batcher.draw(AssetLoader.birdAnimation.getKeyFrame(runTime),
//                bunny.getX(), camTop - bunny.getY() - bunny.getHeight(), bunny.getWidth(), bunny.getHeight());

        batcher.disableBlending();
        batcher.end();

//        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
//        shapeRenderer.begin(ShapeType.Filled);
//        shapeRenderer.setColor(1, 0, 0, 0.5f);
//        shapeRenderer.circle(bunny.getBoundingCircle().x + bunny.getBoundingCircle().radius,
//                camTop - bunny.getBoundingCircle().y - bunny.getBoundingCircle().radius,
//                bunny.getBoundingCircle().radius);
//        shapeRenderer.end();
//        Gdx.graphics.getGL20().glDisable(GL20.GL_BLEND);
    }

    private void renderSnowflakes() {
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeType.Filled);
        for (Snowflake snowflake: snowflakes) {
            if (snowflake.getY() < gameWorld.getWorldTop() && snowflake.getY() > gameWorld.getWorldTop() - gameWorld.getHeight()) {
                shapeRenderer.setColor(1, 1, 1, snowflake.getOpacity());
                shapeRenderer.circle(snowflake.getX(), camTop - snowflake.getY(), snowflake.getRadius());
            }
        }
        shapeRenderer.end();
        Gdx.graphics.getGL20().glDisable(GL20.GL_BLEND);
    }

    private void renderBells() {
        batcher.begin();
        batcher.enableBlending();
        for (Bell bell: bells) {
            Color color = bell.getColor();
            batcher.setColor(color);
            batcher.draw(AssetLoader.bell, bell.getX(), camTop - bell.getY() - bell.getHeight(), bell.getWidth() / 2.0f, 0,
                    bell.getWidth(), bell.getHeight(), 1, 1, bell.getRotation());
        }
        batcher.setColor(1, 1, 1, 1);
        batcher.disableBlending();
        batcher.end();
    }

    private void renderBirds(float runTime) {
        batcher.begin();
        batcher.enableBlending();
        for (Bird bird: birds) {
            if (bird.isDying()) {
                batcher.setColor(1, 1, 1, bird.getTransparency());
            } else {
                batcher.setColor(1, 1, 1, 1);
            }
            boolean facingRight = bird.isFacingRight();
//            batcher.draw((facingRight ? AssetLoader.birdAnimationr : AssetLoader.birdAnimationl).getKeyFrame(runTime),
//                    bird.getX(), camTop - bird.getY() - bird.getHeight(), bird.getWidth(), bird.getHeight());
            batcher.draw(AssetLoader.gift, bird.getX(), camTop - bird.getY() - bird.getHeight(), bird.getWidth() / 2.0f, 0,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        }
        batcher.setColor(1, 1, 1, 1);
        batcher.disableBlending();
        batcher.end();
    }

    private void renderScoreEffects() {
        for (ScoreEffect scoreEffect: scoreEffects) {
            scoreEffect.render(batcher, uiBatcher, shapeRenderer);
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
}
