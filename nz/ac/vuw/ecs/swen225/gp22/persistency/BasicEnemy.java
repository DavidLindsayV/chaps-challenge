package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.Enemy;
import nz.ac.vuw.ecs.swen225.gp22.domain.Point;

import java.util.Collections;
import java.util.List;

/**
 * This class represents an enemy that moves in a predetermined path.
 * If the player moves into this enemy, it will DIE.
 * If the enemy moves into this player, it will DIE.
 */
public class BasicEnemy extends Enemy {

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
    public BasicEnemy(List<Point> path) {
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
     * Returns the path
     * 
     * @return
     */
    public List<Point> getPath() {
        return Collections.unmodifiableList(path);
    }

    /**
     * Returns the current position of the enemy.
     * 
     * @return Point object.
     */
    public Point getPosition() {
        return this.path.get(currentFrameIndex);
    }

    /**
     * Returns the position of the enemy
     * 
     * @return Point object.
     */
    public Point getGraphicalPosition() {
        return getPosition().translate(1, 1);
    }

    /**
     * Does the position passed into the program collide with.
     */
    public Boolean collidesWith(Point pos) {
        return getPosition().equals(pos);
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