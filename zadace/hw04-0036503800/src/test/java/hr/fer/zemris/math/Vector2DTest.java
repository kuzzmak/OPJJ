package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class Vector2DTest {

	@Test
	public void normalanVektor() {
		Vector2D v = new Vector2D(1, 2);
		assertEquals(1, v.getX());
		assertEquals(2, v.getY());
	}
	
	@Test
	public void translacija() {
		Vector2D v = new Vector2D(1, 2);
		v.translate(new Vector2D(1, 1));
		assertEquals(2, v.getX());
		assertEquals(3, v.getY());
	}
	
	@Test
	public void translacija2() {
		Vector2D v = new Vector2D(1, 2);
		v.translate(new Vector2D(1, 0));
		assertEquals(2, v.getX());
		assertEquals(2, v.getY());
	}
	
	@Test
	public void translated() {
		Vector2D v = new Vector2D(1, 2);
		Vector2D v2 = v.translated(new Vector2D(1, 0));
		assertEquals(2, v2.getX());
		assertEquals(2, v2.getY());
	}
	
	@Test
	public void translated2() {
		Vector2D v = new Vector2D(1, 2);
		Vector2D v2 = v.translated(v);
		assertEquals(2, v2.getX());
		assertEquals(4, v2.getY());
	}
	
	@Test
	public void rotacijaZa45() {
		Vector2D v = new Vector2D(1, 1);
		v.rotate(Math.PI / 4);
		assertEquals(0, v.getX(), 1e-6);
		assertEquals(Math.sqrt(2), v.getY(), 1e-6);
	}
	
	@Test
	public void rotacijaZa180() {
		Vector2D v = new Vector2D(1, 1);
		v.rotate(Math.PI);
		assertEquals(-1, v.getX(), 1e-6);
		assertEquals(-1, v.getY(), 1e-6);
	}
	
	@Test
	public void rotacijaPaNovaInstanca() {
		Vector2D v = new Vector2D(1, 1);
		Vector2D v2 = v.rotated(Math.PI / 4);
		assertEquals(0, v2.getX(), 1e-6);
		assertEquals(Math.sqrt(2), v2.getY(), 1e-6);
	}
	
	@Test
	public void skaliranjeS0() {
		Vector2D v = new Vector2D(1, 1);
		v.scale(0);
		assertEquals(0, v.getX());
		assertEquals(0, v.getY());
	}
	
	@Test
	public void skaliranjeS1() {
		Vector2D v = new Vector2D(1, 1);
		v.scale(1);
		assertEquals(1, v.getX());
		assertEquals(1, v.getY());
	}
	
	@Test
	public void skaliranjeS10() {
		Vector2D v = new Vector2D(1, 1);
		v.scale(10);
		assertEquals(10, v.getX());
		assertEquals(10, v.getY());
	}
	
	@Test
	public void skaliranjeS10PaNovaInstanca() {
		Vector2D v = new Vector2D(1, 1);
		Vector2D v2 = v.scaled(10);
		assertEquals(10, v2.getX());
		assertEquals(10, v2.getY());
	}
	
	@Test
	public void kopija() {
		Vector2D v = new Vector2D(1, 1);
		Vector2D v2 = v.copy();
		assertFalse(v.equals(v2));
		assertEquals(v2.getX(), 1);
		assertEquals(v2.getY(), 1);
	}
}
