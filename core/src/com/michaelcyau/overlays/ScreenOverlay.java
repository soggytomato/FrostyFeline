package com.michaelcyau.overlays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Michael on 9/7/2016.
 */
public interface ScreenOverlay {
    void update(float delta);
    void render(SpriteBatch batcher);
}
