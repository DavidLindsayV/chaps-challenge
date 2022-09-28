package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Represents a wall tile.
 * Cannot be passed through.
 * Can be implemented as a singleton as all walls are the same.
 */
public class WallTile implements Tile {
    private static final WallTile singleton = new WallTile();

    /**
     * Private constructor so you can never call:
     * new WallTile().
     */
    private WallTile() {}

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

    @Override
    public String name() {
        return "wall";
    }

    @Override
    public String toString() {
        return "#";
    }
}
