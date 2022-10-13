package nz.ac.vuw.ecs.swen225.gp22.renderer;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Sounds.SoundEffects;

/**
 * main class that tests renderer components
 * @author Adam Goodyear 300575240
 */


public class TestRender {
    public static void main(String[] args) {
        Renderer r = new Renderer(1000, 1000);
        r.setUpGUI();
        SoundEffects s = new SoundEffects();
        s.loopSound("Test");
    }
}
