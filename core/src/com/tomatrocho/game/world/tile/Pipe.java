package com.tomatrocho.game.world.tile;

import com.badlogic.gdx.math.Vector2;
import com.tomatrocho.game.entity.Entity;
import com.tomatrocho.game.world.World;

public class Pipe extends TileObject {

	/**
	 * 
	 * @param world
	 * @param pos
	 * @param size
	 */
	public Pipe(World world, Vector2 pos, Vector2 size) {
		super(world, pos, size);
		
		fixtureDef.filter.categoryBits = Entity.PIPE_BIT;
	}
}
