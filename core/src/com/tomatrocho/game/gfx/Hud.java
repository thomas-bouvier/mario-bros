package com.tomatrocho.game.gfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tomatrocho.game.MarioBros;

public class Hud {

	/**
	 * 
	 */
	private Stage stage;
	
	/**
	 * 
	 */
	private Viewport viewport;
	
	/**
	 * 
	 */
	private Label countdownLabel;
	
	
	/**
	 * 
	 * @param spriteBatch
	 */
	public Hud(SpriteBatch spriteBatch) {
		viewport = new FitViewport(MarioBros.V_W, MarioBros.V_H, new OrthographicCamera());
		stage = new Stage(viewport, spriteBatch);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		BitmapFont font = new BitmapFont();
		countdownLabel = new Label("<3 tomatrocho <3", new Label.LabelStyle(font, Color.WHITE));
		
		table.add(countdownLabel).expandX().padTop(10);
		
		stage.addActor(table);
	}
	
	/**
	 * 
	 * @return
	 */
	public Stage getStage() {
		return stage;
	}
}
