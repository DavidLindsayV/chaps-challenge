package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;

/**
 * This abstract class represents an enemy that moves in a predetermined path.
 * If the player moves into this enemy, it will DIE.
 * If the enemy moves into this player, it will DIE.
 * 
 * @author Brandon Ru 300562436
 */
public abstract class Enemy {
    /**
     * Returns the current position of the enemy.
     * 
     * @return Point object.
     */
    public abstract Point getPosition();

    /**
     * Advance the enemy forwards in their path.
     * Call this every ping.
     */
    public abstract void move();

    /**
     * Returns the path
     * 
     * @return
     */
    public abstract List<Point> getPath();

    /**
     * String representation of the enemy
     */
    public abstract String toString();

    /**
     * Returns the position of the enemy
     * 
     * @return Point object.
     */
    public Point getGraphicalPosition() {
        return getPosition().translate(Domain.GRAPHICAL_PADDING, Domain.GRAPHICAL_PADDING);
    }

    /**
     * Does the position passed into the program collide with.
     */
    public Boolean collidesWith(Point pos) {
        return getPosition().equals(pos);
    }
}
