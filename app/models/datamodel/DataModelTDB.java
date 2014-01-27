package models.datamodel;

import models.dao.TDB; 
import models.query.URIProvider;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.OWL;

public class DataModelTDB implements IDataModel
{
	@Override
	public Model generate() 
	{
		// HOLDS MODELS FROM DATASET
		Model tdbmodel = ModelFactory.createDefaultModel();
		
		// RECUPERATION D'UN REFERENCE DU DATASET DE TDB
		Dataset dataset = TDB.getData();
		
		System.out.println("Creating TDB model...");
		
		// NAMING MODELS
		Model geoInsee 		= dataset.getNamedModel("geoInsee");
        Model popInsee2011 	= dataset.getNamedModel("popInsee2011");
        Model popInsee2010 	= dataset.getNamedModel("popInsee2010");
        Model geonames 		= dataset.getNamedModel("geonames");
        
    	// SET NAMESPACES
    	geoInsee.setNsPrefix("trsm", URIProvider.trsm);

        // MERGE GRAPHS
    	geoInsee.add(popInsee2011).add(popInsee2010).add(geonames);

        // ASSOCIATE REGION - ADM1
        Resource igeoRegion = geoInsee.getResource(URIProvider.igeo + "Region");
        Resource Adm1 = geonames.getResource(URIProvider.gn + "A.ADM1");
        
        geoInsee.add(igeoRegion, OWL.equivalentClass, Adm1);
        geoInsee.add(Adm1, OWL.equivalentClass, igeoRegion);
        
        // ASSOCIATE REGION - ADM2
        Resource igeoDepartement = geoInsee.getResource(URIProvider.igeo + "Departement");
        Resource Adm2 = geonames.getResource(URIProvider.gn + "A.ADM2");
        
        geoInsee.add(igeoDepartement, OWL.equivalentClass, Adm2);
        geoInsee.add(Adm2, OWL.equivalentClass, igeoDepartement);
        
        // ASSOCIATE ARRONDISSEMENT - ADM3
        Resource igeoArrondissement = geoInsee.getResource(URIProvider.igeo + "Arrondissement");
        Resource Adm3 = geonames.getResource(URIProvider.gn + "A.ADM3");
        
        geoInsee.add(igeoArrondissement, OWL.equivalentClass, Adm3);
        geoInsee.add(Adm3, OWL.equivalentClass, igeoArrondissement);
        
        // ASSOCIATE CANTON - ADM4
        Resource igeoCanton = geoInsee.getResource(URIProvider.igeo + "Canton");
        Resource Adm4 = geonames.getResource(URIProvider.gn + "A.ADM4");
        
        geoInsee.add(igeoCanton, OWL.equivalentClass, Adm4);
        geoInsee.add(Adm4, OWL.equivalentClass, igeoCanton);
        
        // ASSOCIATE COMMUNE - ADM5
        Resource igeoCommune = geoInsee.getResource(URIProvider.igeo + "Commune");
        Resource Adm5 = geonames.getResource(URIProvider.gn + "A.ADM5");
        
        geoInsee.add(igeoCommune, OWL.equivalentClass, Adm5);
        geoInsee.add(Adm5, OWL.equivalentClass, igeoCommune);
        
        // ASSOCIATE PAYS - COUNTRY (PCLI)
        Resource igeoPays = geoInsee.getResource(URIProvider.igeo + "Pays");
        Resource apcli = geonames.getResource(URIProvider.gn + "A.PCLI");
        
        geoInsee.add(igeoPays, OWL.equivalentClass, apcli);
        geoInsee.add(apcli, OWL.equivalentClass, igeoPays);
		
        tdbmodel.add(geoInsee);
        tdbmodel.add(popInsee2011);
        tdbmodel.add(popInsee2010);
        tdbmodel.add(geonames);
       
		// CLOSE ALL DATASETS REFERENCED
        TDB.close();
        
        System.out.println("TDB model creation done !!!");
        
		// REVOIE DU MODEL COMPLET
		return tdbmodel;
	}
}
