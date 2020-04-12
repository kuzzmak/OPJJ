package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
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

		String docBody = loader("src/test/resources/primjer1.txt");

		SmartScriptParser parser = new SmartScriptParser(docBody);

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
	}

	@Test
	public void primjer2() {

		String docBody = loader("src/test/resources/primjer2.txt");

		SmartScriptParser parser = new SmartScriptParser(docBody);

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
	}
	
	@Test
	public void primjer3() {

		String docBody = loader("src/test/resources/primjer3.txt");

		SmartScriptParser parser = new SmartScriptParser(docBody);

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
	}
	
	@Test
	public void primjer4() {

		String docBody = loader("src/test/resources/primjer4.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void primjer5() {

		String docBody = loader("src/test/resources/primjer5.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void primjer6() {

		String docBody = loader("src/test/resources/primjer6.txt");

		SmartScriptParser parser = new SmartScriptParser(docBody);

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(2, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
		assertEquals(documentNode.children.get(1).getClass(), EchoNode.class);
	}
	
	@Test
	public void primjer7() {

		String docBody = loader("src/test/resources/primjer7.txt");

		SmartScriptParser parser = new SmartScriptParser(docBody);

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(2, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
		assertEquals(documentNode.children.get(1).getClass(), EchoNode.class);
	}
	
	@Test
	public void primjer8() {

		String docBody = loader("src/test/resources/primjer8.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void primjer9() {

		String docBody = loader("src/test/resources/primjer9.txt");

		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
	}
	
	@Test
	public void neispravnoImeVarijable() {
		
		assertThrows(SmartScriptParserException.class, 
				() -> new SmartScriptParser("{$ FOR 3 1 10 1 $}"));
	}

	@Test
	public void neispravnoImeVarijable2() {
		
		assertThrows(SmartScriptParserException.class, 
				() -> new SmartScriptParser("{$ FOR * \"1\" -10 \"1\" $}"));
	}
	
	@Test
	public void funkcijaNaKrivomMjestu() {
		
		assertThrows(SmartScriptParserException.class, 
				() -> new SmartScriptParser("{$ FOR year @sin 10 $}"));
	}
	
	@Test
	public void previseArgumenata() {
		
		assertThrows(SmartScriptParserException.class, 
				() -> new SmartScriptParser("{$ FOR year 1 10 \"1\" \"10\" $}"));
	}
	
	@Test
	public void premaloArgumenata() {
		
		assertThrows(SmartScriptParserException.class, 
				() -> new SmartScriptParser("{$ FOR year $}"));
	}
	
	@Test
	public void previseArgumenata2() {
		
		assertThrows(SmartScriptParserException.class, 
				() -> new SmartScriptParser("{$ FOR year 1 10 1 3 $}"));
	}
	
	@Test
	public void normalniFORBezRazmaka() {
		
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i-1.35bbb\"1\" $}");

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), ForLoopNode.class);
	}
	
	@Test
	public void normalniFORSRazmacima() {
		
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i -1.35 bbb \"1\" $}");

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), ForLoopNode.class);
	}
	
	@Test
	public void normalniFORSDvijeVarijable() {
		
		SmartScriptParser parser = new SmartScriptParser("{$ FOR year 1 last_year $}");

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), ForLoopNode.class);
	}
	
	@Test
	public void samoText() {
		
		SmartScriptParser parser = new SmartScriptParser("Example { bla } blu \\{$=1$} Nothing interesting {=here}");

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
	}
	
	@Test
	public void textPaEcho() {
		
		SmartScriptParser parser = new SmartScriptParser("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}");

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(2, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
		assertEquals(documentNode.children.get(1).getClass(), EchoNode.class);
	}
	
	@Test
	public void textPaEcho2() {
		
		SmartScriptParser parser = new SmartScriptParser("Example \\{$=1$}. Now actually write one {$=1$}");

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(2, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
		assertEquals(documentNode.children.get(1).getClass(), EchoNode.class);
	}
	
	@Test
	public void normalanEcho() {
		
		SmartScriptParser parser = new SmartScriptParser("{$= i i * @sin \"0.000\" @decfmt $}");

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(1, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), EchoNode.class);
	}
	
	@Test
	public void mix() {
		
		SmartScriptParser parser = new SmartScriptParser("sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}");

		DocumentNode documentNode = parser.getDocmentNode();
		
		assertEquals(4, documentNode.children.size());
		assertEquals(documentNode.children.get(0).getClass(), TextNode.class);
		assertEquals(documentNode.children.get(1).getClass(), EchoNode.class);
		assertEquals(documentNode.children.get(2).getClass(), TextNode.class);
		assertEquals(documentNode.children.get(3).getClass(), EchoNode.class);
	}
	
	@Test
	public void parsiranjePaNormalniTekstPaParsiranje() {
		
		String document = loader("src/test/resources/document1.txt");
		
		SmartScriptParser parser1 = new SmartScriptParser(document);
		DocumentNode documentNode1 = parser1.getDocmentNode();
		
		SmartScriptParser parser2 = new SmartScriptParser(documentNode1.toString());
		DocumentNode documentNode2 = parser2.getDocmentNode();
		
		assertTrue(documentNode1.equals(documentNode2));
	}
}
