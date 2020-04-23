package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	
	// LIKE
	@Test
	public void testLIKE1() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Blažić", "B*ć"));
	}
	
	@Test
	public void testLIKE2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Blažića", "Blažić"));
	}
	
	@Test
	public void testLIKE3() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
	}
	
	@Test
	public void testLIKE4() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("AAA", "AA*AA"));
	}
	
	@Test
	public void testLIKE5() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("AAA", "*AA"));
	}
	
	@Test
	public void testLIKE6() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("AAa", "AA*"));
	}
	
	// LESS
	@Test
	public void testLESS1() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("a", "b"));
	}
	
	@Test
	public void testLESS2() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertFalse(oper.satisfied("a", "a"));
	}
	
	@Test
	public void testLESS3() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("ant", "ao"));
	}
	
	// LESS_OR_EQUALS
	@Test
	public void testLESS_OR_EQUALS1() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("a", "b"));
	}
	
	@Test
	public void testLESS_OR_EQUALS2() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("a", "a"));
	}
	
	@Test
	public void testLESS_OR_EQUALS3() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("ant", "ao"));
	}
	
	// GREATER
	@Test
	public void testGREATER1() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("a", "b"));
	}
	
	@Test
	public void testGREATER2() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("a", "a"));
	}
	
	@Test
	public void testGREATER3() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("ant", "ao"));
	}
	
	// GREATER_OR_EQUALS
	@Test
	public void testGREATER_OR_EQUALS1() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertFalse(oper.satisfied("a", "b"));
	}
	
	@Test
	public void testGREATER_OR_EQUALS2() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(oper.satisfied("a", "a"));
	}
	
	@Test
	public void testGREATER_OR_EQUALS3() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertFalse(oper.satisfied("ant", "ao"));
	}
	
	// EQUALS
	@Test
	public void testEQUALS1() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertFalse(oper.satisfied("ant", "ao"));
	}
	
	@Test
	public void testEQUALS2() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertTrue(oper.satisfied("a", "a"));
	}
	
	// NOT_EQUALS
	@Test
	public void testNOT_EQUALS1() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertTrue(oper.satisfied("ant", "ao"));
	}
	
	@Test
	public void testNOT_EQUALS2() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertFalse(oper.satisfied("a", "a"));
	}
}
