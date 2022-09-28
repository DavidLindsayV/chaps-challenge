package nz.ac.vuw.ecs.swen225.gp22.persistency;

public interface TriConsumer<A, B, C> {
    void accept(A a, B b, C c);
}
