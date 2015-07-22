package main;

import java.util.List;

public class Evaluator {

	private List<ClassInfo>	classes;
	
	public Evaluator(List<ClassInfo> classesList){
		classes = classesList;
	}
	
	public void evaluate(){
		
		EvaluationGUI jio = new EvaluationGUI();
		jio.fillTextArea(classes);
	}
}
