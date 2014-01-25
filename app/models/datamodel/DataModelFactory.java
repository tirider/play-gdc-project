package models.datamodel;

import com.hp.hpl.jena.rdf.model.Model;

public class DataModelFactory 
{
	/**
	 * Retrieve the model generated from mongodb.
	 * @return
	 */
	public static Model createMongoModel()
	{
		// RECUPERATION D'UNE INSTANCE
		IDataModel mongo = new DataModelMongoDB();
		
		// RENDS LE MODEL GENERE
		return mongo.generate();
	}
	
	/**
	 * Retrieve the model generated from TDB.
	 * @return
	 */
	public static Model createTDBModel()
	{
		// RECUPERATION D'UNE INSTANCE
		IDataModel tdb = new DataModelTDB();
		
		// RENDS LE MODEL GENERE
		return tdb.generate();
	}
	
	/**
	 * Retrieve the model generated from D2RQ.
	 * @return
	 */
	public static Model createD2RQModel()
	{
		// RECUPERATION D'UNE INSTANCE
		IDataModel d2rq = new DataModelD2RQ();
		
		// RENDS LE MODEL GENERE
		return d2rq.generate();
	}
	
	/**
	 * Retrieve the model generated from HBase.
	 * @return
	 */
	public static Model createHBaseModel()
	{
		IDataModel hbase = new DataModelHBase();
		
		return hbase.generate();
	}
	
	/**
	 * Retrieve the model generated from Neo4j.
	 * @return
	 */
	public static DataModelNeo4j createNeo4jModel()
	{
		return null;
	}
}