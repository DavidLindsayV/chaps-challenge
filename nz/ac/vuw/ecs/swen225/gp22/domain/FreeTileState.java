package nz.ac.vuw.ecs.swen225.gp22.domain;

public interface FreeTileState {
    /**
     * Is the tile an obstruction, UNDER THIS STATE.
     *      If this is true --> Do not let the player walk through.
     *      Otherwise --> The player can walk through.
     * 
     * @return Obstruction boolean.
     */
    public boolean isWall();

    /**
     * Perform action on the parent tile,
     * given the state.
     * 
     * For example, the player steps onto the key tile.
     * The player --> gains a key
     * The tile --> reverts to a state of empty.
     */
    public void performAction(Player p, FreeTile tile);

    /**
     * The English name of the tile.
     * @return 
     */
    public String name();

    /**
     * A one character representation of the tile.
     * @return
     */
    public String toString();
}
