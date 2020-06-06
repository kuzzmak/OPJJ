package hr.fer.zemris.java.hw13;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Servlet koji se inicira prilikom pokretanja i gašenja servera.
 * Po pokretanju servera učitava bendove za glasanje i pamti vrijeme
 * pokretanja, a kod gašenja zapisuje rezultate glasanja bendova.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebListener
public class ServerStartAndStopServlet implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		// vrijeme početka servera
		sce.getServletContext().setAttribute("timeStarted", System.currentTimeMillis());
		
		// učitavanje bendova za koje je moguće glasati
		String fileNameDefintion = sce.getServletContext().getRealPath("WEB-INF/glasanje-definicija.txt");
		Map<Long, String> bands = new HashMap<>();
		Map<Long, String> bandLinks = new HashMap<>();
		
		try(Scanner sc = new Scanner(new File(fileNameDefintion))){
			
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] splitted = line.split("\t");
				
				bands.put(Long.valueOf(splitted[0]), splitted[1]);
				bandLinks.put(Long.valueOf(splitted[0]), splitted[2]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sce.getServletContext().setAttribute("bands", bands);
		sce.getServletContext().setAttribute("bandLinks", bandLinks);
		
		// učitavanje rezultata glasanja 
		String fileNameResults = sce.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"); 
		Map<Long, Integer> results = new LinkedHashMap<>();
		
		try(Scanner sc = new Scanner(new File(fileNameResults))){
			
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] splitted = line.split("\t");
				
				results.put(Long.parseLong(splitted[0]), Integer.parseInt(splitted[1]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sce.getServletContext().setAttribute("results", results);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		String fileNameResults = sce.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"); 
		
		Map<Long, Integer> results = (Map<Long, Integer>) sce.getServletContext().getAttribute("results");
		
		// spremanje rezultata glasanje kod gašenja servera
		try (Writer writer = new BufferedWriter(new FileWriter(fileNameResults, false))) {

			for (Map.Entry<Long, Integer> entry : results.entrySet()) {
				writer.write(entry.getKey() + "\t" + entry.getValue() + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
