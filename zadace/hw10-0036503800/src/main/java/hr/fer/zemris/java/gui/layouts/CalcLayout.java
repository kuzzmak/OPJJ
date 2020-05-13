package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Hashtable;

/**
 * Razred koji predstavlja poseban layout manager za 
 * oponašanje izgleda jednostavnog džepnog računala.
 * 
 * @author kuzmi
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	// razmak između redaka i stupaca
	private int space;
	// tablica s pojedinim komponentama prozora
	Hashtable<Component, RCPosition> compTable;
	
	/**
	 * Konstruktor koji postavlja razmak između redaka i stupaca na {@code space}.
	 * 
	 * @param space razmak između redaka i stupaca.
	 */
	public CalcLayout(int space) {
		this.space = space;
		this.compTable = new Hashtable<>();
	}

	/**
	 * Konstruktor koji postavlja razmak između redaka i stupaca na 0.
	 * 
	 */
	public CalcLayout() {
		this(0);
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		compTable.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calcDimension(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calcDimension(parent, Component::getMinimumSize);
	}

	@Override
	public void layoutContainer(Container parent) {

		Insets insets = parent.getInsets();
		
		int nComponents = parent.getComponentCount();
		
		if(nComponents == 0) return;
		
		Dimension size = parent.getSize();
		
		int totalW = size.width - (insets.left + insets.right);
		int totalH = size.height - (insets.top + insets.bottom);
		
		for(int i = 0; i < nComponents; i++) {
			
			Component c = parent.getComponent(i);
			RCPosition pos = compTable.get(c);
			
			// veličine svake ćelije s razmakom
			double totalCellW;
			double totalCellH;
			
			if(pos.equals(new RCPosition(1, 1))) {
				totalCellW = totalW / 7. * 5;
				totalCellH = totalH / 5.;
			}else {
				totalCellW = totalW / 7.;
				totalCellH = totalH / 5.;
			}
				
			// veličina svake ćelije bez razmaka
			double cellW = totalCellW;
			double cellH = totalCellH;
			
			cellW = pos.getColumn() != 7 ? cellW - space : cellW;
			cellH = pos.getRow() != 5 ? cellH - space: cellH;
			
			// indeksi za dodavanje kreću od 1
			int col = pos.getColumn() - 1;
			int row = pos.getRow() - 1;
			
			// x pozicija početka ćelije
			int x = (int) (insets.left + col * totalCellW);
			// y pozicija početka ćelije
			int y = (int) (insets.top + row * totalCellH);
			// širina ćelije
			int w = (int) (cellW);
			if(w < 0) w = 0;
			// visina ćelije
			int h = (int) (cellH);
			if(h < 0) h = 0;
			
			c.setBounds(x, y, w, h);
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		
		if(comp == null) throw new NullPointerException("Komponenta ne može bit null.");
		if(constraints == null)throw new NullPointerException("Pozicija mora biti različita od null.");
		
		RCPosition pos = null;
		
		if(constraints instanceof String){
			
			pos = RCPosition.parse(String.valueOf(constraints));
			
		}else if(constraints instanceof RCPosition) {
			
			pos = (RCPosition) constraints;
			
		}else 
			throw new IllegalArgumentException("Pozicija mora biti string ili rcposition.");
			
		if(RCPosition.invalidPositions.contains(pos))
			throw new CalcLayoutException("Nije moguće dodati ćeliju na indeks: " + pos.getRow() + ", " + pos.getColumn());
		
		setPosition(comp, pos);		
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
	
	/**
	 * Metoda za dodavanje komponente u tablicu komponenta.
	 * 
	 * @param c komponenta koja se dodaje
	 * @param pos indeks komponente u tablici
	 */
	private void setPosition(Component c, RCPosition pos) {
		compTable.put(c, pos);
	}
	
	/**
	 * Sućelje koje modelira dohvat potrebnih veličina komponente.
	 * 
	 * @author kuzmi
	 *
	 */
	private interface SizeGetter{
		Dimension getSize(Component c);
	}
	
	/**
	 * Metoda za izračun veličine komponente roditelja obzirom
	 * na veličinu svakog od djeteta.
	 * 
	 * @param parent roditelj čija se veličina izračunava
	 * @param getter sučelje za dohvat željene vrste veličine
	 * @return objekt tipa {@code Dimension} koji predstavlja veličinu komponente
	 */
	private Dimension calcDimension(Container parent, SizeGetter getter) {
		
		int nComponents = parent.getComponentCount();
		Dimension dim = new Dimension(0, 0);
		
		double tempWidth = 0;
		double tempHeight = 0;
		
		for(int i = 0; i < nComponents; i++) {
			
			Component c = parent.getComponent(i);
			Dimension cDim = getter.getSize(c);
			RCPosition pos = compTable.get(c);
			
			if(cDim != null) {
				
				if(pos.equals(RCPosition.FIRST)) {
					
					tempWidth = Math.max(tempWidth, (cDim.width - 4 * space) / 5.);
					tempHeight = Math.max(tempHeight, cDim.height);
					
				}else {
					
					tempWidth = Math.max(tempWidth, cDim.width);
					tempHeight = Math.max(tempHeight, cDim.height);
				}
			}
		}
		
		dim.width = (int)(tempWidth * 7 + space * 6);
		dim.height = (int)(tempHeight * 5 + space * 4);
		
		Insets parentInsets = parent.getInsets();
		
		dim.width += parentInsets.left + parentInsets.right;
		dim.height += parentInsets.top + parentInsets.bottom;
		
		return dim;
	}

}
