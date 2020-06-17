package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		File configFile = new File(sce.getServletContext().getRealPath("WEB-INF/dbsettings.properties"));
		Properties props;

		try {

			FileReader reader = new FileReader(configFile);
			props = new Properties();
			props.load(reader);

			String connectionURL = "jdbc:derby://" + props.getProperty("host") + ":" + props.getProperty("port") + "/"
					+ props.getProperty("name") + ";user=" + props.getProperty("user") + ";password="
					+ props.getProperty("password");

			ComboPooledDataSource cpds = new ComboPooledDataSource();
			try {
				cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
			} catch (PropertyVetoException e1) {
				throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
			}
			cpds.setJdbcUrl(connectionURL);

			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

			try {

				Connection conn = cpds.getConnection();

				DatabaseMetaData dmd = conn.getMetaData();
				ResultSet rs = dmd.getTables(null, conn.getSchema(), "Polls".toUpperCase(), null);

				List<Long> pollKeys = new ArrayList<>();

				// provjera postoji li tablica "Polls", ako ne, stvara se
				if (!rs.next()) {

					String query = "CREATE TABLE Polls " + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
							+ "title VARCHAR(150) NOT NULL, " + "message CLOB(2048) NOT NULL)";
					PreparedStatement pst = cpds.getConnection().prepareStatement(query);
					pst.execute();

					List<Poll> polls = loadPolls(sce.getServletContext().getRealPath("WEB-INF/ankete-definicija.txt"));
					
					query = "INSERT INTO Polls (title, message) VALUES (?, ?)";
					
					for(Poll poll: polls) {
						pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
						pst.setString(1, poll.getTitle());
						pst.setString(2, poll.getMessage());
						pst.execute();
						
						ResultSet genKey = pst.getGeneratedKeys();
						
						try {
							if(genKey != null && genKey.next()) {
								long newID = genKey.getLong(1);
								pollKeys.add(newID);
							}
						} finally {
							try { genKey.close(); } catch(SQLException ex) {
								ex.printStackTrace();
							}
						}
					}
				}

				rs = dmd.getTables(null, conn.getSchema(), "PollOptions".toUpperCase(), null);
				// provjeta postoji li tablica "PollOptions"
				if (!rs.next()) {

					String query = "CREATE TABLE PollOptions " + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
							+ "optionTitle VARCHAR(100) NOT NULL, " + "optionLink VARCHAR(100) NOT NULL, "
							+ "pollID BIGINT, " + "votesCount BIGINT, " + "FOREIGN KEY (pollID) REFERENCES Polls(id))";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.execute();
					
					// za svaku anketu
					for(int i = 0; i < pollKeys.size(); i++) {
						
						// učitavanje dostupnih bendova za glasanje
						String fileNameDefintion = sce.getServletContext().getRealPath("WEB-INF/glasanje-definicija-" + String.valueOf(i + 1) + ".txt");
						
						try (Scanner sc = new Scanner(new File(fileNameDefintion))) {

							while (sc.hasNextLine()) {
								String line = sc.nextLine();
								String[] splitted = line.split("\t");

								query = "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount)"
										+ "VALUES (?, ?, ?, ?)";
								pst = conn.prepareStatement(query);

								pst.setString(1, splitted[1]);
								pst.setString(2, splitted[2]);
								pst.setLong(3, pollKeys.get(i));
								// broj glasova
								pst.setLong(4, 0);

								pst.execute();
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Funnkcija za učitavanje dostupnih anketa.
	 * 
	 * @param fileName staza do datoteke s anketama
	 * @return lista objekata tipa {@code Poll} koji predstavlja pojedinu anketu
	 */
	public List<Poll> loadPolls(String fileName) {
		
		List<Poll> polls = new ArrayList<>();
		
		try (Scanner sc = new Scanner(new File(fileName))) {

			while (sc.hasNextLine()) {

				String line = sc.nextLine();
				String[] splitted = line.split("\t");
				
				String title = splitted[0];
				String message = splitted[1];
				
				Poll poll = new Poll();
				poll.setTitle(title);
				poll.setMessage(message);
				polls.add(poll);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return polls;
	}

}
