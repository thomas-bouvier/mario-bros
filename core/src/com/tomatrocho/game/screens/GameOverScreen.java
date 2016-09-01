package com.tomatrocho.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tomatrocho.game.MarioBros;

public class GameOverScreen implements Screen {
	
	/**
	 * 
	 */
	private MarioBros instance;
	
	/**
	 * 
	 */
	private Viewport viewport;
	
	/**
	 * 
	 */
	private Stage stage;
	
	
	/**
	 * 
	 * @param instance
	 */
	public GameOverScreen(MarioBros instance) {
		this.instance = instance;
		
		this.viewport = new FitViewport(MarioBros.V_W, MarioBros.V_H, new OrthographicCamera());
		this.stage = new Stage(viewport, instance.getSpriteBatch());
		
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		
		Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		
		table.add(new Label("GAME OVER", labelStyle)).expandX();
		table.row();
		table.add(new Label("Click to play again", labelStyle)).expandX().padTop(4);
		
		stage.addActor(table);
	}
	
	/**
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		if (Gdx.input.justTouched()) {
			instance.setScreen(new GameScreen(instance));
			dispose();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
