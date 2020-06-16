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
		    
			String connectionURL = "jdbc:derby://" + 
					props.getProperty("host") + ":" + 
					props.getProperty("port") + "/" + 
					props.getProperty("name") + ";user=" + 
					props.getProperty("user") + ";password=" + 
					props.getProperty("password");

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
			
				ResultSet pollIdKey = null;
				
				// provjera postoji li tablica "Polls", ako ne, stvara se
				if(!rs.next()) {
					
					String query = 
							"CREATE TABLE Polls "
							+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
							+ "title VARCHAR(150) NOT NULL, "
							+ "message CLOB(2048) NOT NULL)";
					PreparedStatement pst = cpds.getConnection().prepareStatement(query);
					pst.execute();
					
					query = "INSERT INTO Polls (title, message) "
							+ "VALUES ('Glasanje za omiljeni bend:', "
							+ "'Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali.')";
					
					pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					pst.execute();
					
					pollIdKey = pst.getGeneratedKeys();
				}
				
				rs = dmd.getTables(null, conn.getSchema(), "PollOptions".toUpperCase(), null);
				// provjeta postoji li tablica "PollOptions"
				if(!rs.next()) {
					
					String query = 
							"CREATE TABLE PollOptions "
							+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
							+ "optionTitle VARCHAR(100) NOT NULL, "
							+ "optionLink VARCHAR(100) NOT NULL, "
							+ "pollID BIGINT,"
							+ "FOREIGN KEY (pollID) REFERENCES Polls(id))";
					PreparedStatement pst = conn.prepareStatement(query);
					pst.execute();
					
					// učitavanje dostupnih bendova za glasanje
					String fileNameDefintion = sce.getServletContext().getRealPath("WEB-INF/glasanje-definicija.txt");
					
					// dohvat ključa generiranog za ovaj poll
					Long pollId = null;
					if(pollIdKey != null && pollIdKey.next()) {
						pollId = pollIdKey.getLong(1);
					}
					
					try(Scanner sc = new Scanner(new File(fileNameDefintion))){
						
						while(sc.hasNextLine()) {
							String line = sc.nextLine();
							String[] splitted = line.split("\t");
							
							query = "INSERT INTO PollOptions (optionTitle, optionLink, pollID)"
									+ "VALUES (?, ?, ?)";
							pst = conn.prepareStatement(query);
							
							// ime benda
							pst.setString(1, splitted[1]);
							// link benda
							pst.setString(2, splitted[2]);
							// broj poll-a
							pst.setLong(3, pollId);
							
							pst.execute();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
