package models.dao;

import java.io.File;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;

public class TDBDatasetDAO
{
	private TDB tdb = null;
	private static final String trsm = "http://www.tourisme.fr/";
	
	/**
	 * 
	 * @param tdb
	 */
	public TDBDatasetDAO(TDB tdb) { this.tdb = tdb; }
	
	/**
	 * 
	 * @return
	 */
	public Model getModel()
	{
		// RECUPERATION DU DATASET
		Dataset ds = tdb.getDataset();
		
		// GET ALL MODELS FROM DATASET
		Model geoInsee = ds.getNamedModel("geoInsee");
        Model popInsee = ds.getNamedModel("popInsee");
        Model geonames = ds.getNamedModel("geonames");

        // GET NAMESPACES FROM MODELS
    	String igeoNS = geoInsee.getNsPrefixURI("igeo");
    	String geoNS = geoInsee.getNsPrefixURI("geo");
    	String skosNS = geonames.getNsPrefixURI("skos");
    	String gnNS = geonames.getNsPrefixURI("gn");
    	String dcNS = geonames.getNsPrefixURI("dc");
    	String foafNS = geonames.getNsPrefixURI("foaf");
    	String voafNS = geonames.getNsPrefixURI("voaf");
    	String vannNS = geonames.getNsPrefixURI("vann");
    	String ccNS = geonames.getNsPrefixURI("cc");
    	
    	// SET NAMESPACES
    	geoInsee.setNsPrefix("trsm", trsm);

        // MERGE GRAPHS
    	geoInsee.add(popInsee).add(geonames);

        // ASSOCIATE REGION - ADM1
        Resource igeoRegion = geoInsee.getResource(igeoNS + "Region");
        Resource Adm1 = geonames.getResource(gnNS + "A.ADM1");
        
        geoInsee.add(igeoRegion, OWL.equivalentClass, Adm1);
        geoInsee.add(Adm1, OWL.equivalentClass, igeoRegion);
        
        // ASSOCIATE REGION - ADM2
        Resource igeoDepartement = geoInsee.getResource(igeoNS + "Departement");
        Resource Adm2 = geonames.getResource(gnNS + "A.ADM2");
        
        geoInsee.add(igeoDepartement, OWL.equivalentClass, Adm2);
        geoInsee.add(Adm2, OWL.equivalentClass, igeoDepartement);
        
        // ASSOCIATE ARRONDISSEMENT - ADM3
        Resource igeoArrondissement = geoInsee.getResource(igeoNS + "Arrondissement");
        Resource Adm3 = geonames.getResource(gnNS + "A.ADM3");
        
        geoInsee.add(igeoArrondissement, OWL.equivalentClass, Adm3);
        geoInsee.add(Adm3, OWL.equivalentClass, igeoArrondissement);
        
        // ASSOCIATE CANTON - ADM4
        Resource igeoCanton = geoInsee.getResource(igeoNS + "Canton");
        Resource Adm4 = geonames.getResource(gnNS + "A.ADM4");
        
        geoInsee.add(igeoCanton, OWL.equivalentClass, Adm4);
        geoInsee.add(Adm4, OWL.equivalentClass, igeoCanton);
        
        // ASSOCIATE COMMUNE - ADM5
        Resource igeoCommune = geoInsee.getResource(igeoNS + "Commune");
        Resource Adm5 = geonames.getResource(gnNS + "A.ADM5");
        
        geoInsee.add(igeoCommune, OWL.equivalentClass, Adm5);
        geoInsee.add(Adm5, OWL.equivalentClass, igeoCommune);
        
        // ASSOCIATE PAYS - COUNTRY (PCLI)
        Resource igeoPays = geoInsee.getResource(igeoNS + "Pays");
        Resource apcli = geonames.getResource(gnNS + "A.PCLI");
        
        geoInsee.add(igeoPays, OWL.equivalentClass, apcli);
        geoInsee.add(apcli, OWL.equivalentClass, igeoPays);
        
//        Resource DepartementR = geoInsee.getResource(igeoNS + "Departement"); // Class Resource
//        Property tourismeProp = geoInsee.createProperty(trsm + "info"); // Propriete pour lier la Resource du Departement Insee a la resource Tourisme
//        Property codeDepartementProp = geoInsee.getProperty(igeoNS + "codeDepartement"); // Recuperer le code de departement pour creer la resource de Tourisme
//        Resource TourismeClass = geoInsee.createResource(igeoNS + "Tourisme"); // Creer la class Tourisme
//        
//        // AJOUTER DANS CHAQUE DEPARTEMENT LA PROPRIETE AVEC LA RESOURCE DE TOURISME
//		ResIterator res_i = geoInsee.listSubjectsWithProperty(RDF.type, DepartementR);
//		while (res_i.hasNext())
//		{
//			Resource r = res_i.nextResource();
//			Resource DepartementTourismeR = geoInsee.createResource(trsm + "departement/" + r.getProperty(codeDepartementProp).getLiteral());
//			r.addProperty(tourismeProp, DepartementTourismeR);
//			DepartementTourismeR.addProperty(RDF.type, TourismeClass);
//		}
		
		// CLOSE THE DATASET
		//ds.close();
		
		// REVOIE DU MODEL COMPLET
		return geoInsee;
	}
	
	/**
	 * 
	 * @param file
	 */
	public void createData(File file)
	{
		if(tdb != null)
		{
			if(file.exists())
			{
				Model model = getModel();
				
				FileManager.get().readModel(model, file.getAbsolutePath());
			}
		}
	}
}
