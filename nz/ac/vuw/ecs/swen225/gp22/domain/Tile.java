package nz.ac.vuw.ecs.swen225.gp22.domain;

public interface Tile {
    /**
     * Is the tile an obstruction.
     *      If this is true --> Do not let the player walk through.
     *      Otherwise --> The player can walk through.
     * 
     * @return Obstruction boolean.
     */
    public boolean isWall();

    /**
     * Visitor pattern.
     * Delegate what the player does on each tile to each internal tile.
     * 
     * Override this function for each individual tile,
     * and interact with the player accordingly.
     * For example, a key tile may accept a player,
     * and add a key to the players toolbelt.
     * 
     * @param player Player object.
     */
    public void acceptPlayer(Player player);

}
