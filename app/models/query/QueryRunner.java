package models.query;
 
import models.beans.Region;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.sparql.engine.http.QueryExceptionHTTP;

public class QueryRunner 
{	
	private static final String SERVICE = "http://dbpedia.org/sparql";
	private static final String FIELD1  = "cityLat";
	private static final String FIELD2  = "cityLong";
	
	/**
	 * Usuful for query execution
	 * @param queryString
	 * @param regionName
	 * @return
	 */
	public static Region execute(String queryString, String regionName)
	{
		Region region = null;
		
		String query = String.format(queryString, regionName);
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService(SERVICE, query);
		ResultSet results = qexec.execSelect() ;

		for ( ; results.hasNext() ; )
		{
			region = new Region();
			
			QuerySolution qsolution = results.nextSolution() ;
		    
			Literal result = qsolution.getLiteral(FIELD1) ;
		    
			result = qsolution.getLiteral(FIELD1) ;
		    String cityLat = result.getString();
		    
			result = qsolution.getLiteral(FIELD2) ;
		    String cityLong = result.getString();		    

		    region.setName(regionName);
		    region.setLatitude(cityLat);
		    region.setLogitude(cityLong);
		}
		return  region;
	}
	
	/**
	 * This method checks whether dbpedia service is running up.
	 * @return
	 */
	public static boolean isServiceUp()
	{
		String query = "ASK { }";
		QueryExecution qexec = QueryExecutionFactory.sparqlService(SERVICE, query);
		
		try 
		{
			return qexec.execAsk() == true;
        } 
		catch (QueryExceptionHTTP e) 
		{
        	System.err.println("Sorry, dbpedia service is not working rigth now...");
        } 
		finally 
		{
        	qexec.close();
        }
		
		return false;
	}
	
	/**
	 * This method check if the given city exists
	 * @param queryString
	 * @param cityName
	 * @return
	 */
	public static boolean exists(String queryString, String cityName)
	{
		String query = String.format(queryString, cityName);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(SERVICE, query);
		
		return qexec.execAsk();
	}
}

