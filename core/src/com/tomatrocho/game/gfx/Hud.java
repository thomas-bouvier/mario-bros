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
	private Viewport viewport;

	/**
	 * 
	 */
	private Stage stage;
	
	/**
	 * 
	 */
	private Label scoreLabel;
	
	/**
	 * 
	 */
	private Label countdownLabel;
	
	
	/**
	 * 
	 * @param spriteBatch
	 */
	public Hud(SpriteBatch spriteBatch) {
		this.viewport = new FitViewport(MarioBros.V_W, MarioBros.V_H, new OrthographicCamera());
		this.stage = new Stage(viewport, spriteBatch);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		
		table.add(new Label("Score", labelStyle)).expandX().padTop(8);
		table.add(new Label("Time", labelStyle)).expandX().padTop(8);
		
		table.row();
		
		scoreLabel = new Label("", labelStyle);
		countdownLabel = new Label("", labelStyle);
		
		table.add(scoreLabel);
		table.add(countdownLabel);
		
		stage.addActor(table);
	}
	
	/**
	 * 
	 * @param score
	 */
	public void setScore(int score) {
		scoreLabel.setText(String.format("%06d", score));
	}
	
	/**
	 * 
	 * @param levelTimer
	 */
	public void setLevelTimer(int levelTimer) {
		countdownLabel.setText(String.format("%03d", levelTimer));
	}
	
	/**
	 * 
	 * @return
	 */
	public Stage getStage() {
		return stage;
	}
}
