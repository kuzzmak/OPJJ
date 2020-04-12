package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingLexerException;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class SmartScriptTester {

	public static void main(String[] args) throws IOException {
		
		if(args.length != 1)
			throw new IllegalArgumentException("Broj argumenata mora biti 1 - staza do datoteke koja se parsira.");
		
		String filePath = args[0];
		
		String docBody = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
		
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		}catch(SmartScriptingLexerException e) {
			System.out.println("Unable to parse document.");
			System.exit(-1);
		}catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class.");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocmentNode();
		String originalDocumentBody = document.toString();
		System.out.println(originalDocumentBody);
	}
}
