package models.datamodel;

import models.dao.Neo4j;
import models.dao.Neo4jDAO;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class DataModelNeo4j implements IDataModel
{
	
	@Override
	public Model generate() 
	{
		Neo4jDAO graph = new Neo4jDAO(Neo4j.getInstance());
		
		//TEST GRAPH INITIALISATION
		graph.initDataBase();
		
		// RETURN EMPTY MODEL
		return ModelFactory.createDefaultModel();
	}

}
