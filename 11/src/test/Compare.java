package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Compare {
	private Scanner scanner1;
	private Scanner scanner2;
	private int lineNumber;

	public Compare(File inputFile1, File inputFile2) {
		try {
			scanner1 = new Scanner(new FileInputStream(inputFile1));
			scanner2 = new Scanner(new FileInputStream(inputFile2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.lineNumber = 0;
	}
	
	private static boolean isEquals(String[] string1, String[] string2) {
		boolean equals = true;
		for(int i = 0;i<string1.length;i++) {
			if(!string1[i].equals(string2[i])) {
				equals = false;
			}
		}
		return equals;
	}
	
	private static boolean isPrefixStringEquals(String string1, String string2) {
		char[] ch1 = string1.toCharArray();
		char[] ch2 = string2.toCharArray();
		
		boolean equals = true;
		for(int i = 0; i<ch1.length;i++) {
			if(Character.isDigit(ch1[i])) {
				break;
			}else {
				String temp1 = ch1[i]+"";
				String temp2 = ch2[i]+"";
				if(!temp1.equals(temp2)) {
					equals = false;
				}
			}
		}
		return equals;
	}
	
	public void compare() {
		boolean equal = true;
		while(scanner1.hasNext()) {
			String line1 = scanner1.nextLine();
			String line2 = scanner2.nextLine();
			lineNumber++;
			
			String[] tokens1 = line1.split(" ");
			String[] tokens2 = line2.split(" ");
		
			if(tokens1[0].equals("if-goto")||
			   tokens1[0].equals("goto")||
			   tokens1[0].equals("label")) {
				equal = isPrefixStringEquals(tokens1[1], tokens2[1]);
			}else {
				equal = isEquals(tokens1, tokens2);
			}
			
			if(equal == false) {
				System.out.println("info error: In line "+lineNumber);
			}
		}
		
		System.out.println("Info true: end scann.");
	}
	
	public void close() {
		scanner1.close();
		scanner2.close();
	}
	
	public static void main(String[] args) throws Exception {
		File inputFile1 = new File("C:\\Users\\Thingcor\\Desktop\\Main.vm");
		File inputFile2 = new File("D:\\leaning_source\\nand2tetris\\projects\\11\\ComplexArrays\\Main.vm");
		Compare compare = new Compare(inputFile1, inputFile2);
		compare.compare();
	}
}
