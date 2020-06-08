package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameMainClass extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	private Music rain;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		rain = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        font.getData().setScale(2, 2);
		rain.setLooping(true);
		rain.play();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
       super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		rain.dispose();


	}
}

