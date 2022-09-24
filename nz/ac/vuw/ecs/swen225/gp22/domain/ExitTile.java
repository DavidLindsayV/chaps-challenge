package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a wall tile.
 * Can be implemented as a singleton as all walls are the same.
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
