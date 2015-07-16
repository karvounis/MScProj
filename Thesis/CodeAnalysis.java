import java.io.File;

public class CodeAnalysis {

	private File component;
	
	public CodeAnalysis(File component){
		this.component = component;
		System.err.println(component.getName());
		System.err.println("Till here");
		System.err.println(component.getAbsolutePath());
	}
	
	public void startAnalysis(){
		System.err.println("Start");
	}
	
	public File getComponent(){
		return component;
	}
}
