package controllers;
 
import java.io.ByteArrayOutputStream; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import models.beans.Department;
import models.beans.Geolocalisation;
import models.beans.Impot;
import models.beans.TourismeANT;
import models.beans.TourismeHA;
import models.datamodel.DataModelFactory;
import models.endpoint.SparqlEndpoint;
import models.global.Core;
import models.query.QueryStringProvider;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.sparqlresults;
import views.html.depttourisme;
import views.html.map;

public class Application extends Controller 
{
    /**
     * Global model holder
     */
	public static Model global = null;
	
	/**
	 * This method provide access to the welcome page
	 * @return
	 */
    public static Result index() 
    {
    	// TEMPLATE FUNCTION RENDER    	
    	return ok(index.render());
    }

    /**
     * Main actions launcher 
     * @return
     */
	public static Model launch()
	{
		// VERIFIE SI LE MODELE N EST PAS DEJA INITIALISE
		// ET DANS CE CA, ON L INITIALISE
		if(global == null)
		{
			// MODEL GENERAL
			global = ModelFactory.createDefaultModel();
			
			// RECUPERE LE MODELE DE TDB
			Model tdb = DataModelFactory.createTDBModel();
			
			// RECUPERE LE MODELE DE MONGODB
			Model mongodb = DataModelFactory.createMongoModel();
	
			// RECUPERE LE MODELE DE D2RQ
			Model d2rq = DataModelFactory.createD2RQModel();
	
			// RECUPERE LE MODELE DE HBASE
			Model hbase = DataModelFactory.createHBaseModel();
			
			// RECUPERE LE MODELE DE NEO4J
			Model neo4j = DataModelFactory.createNeo4jModel();
			
			// FUSSION DES MODELS
			global.add(tdb);
			global.add(mongodb);	
			global.add(d2rq);
			global.add(hbase);
			global.add(neo4j);
			
			// FERMETURE DES MODELES
			tdb.close();		
			mongodb.close();
			d2rq.close();
			hbase.close();
			neo4j.close();
		}
		
		// RETURN GLOBAL MODEL
		return global;
	}
	
	/**
	 * This method provide access to the map page
	 * @return
	 */
    public static Result map()
    {
    	List<Geolocalisation> departements = new ArrayList<Geolocalisation>();
        
    	// RECUPERE LA REQUETE (STRING) POUR AFFICHER LA MAP
    	String query = QueryStringProvider.queryMap;
    	
    	ResultSet results = SparqlEndpoint.queryData(launch(), query);
    	
    	String depName;
    	String depCode;
    	float Lat;
    	float Long;
    	
    	for ( ; results.hasNext() ; )
		{
    		depName = new String();
        	depCode = new String();
        	Lat 	= 0;
        	Long 	= 0;
        	
			QuerySolution qsolution = results.nextSolution();
	
			if(qsolution.contains("nomDepartement")) 
				depName = qsolution.getLiteral("nomDepartement").toString();
			if(qsolution.contains("codeDepartement"))
				depCode = qsolution.getLiteral("codeDepartement").toString();
			if(qsolution.contains("lat")) 
				Lat = qsolution.get("lat").asLiteral().getFloat();
			if(qsolution.contains("long"))
				Long = qsolution.get("long").asLiteral().getFloat();
			departements.add(new Geolocalisation(depName, depCode, Lat, Long));
		}
    	
		return ok(map.render(departements));
    }
    
