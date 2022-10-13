package nz.ac.vuw.ecs.swen225.gp22.persistency;

/**
 * A consumer which takes in 3 parameters and does something
 * to them.
 * Student ID: 3005 30113
 * 
 * @author GeorgiaBarrand
 *
 * @param <A> first parameter
 * @param <B> second parameter
 * @param <C> third parameter
 */
public interface TriConsumer<A, B, C> {
    void accept(A a, B b, C c);
}
