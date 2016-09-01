package com.tomatrocho.game.world.tile;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.tomatrocho.game.AssetContainer;
import com.tomatrocho.game.entity.mob.Player;
import com.tomatrocho.game.world.World;

public class EmptyBlock extends InteractiveTileObject {

	/**
	 * 
	 */
	protected TiledMapTile tile;
	
	
	/**
	 * 
	 * @param world
	 * @param bodyDef
	 * @param shape
	 * @param rectangle
	 */
	public EmptyBlock(World world, Vector2 pos) {
		super(world, pos);
		
		this.tile = world.getTileSet().getTile(28);
	}
	
	/**
	 * 
	 */
	public void init() {
		super.init();
		
		getCell().setTile(tile);
	}

	@Override
	public void onHeadHit(Player player) {
		AssetContainer.get("audio/sounds/bump.wav", Sound.class).play();
	}
	
	/**
	 * 
	 * @return
	 */
	public TiledMapTile getTile() {
		return tile;
	}
}
