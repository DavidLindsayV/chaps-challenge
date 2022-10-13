package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Sounds.SoundEffects;

/**
 * Represents the exit tile.
 * Upon stepping on this tile, it will move the player to the next level.
 * 
 * @author Brandon Ru 300562436
 */
public class ExitTile implements Tile {
	/**
	 * Default constructor.
	 */
    public ExitTile() {
    
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public void acceptPlayer(Player player) {
        SoundEffects.playSound("Win");
        player.nextLevel();
    }

    @Override
    public String name() {
        return "exit";
    }

    @Override
    public String toString() {
        return "E";
    }
}
