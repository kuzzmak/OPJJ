package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JList;

import org.junit.jupiter.api.Test;

public class PrimListModelTest {
	
	@Test
	public void testPrviPrim() {
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		assertEquals(1, list1.getModel().getElementAt(0));
		assertEquals(1, list2.getModel().getElementAt(0));
	}
	
	@Test
	public void testDrugiPrim() {
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		model.next();
		
		assertEquals(2, list1.getModel().getElementAt(1));
		assertEquals(2, list2.getModel().getElementAt(1));
	}
	
	@Test
	public void testTreciPrim() {
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		model.next();
		model.next();
		
		assertEquals(3, list1.getModel().getElementAt(2));
		assertEquals(3, list2.getModel().getElementAt(2));
	}
	
	@Test
	public void testCetvrtiPrim() {
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		model.next();
		model.next();
		model.next();
		
		assertEquals(5, list1.getModel().getElementAt(3));
		assertEquals(5, list2.getModel().getElementAt(3));
	}

}
