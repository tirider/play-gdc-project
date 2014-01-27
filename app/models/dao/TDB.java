package models.dao;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.tdb.TDBFactory;

import play.Play;


public class TDB 
{
	/**
	 * Path to graph
	 */
	private static final String DIRECTORY = Play.application().path() + "/public/data/tdb";
	
	/**
	 * Holds TDB data
	 */
	private static Dataset dataset 	= null;
	
	/**
	 * Retrieve the TDB datasets
	 */
	public static Dataset getData() 
	{
		System.out.println("Getting TDB dataset...");
		
		return dataset = TDBFactory.createDataset(DIRECTORY);
	}
	
	/**
	 * Close the TDB dataset
	 */
	public static void close() 
	{ 
		System.out.println("Closing TDB dataset...");
		
		dataset.close(); 
	}
}