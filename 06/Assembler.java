package com.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Assembler {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("C:\\Users\\Thingcor\\Desktop\\Pong.asm");
		FileInputStream inputStream = new FileInputStream(file);
		Parser parser = new Parser(file, inputStream);
		do {
			parser.advance();
			System.out.print(parser.currentCommand+"\t");
			System.out.print("command type:"+parser.commandType()+"\t");
			System.out.print("symbol:"+parser.sysmbol()+"\t");
			System.out.print("dest:"+parser.dest()+"\t");
			System.out.print("comp:"+parser.comp()+"\t");
			System.out.println("jump:"+parser.jump()+"\t");
		}while(parser.hasMoreCommands());
	}
}
