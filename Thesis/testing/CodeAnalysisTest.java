package testing;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import main.*;


public class CodeAnalysisTest {

	CodeAnalysis frid;
	
	@Before
	public void test() {
		File something = null;
		frid = new CodeAnalysis(something);
	}
	
	@Test
	public void getComponentTest(){
		assertNull(frid.getComponent());
	}
}
