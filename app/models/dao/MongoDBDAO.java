package models.dao;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import models.beans.TourismeHA;

public class MongoDBDAO
{
	/**
	 * Holds mongodb table name.
	 */
	private static final String TABLE_NAME    = "hoteldept";
	
	/**
	 * Describes current mongodb collection properties
	 */
	public static final String TABLE_FIELD_1 = "depid";
	public static final String TABLE_FIELD_2 = "h0e";	
	public static final String TABLE_FIELD_3 = "h1e";
	public static final String TABLE_FIELD_4 = "h2e";
	public static final String TABLE_FIELD_5 = "h3e";
	public static final String TABLE_FIELD_6 = "h4e";	
	public static final String TABLE_FIELD_7 = "h5e";
	public static final String TABLE_FIELD_8 = "year";

	/**
	 * Mongo database holder.
	 */
	private static MongoDB mdb;
	
	/**
	 * Constructor with parameters
	 * @param mdb
	 */
	public MongoDBDAO(MongoDB modb) { mdb = modb; }

	/**
	 *  Retrieve the collection name
	 * @return
	 */
	public String getName() { return new String(TABLE_NAME); }

	/**
	 * Retrieve rows from mongodb
	 * @return
	 */
	public List<TourismeHA> findAll() 
	{
		// USE A CONNECTION FROM THE POOL
		DB db = (DB) mdb.getConnection();
		
		System.out.println("Request connection pooling...");
		db.requestStart();
		
		// MAPPING THE RELATED COLLECTION
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// BUILD THE QUERY
		BasicDBObject searchquery = new BasicDBObject();
		
		// RESULTAT DE LA REQUETE
		DBCursor rows = collection.find(searchquery);
		
		// OBJECT RECEVEUR DE BEANS UTILISATEUR
		List<TourismeHA> deptHotelSets = new ArrayList<TourismeHA>();
		
		try
		{
			while (rows.hasNext())
			{
				// REPRESENTE UNE LIGNE DE LA COLLECTION STOCKE DANS LE RESULTSET
				DBObject row = rows.next();
				
				// BEANS UTILISATEUR
				TourismeHA deptHotel = new TourismeHA();
				
				deptHotel.setDepId(toStr(row.get(TABLE_FIELD_1)));
				deptHotel.setHotel0E(toStr(row.get(TABLE_FIELD_2)));
				deptHotel.setHotel1E(toStr(row.get(TABLE_FIELD_3)));
				deptHotel.setHotel2E(toStr(row.get(TABLE_FIELD_4)));
				deptHotel.setHotel3E(toStr(row.get(TABLE_FIELD_5)));
				deptHotel.setHotel4E(toStr(row.get(TABLE_FIELD_6)));
				deptHotel.setHotel5E(toStr(row.get(TABLE_FIELD_7)));
				deptHotel.setYear(toStr(row.get(TABLE_FIELD_8)));
				
				// WRITE ON THE LIST
				deptHotelSets.add(deptHotel);
			}
		}
		finally
		{
			System.out.println("Releasing the connection back to the pool...");
			
			// CLOSE THE CURSOR
			rows.close();
			
			// RELEASING THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
		
		return deptHotelSets;
	}
	
	/**
	 * Find objects by departement id.
	 * @param depId
	 * @return
	 */
	public TourismeHA find(int depId) 
	{
		// USE A CONNECTION FROM THE POOL
		DB db = (DB) mdb.getConnection();
		db.requestStart();
		
		// MAPPING THE RELATED COLLECTION
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// BUILD THE QUERY
		BasicDBObject searchquery = new BasicDBObject();
		searchquery.put(TABLE_FIELD_1, depId);

		// FETCH THE REQUEST
		DBObject row = collection.findOne(searchquery);
		
		// INIT USER BEANS
		TourismeHA deptHotel = new TourismeHA();
	
		try
		{
			if(row != null)
			{
				deptHotel.setDepId(toStr(row.get(TABLE_FIELD_1)));
				deptHotel.setHotel0E(toStr(row.get(TABLE_FIELD_2)));
				deptHotel.setHotel1E(toStr(row.get(TABLE_FIELD_3)));
				deptHotel.setHotel2E(toStr(row.get(TABLE_FIELD_4)));
				deptHotel.setHotel3E(toStr(row.get(TABLE_FIELD_5)));
				deptHotel.setHotel4E(toStr(row.get(TABLE_FIELD_6)));
				deptHotel.setHotel5E(toStr(row.get(TABLE_FIELD_7)));
				deptHotel.setYear(toStr(row.get(TABLE_FIELD_8)));
			}
		}
		finally
		{
			// RELEASE THE CONNECTION BACK TO THE POOL 
			db.requestDone();
		}
		
		return deptHotel;
	}

	/**
	 * Add values to mongodb collection.
	 * @param dep
	 * @return
	 */
	public boolean save(TourismeHA dep) 
	{
		// USE A CONNECTION FROM THE POOL
		DB db = (DB) mdb.getConnection();
		db.requestStart();
		
		// MAPPING THE RELATED COLLECTION
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// BUILD THE QUERY
		BasicDBObject query = new BasicDBObject();
		
		// SETTING UP JSON OBJECT
		query.put(TABLE_FIELD_1, dep.getDepId());	
		query.put(TABLE_FIELD_2, dep.getHotel0E());		
		query.put(TABLE_FIELD_3, dep.getHotel1E());
		query.put(TABLE_FIELD_4, dep.getHotel2E());
		query.put(TABLE_FIELD_5, dep.getHotel3E());
		query.put(TABLE_FIELD_6, dep.getHotel4E());
		query.put(TABLE_FIELD_7, dep.getHotel5E());
		query.put(TABLE_FIELD_8, dep.getYear());
		
		// COMMITING OPERATION 
		WriteResult  insertion = collection.insert(query);
		
		// RELEASE THE CONNECTION BACK TO THE POOL 
		db.requestDone();
		
		// CHECKING OPERATION RESULT
		if (insertion.getLastError() != null)
			return true;
		return false;
	}

	/**
	 * Remove rows by departement id.
	 * @param depId
	 * @return
	 */
	public boolean remove(int depId) 
	{
		// USE A CONNECTION FROM THE POOL
		DB db = (DB) mdb.getConnection();
		db.requestStart();
		
		// MAPPING THE RELATED COLLECTION
		DBCollection collection = db.getCollection(TABLE_NAME);
		
		// BUILD THE QUERY		
		BasicDBObject wherequery = new BasicDBObject();
		wherequery.put(TABLE_FIELD_1, depId);
		
		// COMMITING OPERATION
		WriteResult  delete = collection.remove(wherequery);
		
		// RELEASE THE CONNECTION BACK TO THE POOL 
		db.requestDone();
		
		// CHECKING OPERATION RESULT
		if(delete.getLastError() != null)
			return true;
		return false;
	}

	/**
	 * This method retrieve empty if the value comming from mongodb is null.
	 * @param object
	 * @return
	 */
	private String toStr(Object object)
	{
		if(object != null)
			return object.toString();
		return new String();
	}
}