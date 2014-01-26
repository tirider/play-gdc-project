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
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import models.beans.Geolocalisation; 
import play.Play;

public class Neo4jDAO 
{
	/**
	 * This file contains data to start neo4j database
	 */
	private static String DATA_FILE_PATH = Play.application().path()+"/public/data/neo4j/geolocalisation.txt";
	
	private static final String NEO4J_GRAPH_PATH = Play.application().path() + "/public/data/neo4j/graph.db";
	
	/**
	 * Holds mongodb table name.
	 */
	private static final String DYNAMIC_LABEL = "Geolocalisation";
	
	private static final String FIELD_1    = "codedep";
	private static final String FIELD_2    = "lat";
	private static final String FIELD_3    = "long";
	
	/**
	 * Neo4j singleton instance
	 */
	private Neo4j neo4jgraph ;
	
	private static GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(NEO4J_GRAPH_PATH);

	/**
	 * Constructor with params
	 * @param neo4j
	 */
	public Neo4jDAO(Neo4j neo4j)
	{
		this.neo4jgraph = neo4j;
	}
    
	/**
	 * This method returns all data from neo4j
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public ArrayList<Geolocalisation> getAll()
	{
    	// GET ALL DATA FROM FILE
    	ArrayList<Geolocalisation> list = new ArrayList<Geolocalisation>();
    			
    	GraphDatabaseService graph = graphDb;
    			
        // START SNIPPET: transaction
        try ( Transaction tx = graph.beginTx())
        {
        	System.out.println("Reading data from neo4j database...");
        	
        	Iterable<Node> in = graph.getAllNodes();
        	// for (Node n : GlobalGraphOperations.at(db).getAllNodes()) 
            // ExecutionEngine engine = new ExecutionEngine( db );
            // ExecutionResult result;
            
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
        
        registerShutdownHook(graph);	
        
		return list;
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
    		GraphDatabaseService graph = graphDb;
    		
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
	       
	        registerShutdownHook(graph);
    	}
    	else System.err.println("Not operations (on neo4jdb database)...");
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
