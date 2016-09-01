package com.tomatrocho.game.world.tile;

import com.badlogic.gdx.math.Vector2;
import com.tomatrocho.game.world.World;

public class Ground extends TileObject {
	
	/**
	 * 
	 * @param world
	 * @param pos
	 * @param size
	 */
	public Ground(World world, Vector2 pos, Vector2 size) {
		super(world, pos, size);
	}
}