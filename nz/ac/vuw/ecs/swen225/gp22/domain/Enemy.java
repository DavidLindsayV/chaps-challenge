package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.List;

/**
 * This class represents an enemy that moves in a predetermined path.
 * If the player moves into this enemy, it will DIE.
 */
public class Enemy {
    /**
     * Current 'frame' of the enemy
     */
    private int currentFrameIndex;

    /**
     * Path of the enemy, the path should be contiguous.
     */
    private List<Point> path;

    /**
     * -------------------- PUBLIC API -----------------------------------------
     */

    /**
     * Constructs an enemy with a predetermined path
     */
    public Enemy(List<Point> path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Invalid enemy path.");
        }

        if (path.stream().anyMatch(p -> p == null)) {
            throw new IllegalArgumentException("Cannot have null points in path.");
        }
        
        this.path = path;
        this.currentFrameIndex = 0;
    }

    /**
     * Returns the current position of the enemy.
     * @return Point object.
     */
    public Point currentPosition() {
        return this.path.get(currentFrameIndex);
    }


    /**
     * Does the position passed into the program collide with.
     */
    public Boolean collidesWith(Point pos) {
        return currentPosition().equals(pos);
    }

    /**
     * Advance the enemy forwards in their path.
     * Call this every ping.
     */
    public void move() {
        this.currentFrameIndex = this.currentFrameIndex == path.size() - 1 
                                 ? 0 
                                 : this.currentFrameIndex + 1;
    }

    /**
     * String representation of the enemy
     */
    public String toString() {
        return "4";
    }
}
