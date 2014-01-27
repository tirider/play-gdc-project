package models.datamodel;

import java.io.IOException;
import java.util.List;

import models.beans.TourismeANT;
import models.dao.HBaseDAO;
import models.query.URIProvider;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDF;

public class DataModelHBase implements IDataModel
{
	// RESOURCE PROPERTIES
	private static final String PROP_1 = "arrivees";
	private static final String PROP_2 = "nuitees";
	private static final String PROP_3 = "tauxOccupation";
	private static final String PROP_4 = "info";
	
	@Override
	public Model generate() 
	{
		System.out.println("Creating HBase model...");
		
		// DEFAULT MODEL RENDER
		Model model = ModelFactory.createDefaultModel();
		
		// CREATING RESOURCE PROPERTIES
		//Property tourismeProp 	= m.createProperty(URIProvider.igeo + "tourism");
		Property arriveesProp 		= model.createProperty(URIProvider.trsm + PROP_1);
		Property nuiteesProp 		= model.createProperty(URIProvider.trsm + PROP_2);
		Property tauxOccupationProp = model.createProperty(URIProvider.trsm + PROP_3);
		
		// Propriete pour lier la Resource du Departement Insee a la resource Tourisme
        Property tourismeInfoProp = model.createProperty(URIProvider.trsm + PROP_4); 
        
        // Creer la class Tourisme
        Resource TourismeClass = model.createResource(URIProvider.trsm + "Tourisme"); 
		
		String[] years = new String[] { "2007", "2008", "2009", "2010", "2011", "2012" };
		
		List<TourismeANT> list = null;
		for(String y : years)
		{
			try 
			{
				// HBaseDAO access
				list = HBaseDAO.getDataByTblColFmly("Tourisme", y);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
	
			for(TourismeANT t : list) 
			{
				Resource DepartementInseeR = model.createResource(URIProvider.geo + "departement/" + t.getCodeDepartement());
				Resource DepartementTourismeR = model.createResource(URIProvider.trsm + "departement/" + t.getCodeDepartement() + "/" + y);
				DepartementInseeR.addProperty(tourismeInfoProp, DepartementTourismeR);
				
				DepartementTourismeR.addProperty(RDF.type, TourismeClass);
				DepartementTourismeR.addProperty(arriveesProp, t.getArrivees());
				DepartementTourismeR.addProperty(nuiteesProp, t.getNuitees());
				DepartementTourismeR.addProperty(tauxOccupationProp, t.getTauxOccupation());
				DepartementTourismeR.addProperty(DC.date, y);
			}
		}
		
		System.out.println("HBase model creation done !!!");
		
		return model;		
		// RENVOIE DU MODEL TDB
		//return HBaseDAO.getModel();
	}
}