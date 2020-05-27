package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingLexerException;
import hr.fer.zemris.java.custom.scripting.nodes.WriterVisitor;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class TreeWriter {

	public static void main(String[] args) throws IOException {
		
		String filePath = args[0];
		
		String docBody = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
		
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
			WriterVisitor visitor = new WriterVisitor();
			parser.getDocmentNode().accept(visitor);
			
		}catch(SmartScriptingLexerException e) {
			System.out.println("Unable to parse document.");
			System.exit(-1);
		}catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class.");
			System.exit(-1);
		}
	}
}
