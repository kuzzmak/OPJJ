package hr.fer.zemris.java.custom.scripting.parser;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class test {
	
	private static String loader(String fileName) {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try(InputStream is = new FileInputStream(fileName)){
			
			byte[] buffer = new byte[1024];
			
			while(true) {
				int read = is.read(buffer);
				if(read < 1) break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		}catch(IOException ex) {
			return null;
		}
	}

	public static void main(String[] args) throws IOException {
		
		String document = loader("src/test/resources/document1.txt");
		
//		String primjer = loader("src/test/resources/extra/primjer8.txt");
//		
//		String error1 = "{$ FOR 3 1 10 1 $}"; //OK
//		String error2 = "{$ FOR * \"1\" -10 \"1\" $}"; //OK
//		String error3 = "{$ FOR year @sin 10 $}"; //OK
//		String error4 = "{$ FOR year 1 10 \"1\" \"10\" $}"; //OK
//		String error5 = "{$ FOR year $}"; //OK
//		String error6 = "{$ FOR year 1 10 1 3 $}"; //OK
//		
//		String for1 = "{$ FOR i-1.35bbb\"1\" $}"; //OK
//		String for2 = "{$ FOR i -1.35 bbb   $}"; //OK
//		String for3 = "{$ FOR year 1 last_year $}";
//		
//		String tekst1 = "Example { bla } blu \\{$=1$} Nothing interesting {=here}"; //OK
//		String tekst2 = "A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}"; //OK
//		String tekst3 = "Example \\{$=1$}. Now actually write one {$=1$}";
//		
//		String equals1 = "{$= i i * @sin \"0.000\" @decfmt $}"; //OK
//		
//		
//		String foor = "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}";
		
		String doc = "This is sample text.\r\n"
				+ "{$ FOR i 1 10 1 $}"
				+ "\r\n  This is {$= i $}-th time this essage is generated.\r\n"
				+ "{$END$}\r\n {$ FOR i 0 10 2 $} \r\n  sin({$=i$}^2) = "
				+ "{$= i i * @sin  \"0.000\" @decfmt $} \r\n{$END$}";
		
		SmartScriptParser parser1 = new SmartScriptParser(document);
		DocumentNode documentNode1 = parser1.getDocmentNode();
		System.out.println(documentNode1);
		System.out.println("-------------------------");
		System.out.println();
		
		SmartScriptParser parser2 = new SmartScriptParser(documentNode1.toString());
		DocumentNode documentNode2 = parser2.getDocmentNode();
		
		System.out.println(documentNode2);
		System.out.println(documentNode1.equals(documentNode2));
		
		
	}
}
