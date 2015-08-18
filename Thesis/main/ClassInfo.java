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
	/**Weight of every coefficient.*/
	private static final double WEIGHT = 0.1;
	
	private Class<?> classOrigin;
	/**Methods of the class*/
	private Method[] methods;
	/**Fields of the class*/
	private Field[] fields;
	private int staticFields, staticMethods, numberOfMethods, instanceVars, publicInstanceVars, privProtMethods, longParameterMethods, couplingClasses;
	private boolean isDataClass;
	
	private double evaluationResult;
	
	public ClassInfo(Class<?> classOrigin){
		this.classOrigin = classOrigin;
		
		methods = classOrigin.getDeclaredMethods();
		numberOfMethods = methods.length;

		fields = classOrigin.getDeclaredFields();
		instanceVars = fields.length;
		fullClassAnalysis();
	}
	
	public ClassInfo(Class<?> classOrigin, Method[] methods, Field[] fields){
		this.classOrigin = classOrigin;
		this.methods = methods;
		this.fields = fields;
	}
	
	/**
	 * Calls the methods that perform the full class analysis
	 */
	public void fullClassAnalysis(){
		privProtMethods = privProtMethods();
		staticMethods = staticMethods();
		longParameterMethods = longParameterMethods();
		staticFields = staticFields();		
		couplingClasses = couplingCount();
		isDataClass = isDataClass();
		publicInstanceVars = countPublicInsVars();
		
		evaluationResult = calculateEvaluationResult();
	}
	
	/**
	 * This method calculates the evaluation of the specific class. The formula is: Σ(wi * ni), where i is the number of metrics used.
	 * @return
	 */
	public double calculateEvaluationResult(){
		double result = 0;
		
		result = WEIGHT * (privProtMethods + staticMethods + longParameterMethods + staticFields + couplingClasses + publicInstanceVars);
		
		if(isDataClass){
			result += 1;
		}
		return result;
	}
	
	/**
	 * Counts the number of instance variables that are public.
	 * @return
	 */
	public int countPublicInsVars(){
		int tempPublicInsVars = 0;
		//Find the number of fields that are public
		for(int i = 0; i < instanceVars; i++){
			if(Modifier.isPublic(fields[i].getModifiers())){
				tempPublicInsVars++;
			}
		}
		return tempPublicInsVars;
	}
	
	/**
	 * Counts the class's instance variables that their type is objects and not primitives. Strings and objects from java. or javax. packages are not counted.
	 */
	public int couplingCount(){
		int tempCoupl = 0;
		for(int i = 0; i < instanceVars; i++){
			Class<?> temp = fields[i].getType();
			//Checks if the type of the field is primitive or starts with java. or javax.
			if(temp.isPrimitive() || temp.getName().toLowerCase().startsWith("java.") || temp.getName().toLowerCase().startsWith("javax.")){
				continue;
			}
			tempCoupl++;
		}
		return tempCoupl;
	}
	
	/**
	 * Counts the class's static fields.
	 */
	public int staticFields(){
		int tempStaticFields = 0;
		//Find the number of fields that are static
		for(int i = 0; i < instanceVars; i++){
			if(isStatic(fields[i].getModifiers())){
				tempStaticFields++;
			}
		}
		return tempStaticFields;
	}
	
	/**
	 * Counts the class's static methods.
	 */
	public int staticMethods(){
		int tempStaticMethods = 0;
		//Find the number of fields that are static
		for(int i = 0; i < numberOfMethods; i++){
			if(isStatic(methods[i].getModifiers())){
				tempStaticMethods++;
			}
		}
		return tempStaticMethods;
	}
	
	/**
	 * Counts the class's public or protected methods.
	 */
	public int privProtMethods(){
		int tempprivProtMethods = 0;
		//Find the number of methods that are either public or protected
		for(int i = 0; i < numberOfMethods; i++){
			if(isPrivateProtected(methods[i].getModifiers())){
				tempprivProtMethods++;
			}
		}
		return tempprivProtMethods;
	}

	/**
	 * Counts the number of methods that their parameter list exceeds the number of the static variable NUMBER_OF_PARAMETERS
	 */
	public int longParameterMethods(){
		int tempLongParameterMethods = 0;
		for(Method meth : methods){
			if(parametersMethod(meth) >= NUMBER_OF_PARAMETERS){
				tempLongParameterMethods++;
			}
		}
		return tempLongParameterMethods;
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
	public static boolean isPrivateProtected(int mod){
		return (Modifier.isPrivate(mod) || Modifier.isProtected(mod));
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

	public Class<?> getClassOrigin() {
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

	public int getPrivProtMethods() {
		return privProtMethods;
	}
	
	public int getStaticMethods() {
		return staticMethods;
	}
	
	public int getLongParameterMethods() {
		return longParameterMethods;
	}

	public int getCouplingClasses() {
		return couplingClasses;
	}

	public int getPublicInstanceVars() {
		return publicInstanceVars;
	}

	public double getEvaluationResult() {
		return evaluationResult;
	}
}