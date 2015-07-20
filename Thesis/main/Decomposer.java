package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Decomposer {

	private List<Class> listClasses;
	private JarFile jarFile;
	private String pathToJar;
	private List<ClassInfo> classes;
	
	/**
	 * Created because we need the String representation of the JarFile's path and not the File object.
	 * @param pathToJar
	 */
	public Decomposer(String pathToJar){
		this.pathToJar = pathToJar;
	}
	
	public void loadClassInfo(){
		GUI.log.append("Starting analysis 2..\n");
		classes = new ArrayList<ClassInfo>();
		
		try {
			jarFile = new JarFile(pathToJar);
			Enumeration<JarEntry> jarElements = jarFile.entries();

			URL[] jars = { new URL("jar:file:" + pathToJar + "!/") };
			URLClassLoader jarContainer = URLClassLoader.newInstance(jars);

			//Iterate the Jar file's elements
			while (jarElements.hasMoreElements()) {

				JarEntry je = (JarEntry) jarElements.nextElement();

				if(je.isDirectory() || !je.getName().endsWith(".class")){
					continue;
				}

				//Gets the name of the file
				String className = je.getName().substring(0,je.getName().length()-6);
				className = className.replace('/', '.');

				//Loads the class with the specified name and adds it to the list
				Class loadedClass = jarContainer.loadClass(className);
				ClassInfo temp = new ClassInfo(loadedClass);
				classes.add(temp);
				//temp.tog();
			}
		} catch (IOException e) {
			System.err.println("Problem with IO.");
		} catch (ClassNotFoundException e1) {
			System.err.println("Class Not Found.");
		}
	}
	
	
	/**
	 * Starts the runtime analysis of the JarFile.
	 */
	public void startAnalysis(){

		GUI.log.append("Starting analysis..\n");
		//pathToJar = component.getPath();

		//List that holds classes in the jar file
		listClasses = new ArrayList<Class>();

		try {
			jarFile = new JarFile(pathToJar);
			Enumeration<JarEntry> jarElements = jarFile.entries();

			URL[] jars = { new URL("jar:file:" + pathToJar + "!/") };
			URLClassLoader jarContainer = URLClassLoader.newInstance(jars);

			//Iterate the Jar file's elements
			while (jarElements.hasMoreElements()) {

				JarEntry je = (JarEntry) jarElements.nextElement();

				if(je.isDirectory() || !je.getName().endsWith(".class")){
					continue;
				}

				//Gets the name of the file
				String className = je.getName().substring(0,je.getName().length()-6);
				className = className.replace('/', '.');

				//Loads the class with the specified name and adds it to the list
				listClasses.add(jarContainer.loadClass(className));
			}
		} catch (IOException e) {
			System.err.println("Problem with IO.");
		} catch (ClassNotFoundException e1) {
			System.err.println("Class Not Found.");
		}

		/*
		//Iterates the list of Classes to find all the fields and methods of this class
		for(Class jio:listClasses){
			Field[] frida = jio.getDeclaredFields();
			Method[] alekos = jio.getDeclaredMethods();

			System.out.println(jio.getName());			
			for(Field fr : frida){
				System.out.println("\t" + fr.getType().getName() + " " + fr.getName());
			}			
			for(Method al : alekos){
				System.out.println("\t" + al.getName() + " " + al.getReturnType().getName());
			}
		}	*/

	}
	
	/**
	 * Searches the package's list of classInfo objects to find the Class with a specified name.
	 * @param className Name of the class to be found
	 * @return Class object
	 */
	public Class getClassByName(String className){
		for(ClassInfo cl:classes){
			if(cl.getClassOrigin().getName().equals(className)){
				return cl.getClass();
			}
		}
		return null;
	}
	/**
	 * String representation of the path
	 * @return String
	 */
	public String getPathToJar(){
		return pathToJar;
	}
}
