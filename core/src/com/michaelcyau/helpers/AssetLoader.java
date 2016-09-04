package com.michaelcyau.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture bunnyTexture, bellTexture;
    public static TextureRegion bunnyRight, bunnyLeft, bell;
    public static Music bgMusic;
    public static Sound ring;
    public static BitmapFont font;

    public static void load() {
        bunnyTexture = new Texture(Gdx.files.internal("data/bunny.png"));
        bellTexture = new Texture(Gdx.files.internal("data/bell.png"));
        bunnyTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bellTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        bunnyRight = new TextureRegion(bunnyTexture);
        bunnyRight.flip(false, true);
        bunnyLeft = new TextureRegion(bunnyTexture);
        bunnyLeft.flip(true, true);

        bell = new TextureRegion(bellTexture);
        bell.flip(false, true);

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("mus/young_love.mp3"));
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.5f);

        ring = Gdx.audio.newSound(Gdx.files.internal("mus/ring.wav"));

        font = new BitmapFont(Gdx.files.internal("data/text2.fnt"), true);
        font.getData().setScale(0.05f, 0.05f);
    }

    public static void dispose() {
        bunnyTexture.dispose();
        bellTexture.dispose();
        bgMusic.dispose();
        ring.dispose();
        font.dispose();
    }

}