package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a wall tile.
 * Can be implemented as a singleton as all walls are the same.
 */
public class WallTile implements Tile {
    private static final WallTile singleton = new WallTile();

    private WallTile() {
        
    }

    @Override
    public boolean isWall() {
        return true;
    }

    @Override
    public void acceptPlayer(Player player) {
        return;
    }

    public static WallTile of() {
        return WallTile.singleton;
    }
}
