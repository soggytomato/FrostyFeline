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
    public static Texture bgTexture;
    public static TextureRegion bg;
    public static Music bgMusic;
    public static Sound ring, doubleScore;
    public static BitmapFont font;
    public static BitmapFont scoreFont;

    public static int maxFontSize = 84;
    public static int maxScoreFontSize = 128;
    public static int maxScreenWidth = 2160;

    public static Texture splashScreenTexture, instructionsTexture, instructionsTextureBlack;
    public static TextureRegion splashScreen, instructions, instructionsBlack;

    public static Texture catTexture;
    public static Animation catAnimationR, catAnimationL;
    public static TextureRegion catR[];
    public static TextureRegion catL[];

    public static Preferences prefs;

    public static void load() {
        bunnyTexture = new Texture(Gdx.files.internal("data/bunny.png"));
        bellTexture = new Texture(Gdx.files.internal("data/bell4.png"));
        starTexture = new Texture(Gdx.files.internal("data/star.png"));
        bunnyTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bellTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        starTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        bgTexture = new Texture(Gdx.files.internal("data/bg.png"));
        bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bg = new TextureRegion(bgTexture);
        bg.flip(false, true);

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

        instructionsTextureBlack = new Texture(Gdx.files.internal("data/splash2_black.png"));
        instructionsTextureBlack.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        instructionsBlack = new TextureRegion(instructionsTextureBlack);

        giftTexture = new Texture(Gdx.files.internal("data/gift.png"));
        giftTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        gift = new TextureRegion(giftTexture);
        gift.flip(false, true);

        catTexture = new Texture(Gdx.files.internal("data/sheet.png"));
        catTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        catR = new TextureRegion[13];
        catL = new TextureRegion[13];
        for (int i = 0; i < 13; i++) {
            catR[i] = new TextureRegion(catTexture, 0, i * 92, 200, 92);
            catR[i].flip(false, true);
            catL[i] = new TextureRegion(catTexture, 0, i * 92, 200, 92);
            catL[i].flip(true, true);
        }
        catAnimationR = new Animation(0.033f, catR);
        catAnimationR.setPlayMode(Animation.PlayMode.LOOP);
        catAnimationL = new Animation(0.033f, catL);
        catAnimationL.setPlayMode(Animation.PlayMode.LOOP);

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