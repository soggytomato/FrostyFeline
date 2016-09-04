package com.michaelcyau.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.michaelcyau.gameobjects.Bell;
import com.michaelcyau.gameobjects.Bunny;
import com.michaelcyau.gameobjects.Snowflake;
import com.michaelcyau.helpers.AssetLoader;

import java.util.List;

public class GameRenderer {

    private GameWorld gameWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;
    private float camTop;

    private Bunny bunny;
    private List<Snowflake> snowflakes;
    private List<Bell> bells;

    BitmapFont font = new BitmapFont();

    public GameRenderer (GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        bunny = gameWorld.getBunny();
        snowflakes = gameWorld.getSnowflakes();
        bells = gameWorld.getBells();

        cam = new OrthographicCamera();
        cam.setToOrtho(true, gameWorld.getWidth(), gameWorld.getHeight());

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    public void render(float runTime) {
        camTop = gameWorld.getWorldTop();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderSnowflakes();
        renderBells();
        renderBunny();
        renderScore();
    }

    private void renderBunny() {
        boolean facingRight = bunny.isFacingRight();
        batcher.begin();
        batcher.enableBlending();
        batcher.draw((facingRight ? AssetLoader.bunnyRight : AssetLoader.bunnyLeft),
                bunny.getX(), camTop - bunny.getY() - bunny.getHeight(), bunny.getWidth(), bunny.getHeight());
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
            if (bell.isDying()) {
                batcher.setColor(1, 1, 1, bell.getTransparency());
            } else {
                batcher.setColor(1, 1, 1, 1);
            }
            batcher.draw(AssetLoader.bell, bell.getX(), camTop - bell.getY() - bell.getHeight(), bell.getWidth() / 2.0f, 0,
                    bell.getWidth(), bell.getHeight(), 1, 1, bell.getRotation());
        }
        batcher.setColor(1, 1, 1, 1);
        batcher.disableBlending();
        batcher.end();
    }

    private void renderScore() {
        batcher.begin();
        batcher.enableBlending();
        AssetLoader.font.draw(batcher, gameWorld.getScore(), 4, 4);
        batcher.disableBlending();
        batcher.end();
    }
}
