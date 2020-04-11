package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {

	private static String loader(String fileName) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try (InputStream is = new FileInputStream(fileName)) {

			byte[] buffer = new byte[1024];

			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

	@Test
	public void prazniTekst() {

		SmartScriptParser parser = new SmartScriptParser("");

		DocumentNode documentNode = parser.getDocmentNode();

		assertEquals("", documentNode.toString());
	}

	@Test
	public void primjer1() {

		String docBody = loader("src/test/resources/extra/primjer1.txt");

		SmartScriptParser parser = new SmartScriptParser(docBody);

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
	}

	@Test
	public void primjer2() {

		String docBody = loader("src/test/resources/extra/primjer2.txt");

		SmartScriptParser parser = new SmartScriptParser(docBody);

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
	}
	
	@Test
	public void primjer3() {

		String docBody = loader("src/test/resources/extra/primjer3.txt");

		SmartScriptParser parser = new SmartScriptParser(docBody);

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
	}
	
	@Test
	public void primjer4() {

		String docBody = loader("src/test/resources/extra/primjer4.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void primjer5() {

		String docBody = loader("src/test/resources/extra/primjer5.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void primjer6() {

		String docBody = loader("src/test/resources/extra/primjer6.txt");

		SmartScriptParser parser = new SmartScriptParser(docBody);

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
	}
	
	@Test
	public void primjer7() {

		String docBody = loader("src/test/resources/extra/primjer7.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void primjer8() {

		String docBody = loader("src/test/resources/extra/primjer8.txt");

		SmartScriptParser parser = new SmartScriptParser(docBody);

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
	}
	
	@Test
	public void primjer9() {

		String docBody = loader("src/test/resources/extra/primjer9.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}

}
