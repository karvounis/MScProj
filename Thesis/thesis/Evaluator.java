package thesis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Performs the evaluation process.
 * @author Evangelos Karvounis
 *
 */
public class Evaluator {

	/**Format of the lines*/
	private static final String FORMAT_INTEGERS = "%-70s | %-10.2f | %-7d | %-10d | %-22d | %-11d | %-11d | %-11d | %-20d | %-17d | %-10s%n";
	private static final String FORMAT_DOUBLES = "%-70s | %-10.2f | %-7.2f | %-10.2f | %-22.2f | %-11.2f | %-11.2f | %-11.2f | %-20.2f | %-17.2f | %-10.2f%n";
	private static final String FORMAT_STRINGS = "%-70s | %-10s | %-7s | %-10s | %-22s | %-10s | %-10s | %-10s | %-17s | %-10s | %-10s%n";
	
	private static final String BORDER_LINE = "---------------------------------------------------------------------"
			+ "----------------------------------------------------------------------------------------"
			+ "------------------------------------------------------------------------\n";

	private List<ClassInfo>	classes;
	private String jarPath;
	//Counters for the metrics.
	private int totalstaticFields, totalstaticMethods, totalnumberOfMethods, totalinstanceVars, totalpublicInstanceVars, 
	totalprivProtMethods, totallongParameterMethods, totalcouplingClasses, totalisDataClass;
	//Total number of classes
	private double classesCounter;
	private double totalevaluationResult;

	public Evaluator(List<ClassInfo> classesList, String jarPath){
		classes = classesList;
		this.jarPath = jarPath;
	}

	/**
	 * Creates an EvaluationGUI object and fills its text area with the results.
	 */
	public void evaluate(){

		EvaluationGUI jio = new EvaluationGUI();
		fillText(jio);
	}

	/**
	 * Uses 2 StringBuilder in order to display the results. The first one is passed to an EvaluationGUI in order to be displayed inside the text area. 
	 * The second one is used to create aan .csv file in order to store the results. The .csv file has a filename of the current time's epoch value and it is unique for every file.
	 * @param eval
	 */
	public void fillText(EvaluationGUI eval){
		StringBuilder str = new StringBuilder();
		StringBuilder strExcel = new StringBuilder();

		long epochValue = System.currentTimeMillis()/1000;

		str.append("JarFile path: " + jarPath + "				Timestamp: " + epochValue + "\n");
		str.append(BORDER_LINE);
		str.append(String.format(FORMAT_STRINGS, "Name of the class", "Evaluation", "Methods", "Variables",	"Private/Protected Meth", 
				"Static Meth", "Static Vars", "Public Vars", "Long Parameter List",  "Referenced Classes", "Data Class"));

		strExcel.append(jarPath + "\n");
		strExcel.append("Name of the class,Evaluation,Methods,Instance Variabless,Private/Protected Methods,Static Methods,Static Variables,"
				+ "Public Variables,Long Parameter List,Referenced Classes,Data Class\n");

		for(ClassInfo cl : classes){
			//Count the sum of the results.
			classesCounter++;
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

			str.append(BORDER_LINE);
			str.append(String.format(FORMAT_INTEGERS, cl.getClassOrigin().getName(),
					cl.getEvaluationResult(), cl.getNumberOfMethods(), cl.getInstanceVars(), cl.getPrivProtMethods(), cl.getStaticMethods(), cl.getStaticFields(),
					cl.getPublicInstanceVars(), cl.getLongParameterMethods(), cl.getCouplingClasses(), cl.isDataClass()));

			strExcel.append(cl.getClassOrigin().getName() + "," + cl.getEvaluationResult() + "," + cl.getNumberOfMethods() + "," + cl.getInstanceVars()
					+ "," + cl.getPrivProtMethods() + "," + cl.getStaticMethods() + "," + cl.getStaticFields() + "," + cl.getPublicInstanceVars()
					+ "," + cl.getLongParameterMethods() + "," + cl.getCouplingClasses() + "," + cl.isDataClass() + "\n");
		}

		str.append(BORDER_LINE);
		str.append(BORDER_LINE);

		str.append(String.format(FORMAT_INTEGERS, "Total:", totalevaluationResult, totalnumberOfMethods, totalinstanceVars, totalprivProtMethods, 
				totalstaticMethods, totalstaticFields, totalpublicInstanceVars, totallongParameterMethods, totalcouplingClasses, totalisDataClass));

		str.append(String.format(FORMAT_DOUBLES, "Total Avg:", totalevaluationResult/classesCounter, totalnumberOfMethods/classesCounter, totalinstanceVars/classesCounter, 
				totalprivProtMethods/classesCounter, totalstaticMethods/classesCounter, totalstaticFields/classesCounter, 
				totalpublicInstanceVars/classesCounter, totallongParameterMethods/classesCounter, totalcouplingClasses/classesCounter, totalisDataClass/classesCounter));
		
		strExcel.append("Total," + totalevaluationResult + "," + totalnumberOfMethods + "," + totalinstanceVars + "," + totalprivProtMethods + ","
				+ totalstaticMethods + "," + totalstaticFields + "," + totalpublicInstanceVars + "," + totallongParameterMethods
				+ "," + totalcouplingClasses + "," + totalisDataClass + "\n");
		
		strExcel.append("Total," + totalevaluationResult/classesCounter + ","+ totalnumberOfMethods/classesCounter + "," + totalinstanceVars/classesCounter
				+ "," + totalprivProtMethods/classesCounter + "," + totalstaticMethods/classesCounter + "," + totalstaticFields/classesCounter + 
				"," + totalpublicInstanceVars/classesCounter + "," + totallongParameterMethods/classesCounter + "," + totalcouplingClasses/classesCounter 
				+ "," + totalisDataClass/classesCounter + "\n");

		//Display the results to the text area.
		eval.fillTextArea(str.toString());

		try {
			FileWriter writer = new FileWriter(epochValue + ".csv");

			//Store the results to an .csv file
			writer.append(strExcel.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}		
	}
}
