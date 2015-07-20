package main;

import java.lang.reflect.*;

/**
 * Contains information about a specific class like its fields and methods.
 * @author Evangelos Karvounis
 *
 */
public class ClassInfo {

	/**If the method has more parameters than the value of this variable, then it is considered a long parameter method*/
	private static final int NUMBER_OF_PARAMETERS = 4;
	private Class classOrigin;
	/**Methods of the class*/
	private Method[] methods;
	/**Fields of the class*/
	private Field[] fields;
	private int staticFields, staticMethods, numberOfMethods, instanceVars, pubProtMethods, longParameterMethod;
	
	public ClassInfo(Class classOrigin){
		this.classOrigin = classOrigin;
		
		methods = classOrigin.getDeclaredMethods();
		numberOfMethods = methods.length;
		pubProtMethods();
		staticMethods();
		longParameterMethods();
		
		fields = classOrigin.getDeclaredFields();
		instanceVars = fields.length;
		staticFields();
	}
	
	public ClassInfo(Class classOrigin, Method[] methods, Field[] fields){
		this.classOrigin = classOrigin;
		this.methods = methods;
		this.fields = fields;
	}
	
	/**
	 * Counts the class's static fields.
	 */
	public void staticFields(){
		//Find the number of fields that are static
		for(int i = 0; i < instanceVars; i++){
			if(isStatic(fields[i].getModifiers())){
				staticFields++;
			}
		}
	}
	
	/**
	 * Counts the class's static methods.
	 */
	public void staticMethods(){
		//Find the number of fields that are static
		for(int i = 0; i < numberOfMethods; i++){
			if(isStatic(methods[i].getModifiers())){
				staticMethods++;
			}
		}
	}
	
	/**
	 * Counts the class's public or protected methods.
	 */
	public void pubProtMethods(){
		//Find the number of methods that are either public or protected
		for(int i = 0; i < numberOfMethods; i++){
			if(isPublicProtected(methods[i].getModifiers())){
				pubProtMethods++;
			}
		}
	}

	/**
	 * Counts the number of methods that their parameter list exceeds the number of the static variable NUMBER_OF_PARAMETERS
	 */
	public void longParameterMethods(){
		for(Method meth : methods){
			if(parametersMethod(meth) >= NUMBER_OF_PARAMETERS){
				longParameterMethod++;
			}
		}
	}
	
	/**
	 * Checks if a class is a data class. Data class is the class that has getters and setter methods for its fields and nothing else.
	 * @return boolean true if it is a data class
	 */
	public boolean isDataClass(){
		for(Method meth : methods){
			String methodName = meth.getName();
			if(!methodName.equalsIgnoreCase("get") && !methodName.equalsIgnoreCase("set")){
				return false;
			}			
		}
		return true;
	}
	
	/**
	 * Checks if the integer input is a public or protected modifier
	 * @param mod
	 * @return
	 */
	public static boolean isPublicProtected(int mod){
		return (Modifier.isPublic(mod) || Modifier.isProtected(mod));
	}
	
	/**
	 * Checks if the integer input is a static modifier
	 * @param mod
	 * @return
	 */
	public static boolean isStatic(int mod){
		return (Modifier.isStatic(mod));
	}

	
	/**
	 * Number of parameters in a method.
	 * @param method
	 * @return
	 */
	public int parametersMethod(Method method){
		return method.getParameterCount();
	}
	
	public void tog(){
		System.err.println("Hi there");
	}

	public Class getClassOrigin() {
		return classOrigin;
	}

	public Method[] getMethods() {
		return methods;
	}

	public Field[] getFields() {
		return fields;
	}

	public int getStaticFields() {
		return staticFields;
	}

	public int getNumberOfMethods() {
		return numberOfMethods;
	}

	public int getInstanceVars() {
		return instanceVars;
	}

	public int getPubProtMethods() {
		return pubProtMethods;
	}
}