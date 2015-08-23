package thesis;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Decomposes the JAR file to its consisting .class files. Then, starts the evaluation process.
 * @author Evangelos Karvounis
 *
 */
public class Decomposer {

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

	/**
	 * Deals with the analysis of a Jar File.
	 */
	public void startJarAnalysis(){
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
				Class<?> loadedClass;
				try{
				loadedClass = jarContainer.loadClass(className);
				ClassInfo temp = new ClassInfo(loadedClass);
				classes.add(temp);
				}catch(NoClassDefFoundError e2){
					System.err.println("NoClassDefFoundError");
				}

			}
		} catch (IOException e) {
			System.err.println("Problem with IO.");
			return;
		} catch (ClassNotFoundException e1) {
			System.err.println("Class Not Found.");
			return;
		}
	}

	/**
	 * Starts the evaluation process
	 */
	public void startEvaluation(){
		Evaluator eval = new Evaluator(classes, jarFile.getName());
		eval.evaluate();
	}

	/**
	 * Searches the package's list of classInfo objects to find the Class with a specified name.
	 * @param className Name of the class to be found
	 * @return Class object
	 */
	public Class<?> getClassByName(String className){
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
