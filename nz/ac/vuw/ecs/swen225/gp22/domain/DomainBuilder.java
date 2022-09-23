package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.Arrays;

public class DomainBuilder {
    private static final int MAX_WIDTH  = 1000;
    private static final int MAX_HEIGHT = 1000;

    private Tile[][] domainContent;
    private Integer  domainHeight;
    private Integer  domainWidth;

    public DomainBuilder() {
        this.reset();
    }

    /**
     * Resets the state of the builder
     * All tiles are free tiles by default.
     */
    public void reset() {
        domainContent = new Tile[MAX_WIDTH][MAX_HEIGHT];    
        domainHeight  = -1;
        domainWidth   = -1;
        for (Tile[] domainContentRow : domainContent) {
            Arrays.fill(domainContentRow, FreeTile.empty());
        }
    }

    /**
     * Creates an empty tile at given row and column.
     * @param row Row of the tile.
     * @param col Column of the tile.
     * @return Domain builder object.
     */
    public DomainBuilder empty(int row, int col) {
        domainContent[row][col] = FreeTile.empty();
        domainHeight = Math.max(domainHeight, row + 1);
        domainWidth  = Math.max(domainWidth, col + 1);
        return this;
    }

    /**
     * Creates a wall tile at the given location.
     * !! NOTE, this really shouldn't be used often. 
     * As all default tiles are rows.
     * 
     * @param row Row of the tile.
     * @param col Column of the tile.
     * @return Domain builder object.
     */
    public DomainBuilder wall(int row, int col) {
        domainContent[row][col] = WallTile.of();
        domainHeight = Math.max(domainHeight, row + 1);
        domainWidth  = Math.max(domainWidth, col + 1);
        return this;
    }

    /**
     * Returns the constructed domain object.
     * @return
     */
    public Domain make() {
        // Copies over the selected region.
        Tile[][] selectedDomainContent = new Tile[domainWidth][domainHeight];
        for (int y=0; y<domainHeight; ++y) {
            for (int x=0; x<domainWidth; ++x) {
                selectedDomainContent[y][x] = domainContent[y][x];
            }
        }

        return new Domain(selectedDomainContent);
    }
}
