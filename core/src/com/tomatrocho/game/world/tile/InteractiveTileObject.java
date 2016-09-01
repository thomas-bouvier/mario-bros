package com.tomatrocho.game.world.tile;

import com.badlogic.gdx.math.Vector2;
import com.tomatrocho.game.entity.Entity;
import com.tomatrocho.game.entity.mob.Player;
import com.tomatrocho.game.world.World;

public abstract class InteractiveTileObject extends TileObject {

	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public InteractiveTileObject(World world, Vector2 pos) {
		super(world, pos);
		
		fixtureDef.filter.categoryBits = Entity.BLOCK_BIT;
	}

	/**
	 * 
	 */
	public abstract void onHeadHit(Player player);
}
