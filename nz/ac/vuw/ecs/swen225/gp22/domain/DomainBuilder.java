package nz.ac.vuw.ecs.swen225.gp22.domain;

public class DomainBuilder {
    private int domainHeight = -1;          /** Must be set */
    private int domainWidth  = -1;          /** Must be set */
    
    public DomainBuilder() {
        
    }
    
    /**
     * Sets the height of the domain in construction.
     * @param height Height of the domain.
     * @return Builder object.
     */
    public DomainBuilder height(int height) {
        if (height <= 0) {  throw new IllegalArgumentException("Domain height must be positive."); }        
        this.domainHeight = height;
        return this;
    }

    /**
     * Sets the width of the domain in construction.
     * @param width Width of the domain.
     * @return Builder object.
     */
    public DomainBuilder width(int width) {
        if (width <= 0) { throw new IllegalArgumentException("Domain width must be positive."); }
        this.domainWidth = width;
        return this;
    }

    
}
