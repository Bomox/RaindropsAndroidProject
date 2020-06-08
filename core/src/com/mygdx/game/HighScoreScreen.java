package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;

public class HighScoreScreen implements Screen {
    final GameMainClass game;
    private static ArrayList<Integer> Highscores = new ArrayList<Integer>();
    public int HighScore = GameScreen.dropsGathered;
    private OrthographicCamera camera;
    private static Preferences prefs = Gdx.app.getPreferences("My Preferences");

    public HighScoreScreen(GameMainClass game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    public static void addScore(int newScore){

        if(newScore > Highscores.get(0)){
            Highscores.set(0, newScore);
        }
        if(newScore > Highscores.get(1) && newScore < Highscores.get(0) ){
            Highscores.set(1, newScore);
        }
        if(newScore > Highscores.get(2) && newScore < Highscores.get(1)){
            Highscores.set(2, newScore);
        }
        int Scoreboardsize = 3;
        if(Highscores.size() > Scoreboardsize){
            Highscores.remove(Scoreboardsize);
        }

    }

    public static void savePrefs(){
        prefs.putInteger("high_score_1", Highscores.get(0));
        prefs.putInteger("high_score_2", Highscores.get(1));
        prefs.putInteger("high_score_3", Highscores.get(2));

        prefs.flush();
    }

    public static void loadPrefs(){

        prefs = Gdx.app.getPreferences("game-prefs");
        Highscores.add(prefs.getInteger("high_score_1",200));
        Highscores.add(prefs.getInteger("high_score_2",150));
        Highscores.add(prefs.getInteger("high_score_3",100));

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        loadPrefs();
        addScore(HighScore);
        savePrefs();

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "HighScores:", 250, 340);
        game.font.draw(game.batch, Highscores.get(0).toString(), 250, 300);
        game.font.draw(game.batch, Highscores.get(1).toString(), 250, 260);
        game.font.draw(game.batch, Highscores.get(2).toString(), 250, 220);
        game.font.draw(game.batch,"Tap to restart the game", 250, 180);
        game.batch.end();

        if (Gdx.input.justTouched()) {
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