    /**
     * This method provide access to the single map page with  
     * all information comming from differents storage solutions
     * @param codedep
     * @param year
     * @return
     */
    public static Result depttourisme(String codedep, String year)
    {
    	// INITIALISATION DES OBJECTS
    	Geolocalisation geolocalisation	= new Geolocalisation();
    	TourismeANT tourismeANT 		= new TourismeANT();
    	TourismeHA tourismeHA 			= new TourismeHA();
    	Impot impot						= new Impot();
    	Department departement 			= new Department();
        
    	// RECUPERE LA REQUETE (STRING) DE TOURISMES PAR DEPARTEMENT ET ANNEE
    	String query = QueryStringProvider.queryDeptTourisme;
    	query = String.format(query,year, year,codedep); 
    	
    	// DEMANDE A L END POINT D EXECUTER LA REQUETE
    	ResultSet results = SparqlEndpoint.queryData(launch(), query);
    	
    	for ( ; results.hasNext() ; )
		{
			QuerySolution qsolution = results.nextSolution();
			
			if(qsolution.contains("nomDepartement"))
				departement.setName(qsolution.getLiteral("nomDepartement").toString());
			if(qsolution.contains("label"))
				departement.setLabelGeonames(qsolution.getLiteral("label").toString());
			if(qsolution.contains("pop"))
				departement.setPopulation(qsolution.getLiteral("pop").getInt());
			if(qsolution.contains("arrivees"))
				tourismeANT.setArrivees(qsolution.getLiteral("arrivees").toString());
			if(qsolution.contains("nuitees"))
				tourismeANT.setNuitees(qsolution.getLiteral("nuitees").toString());
			if(qsolution.contains("tauxOccupation"))
				tourismeANT.setTauxOccupation(qsolution.getLiteral("tauxOccupation").toString());
			if(qsolution.contains("h0e"))
				tourismeHA.setHotel0E(qsolution.get("h0e").asLiteral().getString());
			if(qsolution.contains("h1e"))
				tourismeHA.setHotel1E(qsolution.get("h1e").asLiteral().getString());
			if(qsolution.contains("h2e"))
				tourismeHA.setHotel2E(qsolution.get("h2e").asLiteral().getString());
			if(qsolution.contains("h2e"))
				tourismeHA.setHotel2E(qsolution.get("h2e").asLiteral().getString());
			if(qsolution.contains("h3e"))
				tourismeHA.setHotel3E(qsolution.get("h3e").asLiteral().getString());
			if(qsolution.contains("h4e"))
				tourismeHA.setHotel4E(qsolution.get("h4e").asLiteral().getString());
			if(qsolution.contains("h5e"))
				tourismeHA.setHotel5E(qsolution.get("h5e").asLiteral().getString());
			if(qsolution.contains("lat"))
				geolocalisation.setLatitude(qsolution.get("lat").asLiteral().getFloat());
			if(qsolution.contains("long"))
				geolocalisation.setLongitude(qsolution.get("long").asLiteral().getFloat());
		}
    	
    	// PREPARATION DE L OBJET DEPARTEMENT ()
    	departement.setTourismeant(tourismeANT);
    	departement.setTourismeha(tourismeHA);
    	departement.setGeolocalisation(geolocalisation);
    	
    	// RECUPERE LA REQUETE (STRING) POUR CALCULER LES IMPOTS
    	String queryImpots = QueryStringProvider.queryImpots;
    	queryImpots = String.format(queryImpots,codedep, year);
    	
    	// DEMANDE A L END POINT D EXECUTER LA REQUETE
    	ResultSet resultsImpots = SparqlEndpoint.queryData(launch(), queryImpots);
    	
    	for ( ; resultsImpots.hasNext() ; )
		{
			QuerySolution qsolution = resultsImpots.nextSolution();
	
			if(qsolution.contains("moyenne_general"))
				impot.setImpots(qsolution.get("moyenne_general").asLiteral().getString());
			if(qsolution.contains("moyenne_patrimoine"))
				impot.setPatrimoine(qsolution.get("moyenne_patrimoine").asLiteral().getString());
		}
    	
    	// MISE A JOURS DE L OBJET DEPARTEMENT AVANT LE RENDRE
    	departement.setImpot(impot);
    	
		return ok(depttourisme.render(departement, year));
    }
    
    /**
     * This method handle sparql request 
     * @return
     */
    public static Result sparqlresults()
    {
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
    	String query = dynamicForm.get("query");
    	String format = null;
    	format = dynamicForm.get("format");

    	int formatInt = 0;
    	if(format == null) {
    		if(request().accepts("text/html")) {
        		formatInt = 0;
        	}
        	else if(request().accepts("application/json")) {
        		formatInt = 1;
        	}
        	else if (request().accepts("text/xml")) {
    	    	formatInt = 2;
    	    }
        	else if (request().accepts("application/rdf+xml")) {
    	    	formatInt = 3;
    	    }
        	else if (request().accepts("application/x-turtle")) {
    	    	formatInt = 4;
    	    }
    	}
    	else 
    	{
    		formatInt = Core.parseInt(format);
    	}
    	
    	// CHECKING FOR VALID QUERY
    	ResultSet results = SparqlEndpoint.queryData(launch(), query);
    	
    	if(results == null) {
    		return ok(sparqlresults.render("Error on SPARQL query"));
    	}

		switch(formatInt) {
			case 0:
				String resultsHtml = SparqlEndpoint.outputHtml(results);
				return ok(sparqlresults.render(resultsHtml));
			case 1:
				ByteArrayOutputStream baosJson = new ByteArrayOutputStream();
				ResultSetFormatter.outputAsJSON(baosJson, results);
				return ok(Json.parse(baosJson.toString()));
			case 2:
				ByteArrayOutputStream baosXml = new ByteArrayOutputStream();
				ResultSetFormatter.outputAsXML(baosXml, results);
				return ok(baosXml.toString());
			case 3:
				ByteArrayOutputStream baosRDF = new ByteArrayOutputStream();
				ResultSetFormatter.outputAsRDF(baosRDF, "RDF/XML-ABBREV", results);
				return ok(baosRDF.toString());
			case 4:
				ByteArrayOutputStream baosN3 = new ByteArrayOutputStream();
				ResultSetFormatter.outputAsRDF(baosN3, "N3", results);
				return ok(baosN3.toString());
		}
		return null;
    }
}
