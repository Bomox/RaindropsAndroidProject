package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

import java.util.Iterator;
import java.util.Random;

public class GameScreen implements Screen {
    final GameMainClass game;
    private Texture waterdrop;
    private Texture bucket;
    private Sound raindrop_sound;
    private OrthographicCamera camera;
    private Rectangle rectangle_bucket;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    public static int dropsGathered;
    private int lives = 3;

    public GameScreen(GameMainClass game){
        this.game = game;
        waterdrop = new Texture(Gdx.files.internal("water_drop.png"));
        bucket = new Texture(Gdx.files.internal("bucket.png"));
        raindrop_sound = Gdx.audio.newSound(Gdx.files.internal("water_drop_sound.mp3"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        rectangle_bucket = new Rectangle();
        rectangle_bucket.x =  800 / 2 - 64 / 2;
        rectangle_bucket.y = 20;
        rectangle_bucket.height = 64;
        rectangle_bucket.width = 180;
        raindrops = new Array<Rectangle>();
    }

    private void SpawnRaindrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 100;
        raindrop.height = 50;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Raindrops  Collected : " + dropsGathered, 20, 480);
        game.font.draw(game.batch, "Lives : " + lives, 20, 440);
        game.batch.draw(bucket, rectangle_bucket.x, rectangle_bucket.y, rectangle_bucket.width, rectangle_bucket.height);
        for (Rectangle raindrop : raindrops) {
            game.batch.draw(waterdrop, raindrop.x, raindrop.y);
        }
        if(lives == 0){
            game.font.draw(game.batch, "Game Over!", 800 / 2 - 64, 500 / 2);
            game.font.draw(game.batch, "Tap to view high scores", 800 / 2 - 130, 440 / 2);

        }
        game.batch.end();

       if(lives > 0) {
           if (Gdx.input.isTouched()) {
               Vector3 touchPosition = new Vector3();
               touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
               camera.unproject(touchPosition);
               rectangle_bucket.x = (int) (touchPosition.x - 64 / 2);
           }

           int dropSpeed = 200;
           int dropSpeedTime = 1000000000;
           Iterator<Rectangle> iterator = raindrops.iterator();
           if (TimeUtils.nanoTime() - lastDropTime > dropSpeedTime) {
               SpawnRaindrop();
           }
           while (iterator.hasNext()) {
               Rectangle raindrop = iterator.next();
               raindrop.y -= dropSpeed * Gdx.graphics.getDeltaTime();
               if (dropsGathered >= 15) {
                   dropSpeed += 2;
                   dropSpeedTime = 900000000;
                   if (TimeUtils.nanoTime() - lastDropTime > dropSpeedTime)
                       SpawnRaindrop();
                   raindrop.y -= dropSpeed * Gdx.graphics.getDeltaTime();
               }
               if (dropsGathered >= 50) {
                   dropSpeed += 4;
                   dropSpeedTime = 500000000;
                   if (TimeUtils.nanoTime() - lastDropTime > dropSpeedTime)
                       SpawnRaindrop();
                   raindrop.y -= dropSpeed * Gdx.graphics.getDeltaTime();
               }
               if (dropsGathered >= 100) {
                   dropSpeed += 5;
                   dropSpeedTime = 400000000;
                   if (TimeUtils.nanoTime() - lastDropTime > dropSpeedTime)
                       SpawnRaindrop();
                   raindrop.y -= dropSpeed * Gdx.graphics.getDeltaTime();
               }
               if (dropsGathered >= 200) {
                   dropSpeedTime = 200000000;
                   if (TimeUtils.nanoTime() - lastDropTime > dropSpeedTime)
                       SpawnRaindrop();
               }
               if (raindrop.y + 64 < 0) {
                   iterator.remove();
                   lives--;
                   Gdx.input.vibrate(200);
               }
               if (raindrop.overlaps(rectangle_bucket)) {
                   dropsGathered++;
                   raindrop_sound.play();
                   iterator.remove();
               }
           }
       }
        else{
            if (Gdx.input.justTouched()) {
                game.setScreen(new HighScoreScreen(game));
                dispose();
                dropsGathered = 0;
            }
        }
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        waterdrop.dispose();
        bucket.dispose();
        raindrop_sound.dispose();
    }
}
