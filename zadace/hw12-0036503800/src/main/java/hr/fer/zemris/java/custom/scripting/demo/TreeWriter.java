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
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class TreeWriter {

	public static void main(String[] args) throws IOException {

		demo5();
	}

	public static void demo1() {

		String filePath = "C:\\Users\\tonkec\\Documents\\OPJJ\\zadace\\hw12-0036503800\\osnovni.smscr.txt";

		try {
			String documentBody = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
			Map<String, String> parameters = new HashMap<String, String>();
			Map<String, String> persistentParameters = new HashMap<String, String>();
			List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>(); // put some parameter into parameters map
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocmentNode(),
					new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

		} catch (SmartScriptingLexerException e) {
			System.out.println("Unable to parse document.");
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class.");
			System.exit(-1);
		}
	}

	public static void demo2() {

		String filePath = "C:\\Users\\tonkec\\Documents\\OPJJ\\zadace\\hw12-0036503800\\zbrajanje.smscr.txt";

		try {
			String documentBody = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
			Map<String, String> parameters = new HashMap<String, String>();
			Map<String, String> persistentParameters = new HashMap<String, String>();
			List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>(); // put some parameter into parameters map
			parameters.put("a", "4"); // create engine and execute it
			parameters.put("b", "2");
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocmentNode(),
					new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();

		} catch (SmartScriptingLexerException e) {
			System.out.println("Unable to parse document.");
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class.");
			System.exit(-1);
		}
	}

	public static void demo3() {
		String filePath = "C:\\Users\\tonkec\\Documents\\OPJJ\\zadace\\hw12-0036503800\\brojPoziva.smscr.txt";

		try {
			String documentBody = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
			Map<String, String> parameters = new HashMap<String, String>();
			Map<String, String> persistentParameters = new HashMap<String, String>();
			List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>(); // put some parameter into parameters map
			persistentParameters.put("brojPoziva", "3");
			RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocmentNode(), rc).execute();
			System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));

		} catch (SmartScriptingLexerException e) {
			System.out.println("Unable to parse document.");
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class.");
			System.exit(-1);
		}
	}

	public static void demo4() {
		String filePath = "C:\\Users\\tonkec\\Documents\\OPJJ\\zadace\\hw12-0036503800\\fibonacci.smscr.txt";
		try {
			String documentBody = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
			Map<String, String> parameters = new HashMap<String, String>();
			Map<String, String> persistentParameters = new HashMap<String, String>();
			List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>(); // create engine and execute it
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocmentNode(),
					new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
		
		} catch (SmartScriptingLexerException e) {
			System.out.println("Unable to parse document.");
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class.");
			System.exit(-1);
		}
	}
	
	public static void demo5() {
		String filePath = "C:\\Users\\tonkec\\Documents\\OPJJ\\zadace\\hw12-0036503800\\fibonaccih.smscr.txt";
		try {
			String documentBody = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
			Map<String, String> parameters = new HashMap<String, String>();
			Map<String, String> persistentParameters = new HashMap<String, String>();
			List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>(); // create engine and execute it
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocmentNode(),
					new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
		
		} catch (SmartScriptingLexerException e) {
			System.out.println("Unable to parse document.");
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class.");
			System.exit(-1);
		}
	}
}
