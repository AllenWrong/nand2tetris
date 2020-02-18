package compiler;

import java.io.File;
import java.io.FileNotFoundException;

public class Demo {
	public static void main(String[] args) {
		// change the file name
		File file = new File("C:\\Users\\Thingcor\\Desktop\\Main.jack");
		File outputFile = new File("C:\\Users\\Thingcor\\Desktop\\MainG.xml");
		if(outputFile.exists()) {
			outputFile.delete();
		}
		CompilationEngine compilationEngine = null;
		try {
			compilationEngine = new CompilationEngine(file, outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		compilationEngine.compileClass();
	}
}
