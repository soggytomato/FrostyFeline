package com.michaelcyau.overlays;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.michaelcyau.gameworld.GameWorld;

public class GameOverScreen implements ScreenOverlay {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    public static float width = 11f; // 15.3f
    public static float height = 11f; // 12f

    private float initRotation = 10;
    private float maxRotation_a = 50;
    private float rotation;
    private float rotation_v;
    private float rotation_a;
    private float transparency = 1;
    private float fadeOutSpeed = 1.5f;
    private boolean dying = false;
    private Color color;

    private GameWorld gameWorld;

    public void update(float delta) {

    }

    public void render(SpriteBatch batcher) {

    }
}
