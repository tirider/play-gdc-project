package models.datamodel;

 
import models.beans.Turisme;
import models.dao.MongoDBDAO;
import models.dao.MongoDB;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType;
import com.hp.hpl.jena.datatypes.xsd.impl.XSDYearType;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
 

public class DataModelMongoDB implements IDataModel
{
	@Override
	public Model generate()
	{
		// CREATION DU MODEL
		Model model = ModelFactory.createDefaultModel(); 
		
		// CREATION DE L'ALIAS (NAMESPACE)
		String ns    = "http://www.turisme-france.fr/statistics#";
		String alias = "turisme";
		model.setNsPrefix(alias, ns);
		
		// GET DATABASE HANDLER
		MongoDBDAO mdh = new MongoDBDAO(MongoDB.getInstance());
		
		// CREATION DE RESOURCES (SUJETS)
		Resource resourceTableName = model.createResource(ns+mdh.getName());
		
		// CREATION DES RESOURCES PROPRIETES (PREDICATES)
		Property hasDepartementId  = model.createProperty(ns,MongoDBDAO.TABLE_FIELD_1);
		Property hasHotel0e        = model.createProperty(ns,MongoDBDAO.TABLE_FIELD_2);
		Property hasHotel1e        = model.createProperty(ns,MongoDBDAO.TABLE_FIELD_3);
		Property hasHotel2e        = model.createProperty(ns,MongoDBDAO.TABLE_FIELD_4);
		Property hasHotel3e        = model.createProperty(ns,MongoDBDAO.TABLE_FIELD_5);
		Property hasHotel4e        = model.createProperty(ns,MongoDBDAO.TABLE_FIELD_6);
		Property hasHotel5e        = model.createProperty(ns,MongoDBDAO.TABLE_FIELD_7);
		Property hasYear           = model.createProperty(ns,MongoDBDAO.TABLE_FIELD_8);
	
		// CREATION D'OBJECTS
		model.add(resourceTableName,RDF.type, RDFS.Class);
		
		// DATA TYPE DEFINITION
		RDFDatatype numericType = XSDBaseNumericType.XSDbase64Binary;
		RDFDatatype yearType = XSDYearType.XSDgYear;
		
		// PARCOURS POUR AJOUTER TOUS LES VALEURS DE LA BD AU MODEL
		for(Turisme dh : mdh.findAll())
		{
			// ASSURER QUE LE RDF GARDER QUE DES LITERALES NON ZERO
			if(!dh.getDepId().isEmpty())
				model.add(resourceTableName, hasDepartementId, dh.getDepId(), numericType);
			if(!dh.getHotel0E().isEmpty())
				model.add(resourceTableName, hasHotel0e, dh.getHotel0E(), numericType);
			if(!dh.getHotel1E().isEmpty())
				model.add(resourceTableName, hasHotel1e, dh.getHotel1E(), numericType);
			if(!dh.getHotel2E().isEmpty())
				model.add(resourceTableName, hasHotel2e, dh.getHotel2E(), numericType);
			if(!dh.getHotel3E().isEmpty())
				model.add(resourceTableName, hasHotel3e, dh.getHotel3E(), numericType);
			if(!dh.getHotel4E().isEmpty())
				model.add(resourceTableName, hasHotel4e, dh.getHotel4E(), numericType);
			if(!dh.getHotel5E().isEmpty())
				model.add(resourceTableName, hasHotel5e, dh.getHotel5E(), numericType);
			if(!dh.getYear().isEmpty())
				model.add(resourceTableName, hasYear, dh.getYear(), yearType);
		}		
		
		return model;
	}
}
