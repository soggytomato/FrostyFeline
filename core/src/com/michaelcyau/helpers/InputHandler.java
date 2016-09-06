package com.michaelcyau.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.michaelcyau.frostyfriends.FFGame;
import com.michaelcyau.gameobjects.Bunny;
import com.michaelcyau.gameworld.GameWorld;
import com.michaelcyau.screens.GameScreen;

public class InputHandler implements InputProcessor {

    private GameWorld gameWorld;
    private Bunny myBunny;
    private float scaleFactor;

    public InputHandler(GameWorld gameWorld, float scaleFactor) {
        this.gameWorld = gameWorld;
        myBunny = gameWorld.getBunny();
        this.scaleFactor = scaleFactor;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (gameWorld.isRunning()) {
            myBunny.onclick();
            myBunny.setTargetX((int) (screenX / scaleFactor));
            return true;
        } else if (gameWorld.isInstructions()) {
            gameWorld.start();
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (gameWorld.isRunning()) {
            myBunny.setTargetX((int) (screenX / scaleFactor));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (gameWorld.isRunning()) {
            myBunny.setTargetX((int) (screenX / scaleFactor));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
