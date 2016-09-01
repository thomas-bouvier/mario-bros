package com.tomatrocho.game.world.tile;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.tomatrocho.game.AssetContainer;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.item.Mushroom;
import com.tomatrocho.game.entity.mob.Player;
import com.tomatrocho.game.world.World;

public class QuestionBlock extends InteractiveTileObject {
	
	/**
	 * 
	 */
	public static final int SCORE = 200;
	
	/**
	 * 
	 */
	private boolean spawnMushroom = false;

	
	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public QuestionBlock(World world, Vector2 pos) {
		super(world, pos);
	}
	
	/**
	 * 
	 * @param value
	 */
	public void enableMushroomSpawning(boolean value) {
		this.spawnMushroom = value;
	}

	@Override
	public void onHeadHit(Player player) {
		world.getLevel().addScore(SCORE);

		world.setTileObject(new EmptyBlock(world, body.getPosition()));
		world.addBodyToDestroy(body);
		
		if (spawnMushroom) {
			AssetContainer.get("audio/sounds/powerup_spawn.wav", Sound.class).play();

			world.spawnItem(new Mushroom(world, new Vector2(body.getPosition().x, body.getPosition().y + 16 / MarioBros.PPM)));
		}
		else {
			AssetContainer.get("audio/sounds/coin.wav", Sound.class).play();
		}
	}
}
