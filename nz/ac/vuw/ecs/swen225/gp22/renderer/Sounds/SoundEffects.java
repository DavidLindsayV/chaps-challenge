package nz.ac.vuw.ecs.swen225.gp22.renderer.Sounds;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class SoundEffects {
    public static void playSound(String filename) {
        try {
            File soundPath = new File("nz/ac/vuw/ecs/swen225/gp22/renderer/Sounds/" + filename + ".wav");
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Can't find file: " + soundPath);
            }
        } catch (Exception e) {
            System.out.println("Could not play sound: " + e);
        }
    }

    public static void loopSound(String filename) {
        try {
            File soundPath = new File("nz/ac/vuw/ecs/swen225/gp22/renderer/Sounds/" + filename + ".wav");
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("Can't find file: " + soundPath);
            }
        } catch (Exception e) {
            System.out.println("Could not play sound: " + e);
        }
    }
}
