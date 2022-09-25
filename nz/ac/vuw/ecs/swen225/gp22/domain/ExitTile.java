package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents the exit tile.
 * Upon stepping on this tile, it will move the player to the next level.
 */
public class ExitTile implements Tile {
    public ExitTile() {
    
    }

    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public void acceptPlayer(Player player) {
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
