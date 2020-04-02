package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComplexNumberTest {

	@Test
	public void ispravnaInicijalizacija() {
		ComplexNumber c = new ComplexNumber(1, 1);
		assertEquals(1, c.getReal());
		assertEquals(1, c.getImaginary());
	}
	
	// fromReal metoda
	@Test
	public void fromRealTest() {
		ComplexNumber c = ComplexNumber.fromReal(1);
		assertEquals(1, c.getReal());
		assertEquals(0, c.getImaginary());
	}
	
	// fromImaginary metoda
	@Test
	public void fromImaginaryTest() {
		ComplexNumber c = ComplexNumber.fromImaginary(1);
		assertEquals(0, c.getReal());
		assertEquals(1, c.getImaginary());
	}
	
	// fromMagnitudeAndAngle metoda
	@Test
	public void normalneVrijednosti() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(5, 0.927295218);
		assertEquals(3, c.getReal(), 1e-6);
		assertEquals(4, c.getImaginary(), 1e-6);
	}
	
	@Test
	public void kutNula() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(5, 0);
		assertEquals(5, c.getReal(), 1e-6);
		assertEquals(0, c.getImaginary(), 1e-6);
	}
	
	@Test
	public void amplitudaNulaKutNula() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(0, 0);
		assertEquals(0, c.getReal(), 1e-6);
		assertEquals(0, c.getImaginary(), 1e-6);
	}
	
	@Test
	public void kutPiPola() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(5, Math.PI / 2);
		assertEquals(0, c.getReal(), 1e-6);
		assertEquals(5, c.getImaginary(), 1e-6);
	}
	
	// parse metoda
	@Test
	public void normalniBrojBezRazmaka() {
		ComplexNumber c = ComplexNumber.parse("1+i");
		assertEquals(1, c.getReal());
		assertEquals(1, c.getImaginary());
	}
	
	@Test
	public void normalniBrojSRazmakom() {
		ComplexNumber c = ComplexNumber.parse("1  +i");
		assertEquals(1, c.getReal());
		assertEquals(1, c.getImaginary());
	}
	
	@Test
	public void normalniBrojSPredvodecimPlusom() {
		ComplexNumber c = ComplexNumber.parse("+1+i");
		assertEquals(1, c.getReal());
		assertEquals(1, c.getImaginary());
	}
	
	@Test
	public void normalniBroj2() {
		ComplexNumber c = ComplexNumber.parse("1-i");
		assertEquals(1, c.getReal());
		assertEquals(-1, c.getImaginary());
	}
	
	@Test
	public void samoRealni() {
		ComplexNumber c = ComplexNumber.parse("1");
		assertEquals(1, c.getReal());
		assertEquals(0, c.getImaginary());
	}
	
	@Test
	public void samoImaginarni() {
		ComplexNumber c = ComplexNumber.parse("i");
		assertEquals(0, c.getReal());
		assertEquals(1, c.getImaginary());
	}
	
	@Test
	public void imaginarniSPlusom() {
		ComplexNumber c = ComplexNumber.parse("+i");
		assertEquals(0, c.getReal());
		assertEquals(1, c.getImaginary());
	}
	
	@Test
	public void decimalniNormalniBroj() {
		ComplexNumber c = ComplexNumber.parse("1.2+0.69i");
		assertEquals(1.2, c.getReal());
		assertEquals(0.69, c.getImaginary());
	}
	
	// getMagnitude metoda
	@Test
	public void normalanBroj() {
		ComplexNumber c = ComplexNumber.parse("3+4i");
		assertEquals(5, c.getMagnitude(), 1e-6);
	}
	
	@Test
	public void samoRealan() {
		ComplexNumber c = ComplexNumber.parse("3");
		assertEquals(3, c.getMagnitude(), 1e-6);
	}
	
	@Test
	public void samoImaginaran() {
		ComplexNumber c = ComplexNumber.parse("4i");
		assertEquals(4, c.getMagnitude(), 1e-6);
	}
	
	@Test
	public void nulaOba() {
		ComplexNumber c = ComplexNumber.parse("0");
		assertEquals(0, c.getMagnitude(), 1e-6);
	}
	
	// getAngle metoda
	@Test
	public void brojUPrvomKvadrantu() {
		ComplexNumber c = ComplexNumber.parse("1+i");
		assertEquals(Math.PI / 4, c.getAngle(), 1e-6);
	}
	
	@Test
	public void brojUDrugomKvadrantu() {
		ComplexNumber c = ComplexNumber.parse("-1+i");
		assertEquals(Math.PI - Math.PI / 4, c.getAngle(), 1e-6);
	}
	
	@Test
	public void brojUTrecemKvadrantu() {
		ComplexNumber c = ComplexNumber.parse("-1-i");
		assertEquals(Math.PI + Math.PI / 4, c.getAngle(), 1e-6);
	}
	
	@Test
	public void brojUCetvrtomKvadrantu() {
		ComplexNumber c = ComplexNumber.parse("1-i");
		assertEquals(2 * Math.PI - Math.PI / 4, c.getAngle(), 1e-6);
	}
	
	@Test
	public void kutSamoImaginarnog() {
		ComplexNumber c = ComplexNumber.parse("-i");
		assertEquals(Math.PI + Math.PI / 2, c.getAngle(), 1e-6);
	}
	
	@Test
	public void kutSamoRealnog() {
		ComplexNumber c = ComplexNumber.parse("2");
		assertEquals(0, c.getAngle(), 1e-6);
	}
	
	// add metoda
	@Test
	public void normalnoZbrajanje() {
		ComplexNumber c1 = ComplexNumber.parse("1+i");
		ComplexNumber c2 = ComplexNumber.parse("1+i");
		ComplexNumber c3 = c1.add(c2);
		assertEquals(2, c3.getReal());
		assertEquals(2, c3.getImaginary());
	}
	
	@Test
	public void jedanBezRealnog() {
		ComplexNumber c1 = ComplexNumber.parse("i");
		ComplexNumber c2 = ComplexNumber.parse("1+i");
		ComplexNumber c3 = c1.add(c2);
		assertEquals(1, c3.getReal());
		assertEquals(2, c3.getImaginary());
	}
	
	@Test
	public void jedanBezImaginarnog() {
		ComplexNumber c1 = ComplexNumber.parse("1");
		ComplexNumber c2 = ComplexNumber.parse("1+i");
		ComplexNumber c3 = c1.add(c2);
		assertEquals(2, c3.getReal());
		assertEquals(1, c3.getImaginary());
	}
	
	@Test
	public void jedanBezRealnogDrugiBezImaginarnog() {
		ComplexNumber c1 = ComplexNumber.parse("i");
		ComplexNumber c2 = ComplexNumber.parse("1");
		ComplexNumber c3 = c1.add(c2);
		assertEquals(1, c3.getReal());
		assertEquals(1, c3.getImaginary());
	}
	
	@Test
	public void jedanNula() {
		ComplexNumber c1 = ComplexNumber.parse("0");
		ComplexNumber c2 = ComplexNumber.parse("1+i");
		ComplexNumber c3 = c1.add(c2);
		assertEquals(1, c3.getReal());
		assertEquals(1, c3.getImaginary());
	}
}
