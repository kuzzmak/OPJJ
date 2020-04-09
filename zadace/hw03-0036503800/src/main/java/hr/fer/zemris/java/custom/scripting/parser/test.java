package hr.fer.zemris.java.custom.scripting.parser;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
		
		String error1 = "{$ FOR 3 1 10 1 $}"; //OK
		String error2 = "{$ FOR * \"1\" -10 \"1\" $}"; //OK
		String error3 = "{$ FOR year @sin 10 $}"; //OK
		String error4 = "{$ FOR year 1 10 \"1\" \"10\" $}"; //OK
		String error5 = "{$ FOR year $}"; //OK
		String error6 = "{$ FOR year 1 10 1 3 $}";
		
		String for1 = "{$ FOR i-1.35bbb\"1\" $}";
		String for2 = "{$ FOR i -1.35 bbb \"1\" $}";
		
		SmartScriptParser parser = new SmartScriptParser(for2);
	}
}
