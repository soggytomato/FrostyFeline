package com.michaelcyau.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.michaelcyau.gameworld.GameRenderer;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.helpers.InputHandler;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;

    private float screenWidth = Gdx.graphics.getWidth();
    private float screenHeight = Gdx.graphics.getHeight();
    private float gameWidth = 136;
    private float scaleFactor = screenWidth / gameWidth;
    private float gameHeight = screenHeight / scaleFactor;

    private float runTime = 0;

    public GameScreen() {
        world = new GameWorld((int) gameWidth, (int) gameHeight);
        renderer = new GameRenderer(world);

        Gdx.input.setInputProcessor(new InputHandler(world.getBunny(), scaleFactor));
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {

    }

}