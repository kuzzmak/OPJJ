package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred koji predstavlja konfiguraciju stupčastog grafa.
 * 
 * @author Antonio Kuzminski
 *
 */
public class BarChart extends JFrame{

	private static final long serialVersionUID = 1L;

	private List<XYValue> values;
	private String xName;
	private String yName;
	private int minY;
	private int maxY;
	private int diff;
	private String chartName;
	
	/**
	 * Konstruktor.
	 * 
	 * @param values vrijednosti koje se prikazuju u grafu
	 * @param xName ime x osi
	 * @param yName ime y osi
	 * @param minY minimalne vrijednost y osi
	 * @param maxY maksimalna vrijednost y osi
	 * @param diff razmak među vrijednostima y osi
	 * @param chartName staza do datoteke grafa
	 */
	public BarChart(List<XYValue> values, String xName, String yName, int minY, int maxY, int diff, String chartName) {
		super();
		this.values = values;
		this.xName = xName;
		this.yName = yName;
		this.minY = minY;
		this.maxY = maxY;
		this.diff = diff;
		this.chartName = chartName;
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * Metoda za inicijalizaciju grafičkog sučelja.
	 * 
	 */
	private void initGUI() {
		
		Container cp = getContentPane();
		
		JLabel graphName = new JLabel(chartName, SwingConstants.CENTER);
		cp.add(graphName, BorderLayout.PAGE_START);
		
		JPanel panel = new JPanel();
		
		BarChartComponent bcc = new BarChartComponent(this);
		panel.add(bcc);
		
		cp.add(panel, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(500, 500));
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
	
	/**
	 * Metoda za učitavanje konfiguracije grafa iz datoteke {@code file}.
	 * 
	 * @param file datoteka iz koje se učitava
	 * @return objekt tipa {@code BarChart}
	 */
	private static BarChart loadFromFile(String file){
		
		List<XYValue> values = new ArrayList<>();
		String xName = "";
		String yName = "";
		int yMin = 0;
		int yMax = 0;
		int diff = 0;
		
		try {
			
			try(Scanner sc = new Scanner(new File(file))){
				
				String line = sc.nextLine().strip();
				xName = new String(line);
				
				line = sc.nextLine().strip();
				yName = new String(line);
				
				line = sc.nextLine().strip();
				String[] dataSplitted = line.split("\\s+");
				
				for(String s: dataSplitted) {
					String[] xy = s.split(",");
					values.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
				}
				
				line = sc.nextLine().strip();
				yMin = Integer.parseInt(line);
				
				line = sc.nextLine().strip();
				yMax = Integer.parseInt(line);
				
				line = sc.nextLine().strip();
				diff = Integer.parseInt(line);
			}
			
		}catch(Exception e) {
			System.out.println("Greška prilikom parsiranja opisa grafa.");
			return null;
		}
		
		if(yMin < 0) 
			throw new IllegalArgumentException("Minimalna vrijednost ne može biti negativna.");
		
		for(XYValue value: values) {
			if(value.getY() < yMin)
				throw new IllegalArgumentException("Minimalna vrijednost je krivo zadana.");
		}
		
		return new BarChart(values, xName, yName, yMin, yMax, diff, file);
	}
	
	/**
	 * Funkcije iz koje reće izvođenje glavnog programa.
	 * 
	 * @param args argumenti programa
	 */
	public static void main(String[] args) {
		
		BarChart chart = loadFromFile(args[0]);
		
		if(chart != null)
			SwingUtilities.invokeLater(() -> chart.setVisible(true));
		else
			System.out.println("Greška prilikom učitavanja grafa.");
	}
	
}
