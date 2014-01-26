package models.dao;

import java.io.File;
import java.io.FileNotFoundException;  
import java.io.IOException;
import java.util.ArrayList; 
import java.util.Scanner;
import java.util.StringTokenizer;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.FileUtils;

import models.beans.Geolocalisation; 
import play.Play;

public class Neo4jDAO 
{
	/**
	 * This file contains data to start neo4j database
	 */
	private static String DATA_FILE_PATH = Play.application().path()+"/public/data/neo4j/geolocalisation.txt";
	private static final String FIELD_1 = "codedep";
	private static final String FIELD_2 = "lat";
	private static final String FIELD_3 = "long";
	/**
	 * Neo4j singleton instance
	 */
	private Neo4j neo4jgraph = null;
	
	/**
	 * Constructor with params
	 * @param neo4j
	 */
	public Neo4jDAO(Neo4j neo4j)
	{
		this.neo4jgraph = neo4j;
	}
    
    /**
     * Start database
     */
    public void initDataBase()
    {
    	// GET ALL DATA FROM FILE
    	ArrayList<Geolocalisation> list = read(DATA_FILE_PATH);
    	
    	if(!list.isEmpty())
    	{
    		// GETTING GRAPH INSTANCE
	    	GraphDatabaseService graph = neo4jgraph.getGraph();
	
	    	// IN CASE ALREADY DONE
	    	clearDb();
	
	        // START SNIPPET: transaction
	        try ( Transaction tx = graph.beginTx())
	        {
	        	for(Geolocalisation geo : list)
	        	{
		        	Node node = null;
		        	
		        	node = graph.createNode();
		        	node.setProperty(FIELD_1, geo.getCodedep());
		        	node.setProperty(FIELD_2, geo.getLatitude());
		        	node.setProperty(FIELD_3, geo.getLongitude());
	        	}
	            // START SNIPPET: transaction
	            tx.success();
	        }
	        
	        // CLOSING
	        registerShutdownHook(graph);
    	}
    	else System.err.println("Not operations");
    }  
    
    /**
     * This method removes data from neo4j graph
     */
    void removeData()
    {
        try ( Transaction tx = neo4jgraph.getGraph().beginTx() )
        {
            // let's remove the data
            // node.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
            // node.delete();

            tx.success();
        }
    }    
    
    /**
     * This method delete all datas into neo4j database
     */
    private void clearDb()
    {
        try
        {
            FileUtils.deleteRecursively(new File("/public/data/neo4j/graph.db"));
        }
        catch (IOException e)
        {
            throw new RuntimeException( e );
        }
    }

    /**
     * Shut down the neo4j database
     */
    void shutDown()
    {
        System.out.println( "Shutting down database ..." );
        neo4jgraph.getGraph().shutdown();
    }
    
    /**
     * Registers a shutdown hook for the neo4j instance so that it
       shuts down nicely when the VM exits (even if you "Ctrl-C" the
       running application).
     * @param graphDb
     */
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
    
	/**
	 * This method returns all data from neo4j
	 * @return
	 */
	public ArrayList<Geolocalisation> getAll()
	{
		return null;
	}
	
	/**
	 * This function save data into neo4j graph
	 */
	public void save()
	{
		File file = new File(DATA_FILE_PATH);
		
		ArrayList<Geolocalisation> list = read(file.getPath());
		
		// AFFICHAGE DU CONTENU
		for(Geolocalisation s : list) System.out.println(s);
	}
	
	/**
	 * This method read commun .txt file
	 * @param filePathName
	 * @return A set of geolicalisable object.
	 */
	private ArrayList<Geolocalisation> read(String filePathName)
	{
		ArrayList<Geolocalisation> list = new ArrayList<Geolocalisation>();
		
		if(new File(filePathName).exists())
		{
			Scanner scanner = null;
			try 
	        {
	            scanner = new Scanner(new File(filePathName));
	            
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
	    				depGeoInfo.setCodedep(Integer.parseInt(tokens.nextToken()));
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
