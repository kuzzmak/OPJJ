package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class UtilTest {
	
	@Test
	public void testHexToByte() {
		String hex = "01aE22";
		assertEquals(Arrays.toString(new byte[] {1, -82, 34}),  
				Arrays.toString(Util.hextobyte(hex)));
	}

	@Test
	public void testByteToHex() {
		byte[] b = new byte[] {1, -82, 34};
		assertEquals("01aE22".toLowerCase(), Util.bytetohex(b));
	}
	
	@Test
	public void testKrivaDuljinaHex() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01a"));
	}
	
	@Test
	public void predanNull() {
		assertThrows(NullPointerException.class, () -> Util.hextobyte(null));
	}
	
	@Test
	public void predanNull2() {
		assertThrows(NullPointerException.class, () -> Util.bytetohex(null));
	}
	
	@Test
	public void jedanByte() {
		byte[] b = new byte[] {1};
		assertEquals("01".toLowerCase(), Util.bytetohex(b));
	}
}
