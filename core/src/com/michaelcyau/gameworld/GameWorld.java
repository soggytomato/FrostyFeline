package com.michaelcyau.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.michaelcyau.gameobjects.Bell;
import com.michaelcyau.gameobjects.Gift;
import com.michaelcyau.gameobjects.Bunny;
import com.michaelcyau.gameeffects.ScoreEffect;
import com.michaelcyau.gameobjects.Collectible;
import com.michaelcyau.gameobjects.Snowflake;
import com.badlogic.gdx.math.MathUtils;
import com.michaelcyau.helpers.AssetLoader;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class GameWorld {

    private GameState currentState;

    public enum GameState {
        SPLASH, INSTRUCTIONS, RUNNING, GAMEOVER
    }

    private int numSnowflakes = 50;

    private float panUpBoundary = 0.60f; // as a fraction of screen height
    private float panDownBoundary = 0.60f; // as a fraction of screen height
    private float bottomBuffer = 0.3f; // as a fraction of screen height
    private float topBuffer = 0.5f; // as a fraction of screen height
    private float bellInterval = 0.25f; // as a factor of GAME WIDTH (not screen width)
    private float bellMaxInterval = 0.4f;
    private float bellIntervalGrowthAmount = 0.00015f;
    private float collectibleXBoundary = 0.03f;

    private Bunny bunny;
    private List<Snowflake> snowflakes;
    private List<Collectible> collectibles;
    private List<Collectible> deadCollectibles;
    private Collectible topCollectible;
    private float newestBellPositionY;
    private float bellSize = 12f;
    private float minBellSize = 6f;
    private float bellShrinkAmount = 0.006f;
    private int newBells = 0;
    private int lastBirdBellNum = 0;
    private int birdInterval = 40;
    private int birdIntervalIncrement = 10;

    private List<ScoreEffect> scoreEffects;
    private List<ScoreEffect> deadScoreEffects;

    private int gameWidth;
    private int gameHeight;

    private float worldTop;
    private float worldTopMax;

    private BigInteger score = new BigInteger("0");
    private BigInteger nextScoreAdded = new BigInteger("10");
    private boolean started = false;

    private float splashTransparency = 0;
    private float splashFadeSpeed = 1f;
    private float splashDuration = 1;
    private float splashRunTime = 0;

    public GameWorld(int gameWidth, int gameHeight) {
        currentState = GameState.SPLASH;
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        worldTop = gameHeight;
        bunny = new Bunny(gameWidth/2, 0, this);
        initSnowflakes();
        initCollectibles();
        initScoreEffects();

    }

    public void update(float delta) {
        switch (currentState) {
            case GAMEOVER:
                updateGameOver(delta);
                // continue running
            case RUNNING:
                updateRunning(delta);
                break;
            case SPLASH:
                updateSplash(delta);
                break;
            case INSTRUCTIONS:
                updateInstructions(delta);
                break;
            default:
                break;
        }
    }

    private void updateSplash(float delta) {
        if (splashRunTime < splashDuration) {
            if (splashTransparency + (splashFadeSpeed * delta) < 1) {
                splashTransparency += splashFadeSpeed * delta;
            } else {
                splashTransparency = 1;
            }
            splashRunTime += delta;
        } else {
            if (splashTransparency - (splashFadeSpeed * delta) > 0) {
                splashTransparency -= splashFadeSpeed * delta;
            } else {
                splashTransparency = 0;
                currentState = GameState.INSTRUCTIONS;
            }
        }
    }

    private void updateInstructions(float delta) {
        if (!started) {
            if (splashTransparency + (splashFadeSpeed * delta) < 1) {
                splashTransparency += splashFadeSpeed * delta;
            } else {
                splashTransparency = 1;
            }
        } else {
            if (splashTransparency - (splashFadeSpeed * delta) > 0) {
                splashTransparency -= splashFadeSpeed * delta;
            } else {
                splashTransparency = 0;
                currentState = GameState.RUNNING;
                AssetLoader.bgMusic.play();
            }
        }

    }

    private void updateGameOver(float delta) {
        // do something
    }

    public void updateRunning(float delta) {
        updateBunny(delta);
        updateSnowflakes(delta);
        updateCollectibles(delta);
        generateCollectibles();
        updateScoreEffects(delta);
        detectCollisions();
    }

    public Bunny getBunny() {
        return bunny;
    }

    public List<Snowflake> getSnowflakes() {
        return snowflakes;
    }

    public List<Collectible> getCollectibles() {
        return collectibles;
    }

    public List<ScoreEffect> getScoreEffects() {
        return scoreEffects;
    }

    public int getWidth() {
        return gameWidth;
    }

    public int getHeight() {
        return gameHeight;
    }

    public float getWorldTop() {
        return worldTop;
    }

    public float getWorldTopMax() {
        return worldTopMax;
    }

    public float getTopBuffer() {
        return topBuffer;
    }

    public float getBottomBuffer() {
        return bottomBuffer;
    }

    public float getSplashTransparency() {
        return splashTransparency;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isSplash() {
        return currentState == GameState.SPLASH;
    }

    public boolean isInstructions() {
        return currentState == GameState.INSTRUCTIONS;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public String getScore() {
        return NumberFormat.getNumberInstance(Locale.US).format(score);
    }

    public BigInteger getBigIntScore() {
        return score;
    }

    public void removeCollectible(Collectible col) {
        deadCollectibles.add(col);
    }

    public void removeScoreEffect(ScoreEffect scoreEffect) {
        deadScoreEffects.add(scoreEffect);
    }

    public void endGame() {
        if (score.compareTo(new BigInteger(AssetLoader.getHighScore())) > 0) {
            AssetLoader.setHighScore(score.toString());
        }
        currentState = GameState.GAMEOVER;
    }

    public void start() {
        started = true;
    }

    private void initCollectibles() {
        collectibles = new LinkedList<Collectible>();
        deadCollectibles = new LinkedList<Collectible>();
        initBells();
    }

    private void initBells() {
        newestBellPositionY = gameWidth * 0.3f;
        while (newestBellPositionY < gameHeight * (1 + topBuffer)) {
            newestBellPositionY += (gameWidth * bellInterval);
            Bell bell = new Bell((gameWidth * 0.03f) + MathUtils.random((gameWidth * 0.94f) - bellSize), newestBellPositionY, bellSize, bellSize, this);
            collectibles.add(bell);
            topCollectible = bell;
            newBells++;
        }
    }

    private void initSnowflakes() {
        snowflakes = new LinkedList<Snowflake>();
        for (int i = 0; i < numSnowflakes; i++) {
            snowflakes.add(new Snowflake(MathUtils.random(gameWidth), MathUtils.random(-gameHeight, 2 * gameHeight), this));
        }
    }

    private void initScoreEffects() {
        scoreEffects = new LinkedList<ScoreEffect>();
        deadScoreEffects = new LinkedList<ScoreEffect>();
    }

    private void updateBunny(float delta) {
        bunny.update(delta);

        if (worldTop - bunny.getY() < gameHeight - (gameHeight * panUpBoundary)) {
            worldTop += (gameHeight - (gameHeight * panUpBoundary)) - (worldTop - bunny.getY());
        } else if (worldTop - bunny.getY() > gameHeight - (gameHeight * panDownBoundary)) {
            if (worldTop > gameHeight) {
                worldTop += ((gameHeight - (gameHeight * panDownBoundary)) - (worldTop - bunny.getY()));
                if (worldTop < gameHeight) {
                    worldTop = gameHeight;
                }
            }
        }
        worldTopMax = worldTop > worldTopMax ? worldTop : worldTopMax;
    }

    private void updateSnowflakes(float delta) {
        for (Snowflake snowflake: snowflakes) {
            snowflake.update(delta);
        }
    }

    private void updateCollectibles(float delta) {
        for (Collectible col: collectibles) {
            col.update(delta);
        }
        for (Collectible col: deadCollectibles) {
            collectibles.remove(col);
        }
        deadCollectibles.clear();
    }

    private void generateCollectibles() {
        if (worldTop > newestBellPositionY + (gameWidth * bellInterval)) {
            newestBellPositionY += gameWidth * bellInterval;
            if (newBells > lastBirdBellNum + birdInterval) {
                Gift gift = new Gift((gameWidth * collectibleXBoundary) + MathUtils.random((gameWidth * (1 - 2 * collectibleXBoundary)) - Gift.width), newestBellPositionY, this);
                collectibles.add(gift);
                topCollectible = gift;
                lastBirdBellNum = newBells;
                birdInterval += birdIntervalIncrement;
            } else {
                Bell bell = new Bell((gameWidth * collectibleXBoundary) + MathUtils.random((gameWidth * (1 - 2 * collectibleXBoundary)) - bellSize),
                        newestBellPositionY, bellSize, bellSize, this);
                collectibles.add(bell);
                topCollectible = bell;
                bellSize = bellSize - bellShrinkAmount < minBellSize ? minBellSize : bellSize - bellShrinkAmount;
            }
            bellInterval = bellInterval + bellIntervalGrowthAmount > bellMaxInterval ? bellMaxInterval : bellInterval + bellIntervalGrowthAmount;
            newBells++;
        }
        if (topCollectible.getY() < newestBellPositionY - (gameWidth * bellInterval)) {
            Bell bell = new Bell((gameWidth * collectibleXBoundary) + MathUtils.random((gameWidth * (1 - 2 * collectibleXBoundary)) - bellSize),
                    newestBellPositionY, bellSize, bellSize, this);
            collectibles.add(bell);
            topCollectible = bell;
            bellSize = bellSize - bellShrinkAmount < minBellSize ? minBellSize : bellSize - bellShrinkAmount;
            bellInterval = bellInterval + bellIntervalGrowthAmount > bellMaxInterval ? bellMaxInterval : bellInterval + bellIntervalGrowthAmount;
        }
    }

    private void updateScoreEffects(float delta) {
        for (ScoreEffect scoreEffect: scoreEffects) {
            scoreEffect.update(delta);
        }
        for (ScoreEffect scoreEffect: deadScoreEffects) {
            scoreEffects.remove(scoreEffect);
        }
        deadScoreEffects.clear();
    }

    private void detectCollisions() {
        for (Collectible col: collectibles) {
            Class c = col.getClass();
            if (bunny.getY() >= col.getY() - bellSize - bunny.getHeight() &&
                    bunny.getY() <= col.getY() + bellSize &&
                    !col.isDying()) {
                if (Intersector.overlaps(bunny.getBoundingCircle(), col.getBoundingCircle())) {
                    bunny.jump();
                    col.playSound();
                    col.die();
                    if (c == Bell.class) {
                        score = score.add(nextScoreAdded);
                        scoreEffects.add(new ScoreEffect(col.getX(), col.getY() + bellSize, col, NumberFormat.getNumberInstance(Locale.US).format(nextScoreAdded), this));
                        nextScoreAdded = nextScoreAdded.add(BigInteger.TEN);
                    } else if (c == Gift.class) {
                        score = score.multiply(new BigInteger("2"));
                        scoreEffects.add(new ScoreEffect(col.getX(), col.getY() + bellSize, col, "Double Score!", this));
                    }
                }
            }
        }
    }
}
