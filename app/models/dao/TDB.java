package models.dao;

import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.query.*;

public class TDB 
{
	public static final String DIRECTORY = "data/tdb";
	
	private static TDB instance = null;
	private Dataset dataset = null;
	
	/**
	 * Constructor TDBDataset.
	 */
	private TDB() 
	{
		dataset = TDBFactory.createDataset(DIRECTORY);
	}
	
	/**
	 * Define a singleton instance.
	 * @return
	 */
	public static TDB getInstance() 
	{
		if(instance == null) 
		{
			instance = new TDB();
		}
		return instance;
	}
	
	/**
	 * Retrieve the TDB datasets.
	 * @return
	 */
	public Dataset getDataset() 
	{
		return dataset;
	}
	
	/**
	 * close the dataset.
	 */
	public void close()
	{
		dataset.close();
	}
}