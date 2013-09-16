package ca.kanoa.rodstwo.helpers;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.objects.CustomRod;
import ca.kanoa.rodstwo.objects.Rod;

public class RodLoader {

	public static Set<Rod> getRods(File rodDirectory) throws Exception {
		rodDirectory.mkdir();
		URLClassLoader classLoader;
		Set<Rod> rods = new HashSet<Rod>();

		for (File f : rodDirectory.listFiles()) {
			if (f.isDirectory() || !f.getName().endsWith(".jar"))
				continue;
			
			JarFile jar = new JarFile(f);
			Enumeration<JarEntry> e = jar.entries();
			classLoader = URLClassLoader.newInstance(new URL[]{new URL("jar:file:" + f.getAbsolutePath() + "!/")}, RodsTwo.plugin.getClass().getClassLoader());

			while (e.hasMoreElements()) {
				JarEntry j = (JarEntry) e.nextElement();
				if(j.isDirectory() || !j.getName().endsWith(".class")){
					continue;
				}
				Class<?> c = classLoader.loadClass(j.getName().substring(0, j.getName().length() - 6));
				CustomRod a = c.getAnnotation(CustomRod.class);
				if (a == null)
					continue;
				if (a.minimumVersion() > RodsTwo.getVersion())
					continue;
				rods.add((Rod) c.getConstructor().newInstance());
			}
			
			jar.close();
			
		}
		return rods;
	}

}
