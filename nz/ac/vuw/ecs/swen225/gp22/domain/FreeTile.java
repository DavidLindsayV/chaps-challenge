package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * A Free tile is a tile that can store 'something'.
 * That 'something', is referred to as it's 'state'.
 * List of possible states:
 *      - Empty
 *      - Key
 *      - ...
 */
public class FreeTile implements Tile {
    private FreeTileState state;

    /**
     * Can only use factory methods.
     */
    private FreeTile(FreeTileState state) {
        this.state = state;
    }

    /** 
     * ------------------------------PUBLIC API---------------------------------
     */
    @Override
    public boolean isWall() {
        return state.isWall();
    }

    @Override
    public void acceptPlayer(Player p) {
        state.performAction(p, this);
    }

    public void changeState(FreeTileState s) {
        this.state = s;
    }

    /** 
     * -----------------------------FACTORY METHODS-----------------------------
     */

    /**
     * Factory method for empty free tile.
     * @return Empty free tile.
     */
    public static FreeTile empty() {
        return new FreeTile(EmptyState.of());
    }

    /**
     * Factory method for treasure tile.
     * @return Empty free tile.
     */
    public static FreeTile treasure() {
        return new FreeTile(new TreasureState());
    }

    /**
     * Factory method for a new door.
     * @return Empty free tile.
     */
    public static FreeTile door(AuthenticationColour colour) {
        return new FreeTile(new LockedDoorState(colour));
    }

    /**
     * Factory method for a new key.
     * @return Empty free tile.
     */
    public static FreeTile key(AuthenticationColour colour) {
        return new FreeTile(new KeyState(colour));
    }

    /**
     * Factory method for an information tile.
     * @return Empty free tile.
     */
    public static FreeTile info() {
        return new FreeTile(new InformationState());
    }

    /**
     * Factory method for an exit lock tile.
     * @return Empty free tile.
     */
    public static FreeTile lock() {
        return new FreeTile(new ExitLockState());
    }

    /**
     * ----------------------------PRINTABLE FUNCTIONS--------------------------
     */
    @Override
    public String name() {
        return state.name();
    }

    @Override
    public String toString() {
        return state.toString();
    }

    @Override 
    public String colour() {
        return state.colour();
    }
}
