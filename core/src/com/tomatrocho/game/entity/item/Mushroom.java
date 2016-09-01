package com.tomatrocho.game.entity.item;

import com.badlogic.gdx.math.Vector2;
import com.tomatrocho.game.entity.mob.Player;
import com.tomatrocho.game.world.World;

public class Mushroom extends Item {

	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public Mushroom(World world, Vector2 pos) {
		super(world, pos);
	}

	@Override
	public void use(Player player) {
		flagToDestroy();
		destroyBody();
		
		if (!player.grown()) {			
			player.grow();
		}
	}
	
	@Override
	public String toString() {
		return "I'm a Mushroom!";
	}
}