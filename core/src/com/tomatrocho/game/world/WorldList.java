package com.tomatrocho.game.world;

import java.util.ArrayList;
import java.util.List;

public class WorldList {

	/**
	 * 
	 */
	private static List<WorldInformation> levels = new ArrayList<>();
	
	static {
        levels.add(new WorldInformation("level-1", "level-1.tmx"));
    }
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static WorldInformation getLevelByName(String name) {
    	for (WorldInformation levelInformation : levels) {
    		if (levelInformation.getName().equalsIgnoreCase(name)) {
    			return levelInformation;
    		}
    	}
    	return null;
    }
}
