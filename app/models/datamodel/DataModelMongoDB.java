package models.datamodel;

 
import models.beans.TourismeHA;
import models.dao.MongoDBDAO;
import models.dao.MongoDB;
import models.query.URI;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseNumericType;
import com.hp.hpl.jena.datatypes.xsd.impl.XSDBaseStringType;
import com.hp.hpl.jena.datatypes.xsd.impl.XSDYearType;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
 

public class DataModelMongoDB implements IDataModel
{
	private final String PROP_1 = "info";
	
	@Override
	public Model generate()
	{
		// CREATION DU MODEL
		Model model = ModelFactory.createDefaultModel(); 
		
		// CREATION DE L'ALIAS (NAMESPACE)
		model.setNsPrefix("trsm", URI.trsm);
		
		// GET DATABASE HANDLER
		MongoDBDAO mdh = new MongoDBDAO(MongoDB.getInstance());
		
		// CREATION DES RESOURCES PROPRIETES (PREDICATES)
		Property hasHotel0e        = model.createProperty(URI.trsm,MongoDBDAO.TABLE_FIELD_2);
		Property hasHotel1e        = model.createProperty(URI.trsm,MongoDBDAO.TABLE_FIELD_3);
		Property hasHotel2e        = model.createProperty(URI.trsm,MongoDBDAO.TABLE_FIELD_4);
		Property hasHotel3e        = model.createProperty(URI.trsm,MongoDBDAO.TABLE_FIELD_5);
		Property hasHotel4e        = model.createProperty(URI.trsm,MongoDBDAO.TABLE_FIELD_6);
		Property hasHotel5e        = model.createProperty(URI.trsm,MongoDBDAO.TABLE_FIELD_7);
		Property hasYear           = model.createProperty(URI.trsm,MongoDBDAO.TABLE_FIELD_8);
		Property trsmInfo 		   = model.createProperty(URI.trsm,PROP_1);
	
		// DATA TYPE DEFINITION
		RDFDatatype stringType = XSDBaseStringType.XSDstring;
		RDFDatatype yearType = XSDYearType.XSDgYear;
		
		// PARCOURS POUR AJOUTER TOUS LES VALEURS DE LA BD AU MODEL
		for(TourismeHA dh : mdh.findAll())
		{
			// ASSURER QUE LE RDF GARDER QUE DES LITERALES NON ZERO
			if(!dh.getDepId().isEmpty())
			{
				// ON DIT QUE CHAQUE ID DE DEP.+L'ANNEE DANS TOURISME SONT DES RESOURCES
				Resource resourceInsee  = model.createResource(URI.geo+"departement/"+dh.getDepId());
				Resource resourceTurisme = model.createResource(URI.trsm+"departement/"+dh.getDepId()+"/"+dh.getYear());
				
				// ADD TRIPLET INSEE REL TRSM
				model.add(resourceInsee, trsmInfo, resourceTurisme);
				
				if(!dh.getHotel0E().isEmpty())
					model.add(resourceTurisme, hasHotel0e, dh.getHotel0E(), stringType);
				if(!dh.getHotel1E().isEmpty())
					model.add(resourceTurisme, hasHotel1e, dh.getHotel1E(), stringType);
				if(!dh.getHotel2E().isEmpty())
					model.add(resourceTurisme, hasHotel2e, dh.getHotel2E(), stringType);
				if(!dh.getHotel3E().isEmpty())
					model.add(resourceTurisme, hasHotel3e, dh.getHotel3E(), stringType);
				if(!dh.getHotel4E().isEmpty())
					model.add(resourceTurisme, hasHotel4e, dh.getHotel4E(), stringType);
				if(!dh.getHotel5E().isEmpty())
					model.add(resourceTurisme, hasHotel5e, dh.getHotel5E(), stringType);
				if(!dh.getYear().isEmpty())
					model.add(resourceTurisme, hasYear,dh.getYear(), yearType);
			}
		}		
		
		return model;
	}
}
