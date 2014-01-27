package models.dao;
   
import org.neo4j.graphdb.GraphDatabaseService; 
import org.neo4j.graphdb.factory.GraphDatabaseFactory; 

import play.Play;

public class Neo4j 
{
	/**
	 * Neo4j data base path
	 */
	private static final String NEO4J_GRAPH_PATH = Play.application().path() + "/public/data/neo4j/graph.db";
	
	/**
	 * Neo4j graph holder
	 */
    private static GraphDatabaseService graphDb;
    
    /**
     * Default constructor
     */
    public static GraphDatabaseService getGraphDataBase() 
    {
    	System.out.println("Getting the neo4j graph database...");
    	
        return graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(NEO4J_GRAPH_PATH);
    }
    
    /**
     * Shutting down the data base usage
     */
    public static void close()
    {
    	System.out.println("Shutting down Neo4j database...");
    	
    	graphDb.shutdown();
    }
    
    /**
     * Registers a shutdown hook for the neo4j instance so that it
       shuts down nicely when the VM exits (even if you "Ctrl-C" the
       running application).
     * @param graphDb
     */
    public static void registerShutdownHook()
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
}
