package ca.kanoa.RodsTwo.Helpers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import ca.kanoa.RodsTwo.RodsTwo;
import ca.kanoa.RodsTwo.Objects.CustomRod;
import ca.kanoa.RodsTwo.Objects.Rod;

public class RodLoader {

	public static Set<Rod> getRods(File rodDirectory) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
		rodDirectory.mkdir();
		URLClassLoader classLoader;
		Set<Rod> rods = new HashSet<Rod>();

		for (File f : rodDirectory.listFiles()) {
			if (f.isDirectory() || !f.getName().endsWith(".jar"))
				continue;
			
			JarFile jar = new JarFile(f);
			Enumeration<JarEntry> e = jar.entries();
			classLoader = URLClassLoader.newInstance(new URL[]{new URL("jar:file:" + f.getAbsolutePath() + "!/")});

			while (e.hasMoreElements()) {
				JarEntry j = (JarEntry) e.nextElement();
				if(j.isDirectory() || !j.getName().endsWith(".class")){
					continue;
				}
				Class<?> c = classLoader.loadClass(j.getName().substring(0, j.getName().length() - 6));
				CustomRod a = c.getAnnotation(CustomRod.class);
				if (a == null)
					continue;
				if (a.minimumVersion() < RodsTwo.getVersion())
					continue;
				rods.add((Rod) c.getConstructor().newInstance());
			}
			
			jar.close();
			
		}
		return rods;
	}

}
