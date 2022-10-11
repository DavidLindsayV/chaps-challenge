package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ActorLoader {
    public static Class getClass(File jarFile) {
        try {
            URLClassLoader loader = new URLClassLoader(new URL[] { jarFile.toURI().toURL() },
                    ActorLoader.class.getClass().getClassLoader());
            Class loadedClass = Class.forName("Enemy", true, loader);
            return loadedClass;
        } catch (MalformedURLException | ClassNotFoundException | SecurityException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
