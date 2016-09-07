package com.michaelcyau.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class AssetLoader {

    public static Texture bunnyTexture, bellTexture, starTexture, giftTexture;
    public static TextureRegion bunnyRight, bunnyLeft, bell, star, gift;
    public static Texture birdTexture;
    public static Animation birdAnimationr, birdAnimationl;
    public static TextureRegion bird1r, bird2r, bird3r, bird4r, bird5r, bird6r, bird7r, bird8r;
    public static TextureRegion bird1l, bird2l, bird3l, bird4l, bird5l, bird6l, bird7l, bird8l;
    public static Music bgMusic;
    public static Sound ring, doubleScore;
    public static BitmapFont font;
    public static BitmapFont scoreFont;

    public static int maxFontSize = 84;
    public static int maxScoreFontSize = 128;
    public static int maxScreenWidth = 2160;

    public static Texture splashScreenTexture, instructionsTexture;
    public static TextureRegion splashScreen, instructions;

    public static Preferences prefs;

    public static void load() {
        bunnyTexture = new Texture(Gdx.files.internal("data/bunny.png"));
        bellTexture = new Texture(Gdx.files.internal("data/bell4.png"));
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

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("mus/Good_Starts.mp3"));
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.5f);

        ring = Gdx.audio.newSound(Gdx.files.internal("mus/ring10.wav"));
        doubleScore = Gdx.audio.newSound(Gdx.files.internal("mus/ring5.wav"));

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

        instructionsTexture = new Texture(Gdx.files.internal("data/splash2.png"));
        instructionsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        instructions = new TextureRegion(instructionsTexture);

        birdTexture = new Texture(Gdx.files.internal("data/bird.png"));
        birdTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bird1r = new TextureRegion(birdTexture, 0, 0, 255, 200);
        bird2r = new TextureRegion(birdTexture, 255, 0, 255, 200);
        bird3r = new TextureRegion(birdTexture, 510, 0, 255, 200);
        bird4r = new TextureRegion(birdTexture, 765, 0, 255, 200);
        bird5r = new TextureRegion(birdTexture, 0, 200, 255, 200);
        bird6r = new TextureRegion(birdTexture, 255, 200, 255, 200);
        bird7r = new TextureRegion(birdTexture, 510, 200, 255, 200);
        bird8r = new TextureRegion(birdTexture, 765, 200, 255, 200);
        bird1r.flip(false, true);
        bird2r.flip(false, true);
        bird3r.flip(false, true);
        bird4r.flip(false, true);
        bird5r.flip(false, true);
        bird6r.flip(false, true);
        bird7r.flip(false, true);
        bird8r.flip(false, true);
        bird1l = new TextureRegion(birdTexture, 0, 0, 255, 200);
        bird2l = new TextureRegion(birdTexture, 255, 0, 255, 200);
        bird3l = new TextureRegion(birdTexture, 510, 0, 255, 200);
        bird4l = new TextureRegion(birdTexture, 765, 0, 255, 200);
        bird5l = new TextureRegion(birdTexture, 0, 200, 255, 200);
        bird6l = new TextureRegion(birdTexture, 255, 200, 255, 200);
        bird7l = new TextureRegion(birdTexture, 510, 200, 255, 200);
        bird8l = new TextureRegion(birdTexture, 765, 200, 255, 200);
        bird1l.flip(true, true);
        bird2l.flip(true, true);
        bird3l.flip(true, true);
        bird4l.flip(true, true);
        bird5l.flip(true, true);
        bird6l.flip(true, true);
        bird7l.flip(true, true);
        bird8l.flip(true, true);

        TextureRegion[] birdsr = { bird1r, bird2r, bird3r, bird4r, bird5r, bird6r, bird7r, bird8r };
        birdAnimationr = new Animation(0.16f, birdsr);
        birdAnimationr.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] birdsl = { bird1l, bird2l, bird3l, bird4l, bird5l, bird6l, bird7l, bird8l };
        birdAnimationl = new Animation(0.16f, birdsl);
        birdAnimationl.setPlayMode(Animation.PlayMode.LOOP);

        giftTexture = new Texture(Gdx.files.internal("data/gift.png"));
        giftTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        gift = new TextureRegion(giftTexture);
        gift.flip(false, true);

        prefs = Gdx.app.getPreferences("FrostyFriends");

        if (!prefs.contains("highScore")) {
            prefs.putString("highScore", "0");
            prefs.flush();
        }
    }

    public static void setHighScore(String val) {
        prefs.putString("highScore", val);
        prefs.flush();
    }

    public static String getHighScore() {
        return prefs.getString("highScore");
    }

    public static void dispose() {
        bunnyTexture.dispose();
        bellTexture.dispose();
        bgMusic.dispose();
        ring.dispose();
        font.dispose();
    }

}