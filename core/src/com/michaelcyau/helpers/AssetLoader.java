package com.michaelcyau.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class AssetLoader {

    public static Texture bunnyTexture, bellTexture, starTexture;
    public static TextureRegion bunnyRight, bunnyLeft, bell, star;
    public static Texture birdTexture;
    public static Animation birdAnimation;
    public static TextureRegion bird1, bird2, bird3, bird4, bird5, bird6, bird7, bird8;
    public static Music bgMusic;
    public static Sound ring;
    public static BitmapFont font;
    public static BitmapFont scoreFont;

    public static int maxFontSize = 84;
    public static int maxScoreFontSize = 128;
    public static int maxScreenWidth = 2160;

    public static Texture splashScreenTexture;
    public static TextureRegion splashScreen;

    public static void load() {
        bunnyTexture = new Texture(Gdx.files.internal("data/bunny.png"));
        bellTexture = new Texture(Gdx.files.internal("data/bell.png"));
        starTexture = new Texture(Gdx.files.internal("data/star.png"));
        bunnyTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bellTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        starTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        bunnyRight = new TextureRegion(bunnyTexture);
        bunnyRight.flip(false, true);
        bunnyLeft = new TextureRegion(bunnyTexture);
        bunnyLeft.flip(true, true);

        bell = new TextureRegion(bellTexture);
        bell.flip(false, true);

        star = new TextureRegion(starTexture);
        star.flip(false, true);

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("mus/young_love.mp3"));
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.5f);

        ring = Gdx.audio.newSound(Gdx.files.internal("mus/ring3.wav"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BubblegumSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (maxFontSize * ((float) Gdx.graphics.getWidth() / maxScreenWidth));
        parameter.minFilter = TextureFilter.Linear;
        parameter.magFilter = TextureFilter.Linear;
        font = generator.generateFont(parameter);

        parameter.size = (int) (maxScoreFontSize * ((float) Gdx.graphics.getWidth() / maxScreenWidth));
        scoreFont = generator.generateFont(parameter);

        generator.dispose();

        splashScreenTexture = new Texture(Gdx.files.internal("data/splash.png"));
        splashScreenTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        splashScreen = new TextureRegion(splashScreenTexture);

        birdTexture = new Texture(Gdx.files.internal("data/bird.png"));
        birdTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bird1 = new TextureRegion(birdTexture, 0, 0, 255, 200);
        bird2 = new TextureRegion(birdTexture, 255, 0, 255, 200);
        bird3 = new TextureRegion(birdTexture, 510, 0, 255, 200);
        bird4 = new TextureRegion(birdTexture, 765, 0, 255, 200);
        bird5 = new TextureRegion(birdTexture, 0, 200, 255, 200);
        bird6 = new TextureRegion(birdTexture, 255, 200, 255, 200);
        bird7 = new TextureRegion(birdTexture, 510, 200, 255, 200);
        bird8 = new TextureRegion(birdTexture, 765, 200, 255, 200);
        bird1.flip(false, true);
        bird2.flip(false, true);
        bird3.flip(false, true);
        bird4.flip(false, true);
        bird5.flip(false, true);
        bird6.flip(false, true);
        bird7.flip(false, true);
        bird8.flip(false, true);

        TextureRegion[] birds = { bird1, bird2, bird3, bird4, bird5, bird6, bird7, bird8 };
        birdAnimation = new Animation(0.106f, birds);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public static void dispose() {
        bunnyTexture.dispose();
        bellTexture.dispose();
        bgMusic.dispose();
        ring.dispose();
        font.dispose();
    }

}