package hr.fer.zemris.java.gui.charts;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class BarChart extends JFrame{

	private List<XYValue> values;
	private String xName;
	private String yName;
	private int minY;
	private int maxY;
	private int diff;
	
	/**
	 * Konstruktor.
	 * 
	 * @param values vrijednosti koje se prikazuju u grafu
	 * @param xName ime x osi
	 * @param yName ime y osi
	 * @param minY minimalne vrijednost y osi
	 * @param maxY maksimalna vrijednost y osi
	 * @param diff razmak među vrijednostima y osi
	 */
	public BarChart(List<XYValue> values, String xName, String yName, int minY, int maxY, int diff) {
		super();
		this.values = values;
		this.xName = xName;
		this.yName = yName;
		this.minY = minY;
		this.maxY = maxY;
		this.diff = diff;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.add(new BarChartComponent(this));
		
	}

	public List<XYValue> getValues() {
		return values;
	}

	public String getxName() {
		return xName;
	}

	public String getyName() {
		return yName;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getDiff() {
		return diff;
	}
	
	public static void main(String[] args) {
		
		List<XYValue> values = new ArrayList<>(Arrays.asList(new XYValue(1, 8), new XYValue(2, 20), new XYValue(3, 22), new XYValue(4, 10), new XYValue(5, 4)));
		String xName = "Nuber of people in the car";
		String yName = "Frequency";
		int minY = 4;
		int maxY = 22;
		int diff = 2;
		
		SwingUtilities.invokeLater(() -> new BarChart(values, xName, yName, minY, maxY, diff).setVisible(true));
	}
	
}
