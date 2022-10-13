package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * This tile represents the information help tile.
 * When the player passes over it, show the tool tip. 
 *  
 * @author brandon
 *
 */
public class InformationState implements FreeTileState {
    @Override
    public boolean isWall() {
        return false;
    }

    @Override
    public void performAction(Player p, FreeTile tile) {
        Domain.showToolTip();
        return;        
    }

    @Override
    public String name() {
        return "info";
    }

    @Override
    public String toString() {
        return "i";
    }
}
