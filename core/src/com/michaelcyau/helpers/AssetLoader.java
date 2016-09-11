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
    public static Sound ring, doubleScore, confirm;
    public static BitmapFont font;
    public static BitmapFont scoreFont;

    public static int maxFontSize = 84;
    public static int maxScoreFontSize = 128;
    public static int maxScreenWidth = 2160;

    public static Texture splashScreenTexture, instructionsTexture, instructionsTextureBlack;
    public static TextureRegion splashScreen, instructions, instructionsBlack;

    public static Texture catTexture;
    public static Animation catAnimationR, catAnimationL,
            catStationaryAnimationR, catStationaryAnimationL,
            catJumpAnimationR, catJumpAnimationL,
            catFallAnimationR, catFallAnimationL;
    public static TextureRegion catR[], catL[],
            catStationaryR[], catStationaryL[],
            catJumpR[], catJumpL[],
            catFallR[], catFallL[];

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
        confirm = Gdx.audio.newSound(Gdx.files.internal("mus/Item2A.wav"));

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

        catTexture = new Texture(Gdx.files.internal("data/cat.png"));
        catTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        catR = new TextureRegion[13];
        catL = new TextureRegion[13];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                catR[(4 * i) + j] = new TextureRegion(catTexture, 900 + (j * 225), 500 + (i * 125), 225, 125);
                catR[(4 * i) + j].flip(false, true);
                catL[(4 * i) + j] = new TextureRegion(catTexture, 900 + (j * 225), 500 + (i * 125), 225, 125);
                catL[(4 * i) + j].flip(true, true);
                if ((4 * i) + j == 12) {
                    break;
                }
            }
        }
        catAnimationR = new Animation(0.033f, catR);
        catAnimationR.setPlayMode(Animation.PlayMode.LOOP);
        catAnimationL = new Animation(0.033f, catL);
        catAnimationL.setPlayMode(Animation.PlayMode.LOOP);

        catStationaryR = new TextureRegion[3];
        catStationaryL = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            catStationaryR[i] = new TextureRegion(catTexture, 1125 + (i * 225), 375, 225, 125);
            catStationaryR[i].flip(false, true);
            catStationaryL[i] = new TextureRegion(catTexture, 1125 + (i * 225), 375, 225, 125);
            catStationaryL[i].flip(true, true);
        }
        catStationaryAnimationR = new Animation(0.25f, catStationaryR);
        catStationaryAnimationR.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        catStationaryAnimationL = new Animation(0.25f, catStationaryL);
        catStationaryAnimationL.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        catJumpR = new TextureRegion[7];
        catJumpL = new TextureRegion[7];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                catJumpR[(4 * i) + j] = new TextureRegion(catTexture, (j * 225), 125 + (i * 125), 225, 125);
                catJumpR[(4 * i) + j].flip(false, true);
                catJumpL[(4 * i) + j] = new TextureRegion(catTexture, (j * 225), 125 + (i * 125), 225, 125);
                catJumpL[(4 * i) + j].flip(true, true);
                if ((4 * i) + j == 6) {
                    break;
                }
            }
        }
        catJumpAnimationR = new Animation(0.044f, catJumpR);
        catJumpAnimationR.setPlayMode(Animation.PlayMode.LOOP);
        catJumpAnimationL = new Animation(0.044f, catJumpL);
        catJumpAnimationL.setPlayMode(Animation.PlayMode.LOOP);

        catFallR = new TextureRegion[4];
        catFallL = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            catFallR[i] = new TextureRegion(catTexture, 0 + (i * 225), 500, 225, 125);
            catFallR[i].flip(false, true);
            catFallL[i] = new TextureRegion(catTexture, 0 + (i * 225), 500, 225, 125);
            catFallL[i].flip(true, true);
        }
        catFallAnimationR = new Animation(0.033f, catFallR);
        catFallAnimationR.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        catFallAnimationL = new Animation(0.033f, catFallL);
        catFallAnimationL.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

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