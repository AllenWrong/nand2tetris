package compiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Demo {
	private File inputFile;
	private File outputFile;
	private boolean isDirectory;
	
	public Demo(File inputFile) {
		this.inputFile = inputFile;
		this.isDirectory = inputFile.isDirectory();
	}
	
	public File getInputFile() {
		return inputFile;
	}
	public File getOutputFile() {
		return outputFile;
	}
	public boolean isDirectory() {
		return isDirectory;
	}
	
	public void compile() {
		if(this.isDirectory) {
			compileDirectory();
		}else {
			compileFile();
		}
	}
	
	/**
	 * Compile a directory.
	 */
	private void compileDirectory() {
		File[] files = this.inputFile.listFiles();
		ArrayList<File> fileList = new ArrayList<>();
		for(int i = 0;i<files.length;i++) {
			String fileName = files[i].getName();
			String[] splitName = fileName.split("\\.");
			if(splitName[1].equals("jack")) {
				fileList.add(files[i]);
			}
		}
		
		// Compile.
		for(int i = 0;i<fileList.size();i++) {
			try {
				CompilationEngine cpe = new CompilationEngine(fileList.get(i));
				cpe.compileClass();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Compile single file.
	 */
	private void compileFile() {
		try {
			CompilationEngine cpe = new CompilationEngine(inputFile);
			cpe.compileClass();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {		
		// change the file name
		File file = new File("D:\\leaning_source\\nand2tetris\\projects\\11\\ComplexArrays");
		Demo demo = new Demo(file);
		demo.compile();
	}
}
