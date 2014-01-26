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
	 * Neo4j singleton instance
	 */
	private static Neo4j instance = null;

	/**
	 * Neo4j graph holder
	 */
    private GraphDatabaseService graphDb = null;
    
    /**
     * Default constructor
     */
    private Neo4j() 
    {
        graphDb = null;//new GraphDatabaseFactory().newEmbeddedDatabase(NEO4J_GRAPH_PATH);
    }
    
    /**
     * Singleton implementation
     * This method return a Neo4j instance
     * @return
     */
    public static Neo4j getInstance()
    {
    	if(instance == null)
    	{
			synchronized(Neo4j.class)
			{
				if (instance == null)
				{
		    		System.out.println("Oui une instance est crée");
		    		return new Neo4j();
				}
			} 
    	}
    	System.out.println("Non une instance est crée");
    	return instance;
    }
    
    /**
     * This method the neo4j graph
     * @return
     */
    public GraphDatabaseService getGraph()
    {
    	return graphDb;
    }
    
    /**
     * This method delete all datas into neo4j database
     */
    /*private void restartDataBase()
    {
        try
        {
            FileUtils.cleanDirectory(new File(Play.application().path()+"/public/data/neo4j/graph.db"));
        }
        catch (IOException e)
        {
            throw new RuntimeException( e );
        }
    }    */
}
