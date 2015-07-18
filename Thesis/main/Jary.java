package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.*;

public class Jary {

	JarFile john;

	public Jary(JarFile john){
		this.john = john;
	}

	public static void main(String[] args) {

		//Jary alekos = new Jary(null);
		String pathToJar = "C:/Users/Vag/Documents/Fitness.jar";
		
		/*
		File smth = new File(pathToJar);
		String absPath = smth.getAbsolutePath();
		String path = smth.getPath();
		*/
		
		JarFile jarFile;
		//List that holds classes in the jar file
		List<Class> lista = new ArrayList<Class>();

		try {
			jarFile = new JarFile(pathToJar);
			Enumeration<JarEntry> e = jarFile.entries();

			URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
			URLClassLoader cl = URLClassLoader.newInstance(urls);

			//Iterate the Jar file's elements
			while (e.hasMoreElements()) {
				JarEntry je = (JarEntry) e.nextElement();
				if(je.isDirectory() || !je.getName().endsWith(".class")){
					continue;
				}
				//Gets the name of the file
				String className = je.getName().substring(0,je.getName().length()-6);
				className = className.replace('/', '.');
				
				//Loads the class with the specified name and adds it to the list
				Class c = cl.loadClass(className);
				lista.add(c);

			}
		} catch (IOException e) {
			System.err.println("Problem with IO.");
		} catch (ClassNotFoundException e1) {
			System.err.println("Class Not Found.");
		}
		
		//Iterates the list of Classes to find all the fields and methods of this class
		for(Class jio:lista){
			Field[] frida = jio.getDeclaredFields();
			Method[] alekos = jio.getDeclaredMethods();
			
			System.out.println(jio.getName());			
			for(Field fr : frida){
				System.out.println("\t" + fr.getType().getName() + " " + fr.getName());
			}			
			for(Method al : alekos){
				System.out.println("\t" + al.getName() + " " + al.getReturnType().getName());
			}
		}
	}


}
