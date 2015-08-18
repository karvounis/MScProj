package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Evaluator {

	private List<ClassInfo>	classes;
	private String jarPath;
	private int totalstaticFields, totalstaticMethods, totalnumberOfMethods, totalinstanceVars, totalpublicInstanceVars, 
	totalprivProtMethods, totallongParameterMethods, totalcouplingClasses, totalisDataClass;
	
	private double totalevaluationResult;
	
	public Evaluator(List<ClassInfo> classesList, String jarPath){
		classes = classesList;
		this.jarPath = jarPath;
	}
	
	public void evaluate(){
		
		EvaluationGUI jio = new EvaluationGUI();
		//jio.fillTextArea(classes);
		fillText(jio);
	}
	
	public void fillText(EvaluationGUI eval){
		StringBuilder str = new StringBuilder();
		StringBuilder strExcel = new StringBuilder();
		
		long epochValue = System.currentTimeMillis()/1000;
				
		str.append("JarFile path: " + jarPath + "		" + epochValue + "\n");
		str.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		str.append(String.format("%-20s | %-15s | %-10s | %-10s | %-15s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s%n", "Name of the class", "Evaluation", "Methods", "InstVars", "PrivProtMeth"
				, "StMeth", "StVars","PubVars", "LongList",  "RefCl", "DataClass"));
		
		strExcel.append("Name of the class,Evaluation,Methods,InstVars,PrivProtMeth,StMeth,StVars,PubVars,LongList,RefCl,DataClass\n");
		
		for(ClassInfo cl : classes){
			totalnumberOfMethods += cl.getNumberOfMethods();
			totalinstanceVars += cl.getInstanceVars();
			
			totalstaticFields += cl.getStaticFields();
			totalstaticMethods += cl.getStaticMethods();
			
			totalpublicInstanceVars += cl.getPublicInstanceVars();
			totalprivProtMethods += cl.getPrivProtMethods();
			totallongParameterMethods += cl.getLongParameterMethods();
			totalcouplingClasses += cl.getCouplingClasses();
			
			totalevaluationResult += cl.getEvaluationResult();
			
			if(cl.isDataClass()){
				totalisDataClass ++;
			}
			
			str.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			str.append(String.format("%-20s | %-15.2f | %-10d | %-10d | %-15d | %-10d | %-10d | %-10d | %-10d | %-10d | %-10s%n", cl.getClassOrigin().getName(),
					cl.getEvaluationResult(), cl.getNumberOfMethods(), cl.getInstanceVars(), cl.getPrivProtMethods(), cl.getStaticMethods(), cl.getStaticFields(),
					cl.getPublicInstanceVars(), cl.getLongParameterMethods(), cl.getCouplingClasses(), cl.isDataClass()));
			strExcel.append(cl.getClassOrigin().getName()+","+cl.getEvaluationResult()+","+cl.getNumberOfMethods()+","+cl.getInstanceVars()+","+cl.getPrivProtMethods()+","+cl.getStaticMethods()+","+
					cl.getStaticFields()+","+cl.getPublicInstanceVars()+","+cl.getLongParameterMethods()+","+cl.getCouplingClasses()+","+cl.isDataClass() +"\n");
		}
		
		str.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		str.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		str.append(String.format("%-20s | %-15.2f | %-10d | %-10d | %-15d | %-10d | %-10d | %-10d | %-10d | %-10d | %-10d%n", "Total:",
				totalevaluationResult, totalnumberOfMethods, totalinstanceVars, totalprivProtMethods, totalstaticMethods, totalstaticFields,
				totalpublicInstanceVars, totallongParameterMethods, totalcouplingClasses, totalisDataClass));
		
		strExcel.append("Total,"+totalevaluationResult+","+totalnumberOfMethods+","+totalinstanceVars+","+totalprivProtMethods+","+totalstaticMethods+","+totalstaticFields+","+totalpublicInstanceVars+","+totallongParameterMethods+","+totalcouplingClasses+","+totalisDataClass+"\n");
		
		eval.fillTextArea(str.toString());
		
		try {
			FileWriter writer = new FileWriter(epochValue + ".csv");
			
			writer.append(strExcel.toString());
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
