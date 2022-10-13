package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * WallTile represents a wall tile.
 * Walls cannot allow a player through.
 * Implemented using a singleton as all the walls are the same.
 * This saves memory.
 * 
 * @author Brandon Ru 300562436
 * 
 *
 */
public class WallTile implements Tile {
    private static final WallTile singleton = new WallTile();

    /**
     * WallTile private constructor so you can't call new WallTile()
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

    /**
     * Call WallTile.of() if you want to use a wall tile
     * @return WallTile singleton.
     */
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
