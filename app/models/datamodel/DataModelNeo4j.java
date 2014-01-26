package models.datamodel;

import models.beans.Geolocalisation;
import models.dao.Neo4j;
import models.dao.Neo4jDAO;
import models.query.URI;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.impl.XSDFloat;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class DataModelNeo4j implements IDataModel
{
	private final String PROP_1 = "lat";
	private final String PROP_2 = "long";
	
	@Override
	public Model generate() 
	{
		// CREATION DU MODEL
		Model model = ModelFactory.createDefaultModel(); 
		
		// CREATION DE L'ALIAS (NAMESPACE)
		model.setNsPrefix("gpos", URI.gpos);
		
		// GET GRAPH HANDLER
		Neo4jDAO graph = new Neo4jDAO(Neo4j.getInstance());
		
		// CREATION DES RESOURCES PROPRIETES (PREDICATES)
		Property hasLatitude        = model.createProperty(URI.gpos ,PROP_1);
		Property hasLongitude       = model.createProperty(URI.gpos ,PROP_2);
		
		// DATA TYPE DEFINITION
		RDFDatatype floatType = XSDFloat.XSDfloat;
		
		//graph.initDataBase();
		
		// PARCOURS POUR AJOUTER TOUS LES VALEURS DE LA BD AU MODEL
		for(Geolocalisation g : graph.getAll())
		{
			// ASSURER QUE LE RDF GARDER QUE DES LITERALES NON ZERO
			if(!g.getCodedep().isEmpty())
			{
				Resource resource = model.createResource(URI.geo+"departement/"+g.getCodedep());
				
				model.add(resource, hasLatitude, String.valueOf(g.getLatitude()), floatType);
				model.add(resource, hasLongitude, String.valueOf(g.getLongitude()), floatType);
			}
		}		
		// RETURN EMPTY MODEL
		return model;
	}

}
