package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Point represents a 2D point.
 * @author Brandon Ru 300562436
 *
 */
public record Point(int row, int col) {
	public Point translate(int dr, int dc) {
		return new Point(row + dr, col + dc);
	}

	@Override
	public boolean equals(Object object) { 	
		if (object == null) { return false; }
		if (!(object instanceof Point)) { return false; } 
        else {
             Point other = (Point)object;
             return row == other.row() && col == other.col();
        }
     }
 }


