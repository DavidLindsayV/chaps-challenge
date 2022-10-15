package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * Point represents a 2D point.
 * @author Brandon Ru 300562436
 *
 */
public class Point {
	private final int row;
	private final int col;

	public Point(int r, int c) {
		this.row = r;
		this.col = c;
	}

	public Point translate(int dr, int dc) {
		return new Point(row + dr, col + dc);
	}

	public int row() {return row;}
	public int col() {return col;}

	@Override
	public boolean equals(Object object) {
		if (object == null) { return false; }
		if (!(object instanceof Point)) { return false; }
        else {
             Point other = (Point)object;
             return row == other.row() && col == other.col();
        }
     }

	@Override
	public int hashCode() {
		int prime = 31;
		int coprime = 17;
		prime = (prime + row) * coprime;
		prime = (prime + col) * coprime;
		return prime;
	}
 }





