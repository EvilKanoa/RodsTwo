package ca.kanoa.rodstwo.helpers;

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

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.objects.CustomRod;
import ca.kanoa.rodstwo.objects.Rod;

public class RodLoader {

	public static Set<Rod> getRods(File rodDirectory) {
		rodDirectory.mkdir();
		URLClassLoader classLoader;
		Set<Rod> rods = new HashSet<Rod>();
		JarFile jar = null;
		Class<?> loadedClass = null;
		JarEntry entry = null;

		for (File f : rodDirectory.listFiles()) {

			try {
				if (f.isDirectory() || !f.getName().endsWith(".jar"))
					continue;

				jar = new JarFile(f);
				Enumeration<JarEntry> e = jar.entries();
				classLoader = URLClassLoader.newInstance(new URL[]{new URL("jar:file:" + f.getAbsolutePath() + "!/")}, RodsTwo.plugin.getClass().getClassLoader());

				while (e.hasMoreElements()) {
					entry = (JarEntry) e.nextElement();
					if(entry.isDirectory() || !entry.getName().endsWith(".class"))
						continue;
					
					try {
						loadedClass = classLoader.loadClass(entry.getName().substring(0, entry.getName().length() - 6));
						CustomRod a = loadedClass.getAnnotation(CustomRod.class);
						
						if (a == null)
							continue;
						if (a.minimumVersion() > RodsTwo.getVersion())
							continue;

						rods.add((Rod) loadedClass.getConstructor().newInstance());
						
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
						print("Error while loading class: " + entry.getName() + " (Is it up to date?)");
					} catch (InstantiationException | IllegalAccessException 
							| IllegalArgumentException| InvocationTargetException 
							| NoSuchMethodException | SecurityException e1) {
						e1.printStackTrace();
						print("Error while initializing object: " + loadedClass.getName() + " (Is it compiled properly?)");
					} finally {
						loadedClass = null;
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
				print("Error while loading jar file: " + f.getName() + " (Is it corrupt?)");
			}  finally {
				try {
					jar.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return rods;
	}

	private static void print(String str) {
		System.out.println(str);
	}

}
