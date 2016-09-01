package com.tomatrocho.game.world.tile;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.tomatrocho.game.AssetContainer;
import com.tomatrocho.game.entity.mob.Player;
import com.tomatrocho.game.world.World;

public class BrickBlock extends InteractiveTileObject {
	
	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public BrickBlock(World world, Vector2 pos) {
		super(world, pos);
	}

	@Override
	public void onHeadHit(Player player) {
		if (player.grown()) {
			AssetContainer.get("audio/sounds/breakblock.wav", Sound.class).play();
			
			world.addBodyToDestroy(body);
			getCell().setTile(null);			
		}
		else {
			AssetContainer.get("audio/sounds/bump.wav", Sound.class).play();
		}
	}
}
