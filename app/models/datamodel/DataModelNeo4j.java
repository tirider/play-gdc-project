package models.datamodel;

import models.beans.Geolocalisation;
import models.dao.Neo4jDAO;
import models.query.URIProvider;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.impl.XSDFloat;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class DataModelNeo4j implements IDataModel
{
	/**
	 * Resource predicates
	 */
	private final String PROP_1 = "lat";
	private final String PROP_2 = "long";
	
	@Override
	public Model generate() 
	{
		// GET GRAPH HANDLER
		Neo4jDAO graph = new Neo4jDAO();
		
		System.out.println("Creating Neo4j model...");

		// CREATION DU MODEL
		Model model = ModelFactory.createDefaultModel(); 
		
		// CREATION DE L'ALIAS (NAMESPACE)
		model.setNsPrefix("gpos", URIProvider.gpos);
		
		// CREATION DES RESOURCES PROPRIETES (PREDICATES)
		Property hasLatitude        = model.createProperty(URIProvider.gpos ,PROP_1);
		Property hasLongitude       = model.createProperty(URIProvider.gpos ,PROP_2);
		
		// DATA TYPE DEFINITION
		RDFDatatype floatType = XSDFloat.XSDfloat;
		
		// PARCOURS POUR AJOUTER TOUS LES VALEURS DE LA BD AU MODEL
		for(Geolocalisation g : graph.getStoragedData())
		{
			// ASSURER QUE LE RDF GARDER QUE DES LITERALES NON ZERO
			if(!g.getCodedep().isEmpty())
			{
				Resource resource = model.createResource(URIProvider.geo+"departement/"+g.getCodedep());
				
				model.add(resource, hasLatitude, String.valueOf(g.getLatitude()), floatType);
				model.add(resource, hasLongitude, String.valueOf(g.getLongitude()), floatType);
			}
		}		
		
		System.out.println("Neo4j model creation done !!!");
		
		// RETURN EMPTY MODEL
		return model;
	}

}
