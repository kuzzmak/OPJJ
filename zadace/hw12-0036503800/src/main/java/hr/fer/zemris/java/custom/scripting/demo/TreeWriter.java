package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingLexerException;
import hr.fer.zemris.java.custom.scripting.nodes.WriterVisitor;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class TreeWriter {

	public static void main(String[] args) throws IOException {
		
		String filePath = "C:\\Users\\tonkec\\Documents\\OPJJ\\zadace\\hw12-0036503800\\osnovni.smscr.txt";
		
		try {
			String documentBody = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
			Map<String,String> parameters = new HashMap<String, String>(); 
			Map<String,String> persistentParameters = new HashMap<String, String>(); 
			List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>(); // put some parameter into parameters map 
			parameters.put("broj", "4"); // create engine and execute it 
			
			new SmartScriptEngine( 
					new SmartScriptParser(documentBody).getDocmentNode(), 
					new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

			
		}catch(SmartScriptingLexerException e) {
			System.out.println("Unable to parse document.");
			System.exit(-1);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class.");
			System.exit(-1);
		}
	}
}
