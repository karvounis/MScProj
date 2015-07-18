package main;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CodeAnalysis {

	private File component;

	public CodeAnalysis(File component){
		this.component = component;
		//System.err.println(component.getName());
		//System.err.println("Till here");
		//System.err.println(component.getAbsolutePath());
	}

	public void startAnalysis(){
		//System.err.println("Start");
		GUI.log.append("Starting analysis..\n");

		Class compClass = component.getClass();
		Class[] classy = compClass.getDeclaredClasses();

		GUI.log.append(compClass.toString());
		Method[] methods;
		methods = compClass.getMethods();

		for(Method in:methods){
			//GUI.log.append(in.getName() + "\n");
			//GUI.log.append("--------------------------------\n");
			//GUI.log.append(in.getReturnType() + "\n");
		}
	}

	public void setComponent(File component){
		this.component = component;
	}

	public File getComponent(){
		return component;
	}
}
