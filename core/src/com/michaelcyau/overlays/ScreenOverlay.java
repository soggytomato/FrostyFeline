package com.michaelcyau.overlays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.michaelcyau.gameworld.GameWorld;

/**
 * Created by Michael on 9/7/2016.
 */
public interface ScreenOverlay {
    void update(float delta);
    void render(SpriteBatch batcher);
    void render(ShapeRenderer renderer);
}
