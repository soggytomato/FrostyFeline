package com.michaelcyau.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.michaelcyau.gameobjects.Bunny;
import com.michaelcyau.screens.GameScreen;

public class InputHandler implements InputProcessor {

    private Bunny myBunny;
    private float scaleFactor;

    public InputHandler(Bunny bunny, float scaleFactor) {
        myBunny = bunny;
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
        myBunny.onclick();
        myBunny.setTargetX((int) (screenX / scaleFactor));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        myBunny.setTargetX((int) (screenX / scaleFactor));
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        myBunny.setTargetX((int) (screenX / scaleFactor));
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
