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
            Arrays.fill(domainContentRow, new WallTile());
        }
    }

    /**
     * 
     * Fill in with corresponding tile creation methods for the individual tiles.
     * Automatically detect the width / height, through max of given number.
     * ...
     * 
     */

    
    /**
     * Returns the domain.
     * @return
     */
    public Domain make() {
        // Copies over the selected region.
        Tile[][] selectedDomainContent = new Tile[domainWidth][domainWidth];
        for (int y=0; y<domainHeight; ++y) {
            for (int x=0; x<domainWidth; ++x) {
                selectedDomainContent[y][x] = domainContent[y][x];
            }
        }

        return new Domain(selectedDomainContent);
    }
}
