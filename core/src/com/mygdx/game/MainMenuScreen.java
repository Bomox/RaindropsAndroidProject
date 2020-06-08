package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

     final GameMainClass game;
     private OrthographicCamera camera;


    public MainMenuScreen(final GameMainClass game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
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
         game.font.draw(game.batch, "Welcome to Raindrops", 250, 340);
         game.font.draw(game.batch, "Tap anywhere to begin", 250, 300);
         game.font.draw(game.batch, "Catch the raindrops with the bucket", 190, 260);
         game.batch.end();

         if (Gdx.input.isTouched()) {
             game.setScreen(new GameScreen(game));
             dispose();
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

     }
 }

