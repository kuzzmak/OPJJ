package hr.fer.zemris.java.hw06.crypto;

public class Util {
	
	private final static char[] hexArray = "0123456789abcdef".toCharArray();
	
	public static byte[] hextobyte(String keyText) {
		
		if(keyText.length() % 2 != 0)
			throw new IllegalArgumentException("Krivo zapisan broj u heksadekatskom obliku.");
		
		byte[] data = new byte[keyText.length() / 2];
		
	    for (int i = 0; i < keyText.length(); i += 2) {
	    	
	    	int num1 = Character.digit(keyText.charAt(i), 16) << 4;
	    	int num2 = Character.digit(keyText.charAt(i + 1), 16);
	        
	    	data[i / 2] = (byte)(num1 + num2);
	    }
	    return data;
	}
	
	public static String bytetohex(byte[] bytearray) {
		
		if(bytearray.length % 2 != 0)
			throw new IllegalArgumentException("Broj bajtova mora biti paran.");
		
		StringBuilder sb = new StringBuilder();
		
		for(byte b: bytearray) {
			
			int v = b & 0xFF;
            sb.append(hexArray[v >>> 4]);
            sb.append(hexArray[v & 0x0F]);
		}

		return sb.toString();
	}
}
