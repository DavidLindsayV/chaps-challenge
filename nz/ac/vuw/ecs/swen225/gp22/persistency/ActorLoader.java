package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ActorLoader {

    /**
     * Get the class from a specified jar file
     * 
     * @param jarFile   the jar file with the class in it
     * @param className the name of the class to be found
     * @return the class object
     */
    public static Class<?> getClass(File jarFile, String className) {
        try {
            URLClassLoader loader = new URLClassLoader(new URL[] { jarFile.toURI().toURL() });
            Class<?> loadedClass = Class.forName(className, true, loader);
            return loadedClass;
        } catch (MalformedURLException | ClassNotFoundException | SecurityException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
