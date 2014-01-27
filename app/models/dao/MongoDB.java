package models.dao;

import java.io.File; 
import java.io.FileReader;
import java.io.IOException; 
import java.util.Properties;

import play.api.Play; 

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDB
{
	private static final String PROPERTIES_FILE_PATH  = "/conf/mongodb.properties";
	private static String SERVER   = new String();
	private static int    PORT     = 0;
	private static String DATABASE = new String();
	private static String USERNAME = new String();
	private static String PASSWORD = new String();
	
	private static MongoDB mongo = null;
	private static DB mongoDB = null;
	
	// MONGODB DEFAULT CONSTRUCTOR
	private MongoDB()
	{
		// CHECK IF THE FILE WAS LOADED PROPERTLY
		if(propertiesLoader())
		{
			MongoClient mongoClient = null;
			try 
			{
				mongoClient = new MongoClient(SERVER,PORT);
				mongoDB = mongoClient.getDB(DATABASE);
			}
			catch (Exception e) 
			{
				System.err.println("Erreur lorsqu'on établie une connexion à la base de données ! "+e.getMessage());
			}
		}	
		else System.err.println("Not property file found");
	}
	
	// THIS METHOD IS A REDEFICNITION
	public static synchronized MongoDB getInstance() 
	{
		if(mongo == null)
		{	
			System.out.println("Creating MongoDB instance...");
			return new MongoDB();
		}
		return mongo;
	}
	
	// THIS METHOD IS A REDEFICNITION
	public DB getConnection()
	{
		if(mongoDB != null)
		{
			boolean authentication = false;
			try
			{
				authentication = mongoDB.authenticate(USERNAME, PASSWORD.toCharArray());
			}
			catch (Exception e) 
			{
				System.out.println("Error trying to authenticate into the database "+e.getMessage());
			}
			if(authentication){ return mongoDB; }
		}
		return null;
	}
	
	// THIS METHOD ATTEMPT TO LOAD A FILE HOLDING THE DATABSE CONNECTION CREDENTIALS
	private boolean propertiesLoader()
	{
		boolean connected = false;
		
		// PRE-TRAITEMENT
		Properties properties = new Properties();
		File file = Play.current().getFile(PROPERTIES_FILE_PATH);
		FileReader fr = null;
		
		try 
		{
			// READING PROPERTIES FILE
			fr = new java.io.FileReader(file);
			
			try
			{
				properties.load(fr);
				
				SERVER   = properties.getProperty("server");
				PORT     = Integer.parseInt(properties.getProperty("port"));
				DATABASE = properties.getProperty("database");
				USERNAME = properties.getProperty("username");
				PASSWORD = properties.getProperty("password");

				connected = true;
			}
			catch(IOException e)
			{
				System.err.println("Error loading properties file on ["+PROPERTIES_FILE_PATH+"]");
			}
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			try { fr.close(); }
			catch (IOException e)  { e.printStackTrace(); }
		}
		
		return connected;
	}	
}
