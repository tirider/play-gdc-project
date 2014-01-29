package models.dao;

import java.io.File;
import java.io.FileNotFoundException;   
import java.util.ArrayList; 
import java.util.Scanner;
import java.util.StringTokenizer;
 
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import models.beans.Geolocalisation; 
import play.Play;

public class Neo4jDAO 
{
	/**
	 * Holds mongodb table name.
	 */
	private static final String DYNAMIC_LABEL = "Geolocalisation";
	
	/**
	 * Node properties
	 */
	private static final String FIELD_1    = "codedep";
	private static final String FIELD_2    = "lat";
	private static final String FIELD_3    = "long";
	
	/**
	 * Default constructor
	 * @param neo4j
	 */
	public Neo4jDAO() {}
    
	/**
	 * This method returns all data from neo4j
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public ArrayList<Geolocalisation> getStoragedData()
	{
    	// GET ALL DATA FROM FILE
    	ArrayList<Geolocalisation> list = new ArrayList<Geolocalisation>();
    	
    	// GETTING GRAPH DATABASE
    	GraphDatabaseService graph = Neo4j.getGraphDataBase();
    			
        // START SNIPPET: transaction
        try ( Transaction tx = graph.beginTx())
        {
        	System.out.println("Reading data from neo4j database...");
        	
        	Iterable<Node> in = graph.getAllNodes();
            
        	Geolocalisation geo = new Geolocalisation();
        	
        	for(Node n : in)
        	{
        		geo = new Geolocalisation();
        		
        		String codedep   = (String)n.getProperty("codedep");
        		float latitude  = (Float)n.getProperty("lat");
        		float longitude = (Float)n.getProperty("long");
        		
        		geo.setCodedep(codedep);
        		geo.setLatitude(latitude);
        		geo.setLongitude(longitude);
        		
        		list.add(geo);
        	}
        	
        	// EXEC TRANSACTION
        	tx.success();
        }
        
        // SHUTTING DOWN THE GRAPH DATABASE
        Neo4j.registerShutdownHook();
        Neo4j.close();
        
		return list;
	}
	
    /**
     * Start database
     */
    public void initDataBase()
    {
    	// GET ALL DATA FROM FILE
    	ArrayList<Geolocalisation> list = read();
    	
    	if(!list.isEmpty())
    	{
    		GraphDatabaseService graph = Neo4j.getGraphDataBase();
    		
	        // START SNIPPET: transaction
	        try ( Transaction tx = graph.beginTx())
	        {
	        	System.out.println("Starting neo4j database...");
	        	for(Geolocalisation geo : list)
	        	{
		        	Node node = null;
		        	
		        	node = graph.createNode();
		        	node.addLabel(DynamicLabel.label(DYNAMIC_LABEL));
		        	node.setProperty(FIELD_1, geo.getCodedep());
		        	node.setProperty(FIELD_2, geo.getLatitude());
		        	node.setProperty(FIELD_3, geo.getLongitude());
	        	
		        	// EXEC TRANSACTION
		        	tx.success();
	        	}
	        }
	       
	        // SHUTTING DOWN THE GRAPH DATABASE
	        Neo4j.registerShutdownHook();
	        Neo4j.close();
    	}
    	else System.err.println("Not operations (on neo4jdb database)...");
    }  
    
	/**
	 * This method read commun .txt file
	 * @param filePathName
	 * @return A set of geolicalisable object.
	 */
	private ArrayList<Geolocalisation> read()
	{
		ArrayList<Geolocalisation> list = new ArrayList<Geolocalisation>();
		String filePath = Play.application().path()+"/public/data/neo4j/geolocalisation.txt";
		
		if(new File(filePath).exists())
		{
			Scanner scanner = null;
			try 
	        {
	            scanner = new Scanner(new File(filePath));
	            
	            // SKIP (HEADER) THE FIRST LINE FROM FILE
	            scanner.nextLine();
	            
	            while (scanner.hasNextLine()) 
	            {
	    			// GETTING THE FIRST LINE
	    			String line = scanner.nextLine();
	    			Geolocalisation depGeoInfo = new Geolocalisation();
	    			
	    			// CKECKS WHETHER THE LINE ISN'T EMPTY
	    			if(!line.isEmpty())
	    			{
	    				// PROVIDE LINE TOKENS
	    				StringTokenizer tokens = new StringTokenizer(line);
	    				
	    				// GETTING TOKENS
	    				depGeoInfo.setCodedep(tokens.nextToken());
	    				depGeoInfo.setLatitude(Float.parseFloat(tokens.nextToken()));
	    				depGeoInfo.setLongitude(Float.parseFloat(tokens.nextToken()));
	    				
	    				// COPYING PROPERTIES
	    				list.add(depGeoInfo);
	    			}
	            }
	        } 
	        catch (FileNotFoundException e) 
	        {
	        	System.err.println(e.getMessage());
	        }	
	        finally { scanner.close(); }
		}
		else System.err.println("This file does not exists...");
		
		return list;
	}
}
