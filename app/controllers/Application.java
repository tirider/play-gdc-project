package controllers;
 
import java.io.ByteArrayOutputStream; 
import java.util.Map;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import models.beans.Department;
import models.beans.Geolocalisation;
import models.beans.TourismeANT;
import models.beans.TourismeHA;
import models.datamodel.DataModelFactory;
import models.endpoint.SparqlEndpoint;
import models.global.Core;
import models.query.URI;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.sparqlresults;
import views.html.depttourisme;
import views.html.map;
import views.html.namespaceprefixes;

public class Application extends Controller 
{
	/**
	 * This method provide access to the welcome page.
	 * @param auth : Holds access to the authentication frame.
	 * @return
	 */
    public static Result index() 
    {
    	return ok(index.render());
    }

	public static Model launch()
	{
		// MODEL GENERAL
		Model global = ModelFactory.createDefaultModel();
		
//		// RECUPERER LE MODEL DE TDB
		Model tdb = DataModelFactory.createTDBModel();
//		
//		// RECUPERER LE MODEL DE MONGODB
		Model mongodb = DataModelFactory.createMongoModel();

		// RECUPERER LE MODEL DE D2RQ
		Model d2rq = DataModelFactory.createD2RQModel();

		// RECUPERER LE MODEL DE HBASE
		Model hbase = DataModelFactory.createHBaseModel();
		
		// RECUPERER LE MODEL DE NEO4J
		Model neo4j = DataModelFactory.createNeo4jModel();
		
		// FOUSSIONER DES MODELS -- EXAMPLE
		global.add(tdb);
		global.add(hbase);
		global.add(mongodb);
		global.add(d2rq);
		global.add(neo4j);
		
		// FERMETURE TOUS LES REFERENCES
		d2rq.close();
		mongodb.close();
		tdb.close();
		hbase.close();
		neo4j.close();
		//global.close();
		
		return global;
	}
	
    /**
     * This method provide access to the result page.
     * @param codeDept
     * @return
     */
    public static Result map()
    {

		return ok();
    }
    
    /**
     * This method provide access tourism department page
     * @param codeDept
     * @return
     */
    public static Result depttourisme(String codedep, String year)
    {
    	Geolocalisation depGeoInfo = new Geolocalisation();
    	TourismeANT tourismeANT = new TourismeANT();
    	TourismeHA tourismeHA = new TourismeHA();
    	Department departement = new Department();
        
    	String query = "SELECT ?nomDepartement ?label ?pop ?arrivees ?nuitees ?tauxOccupation ?date WHERE { "
	    			+ "?resourceDepartement rdf:type igeo:Departement . "
	    			+ "?resourceDepartement igeo:nom ?nomDepartement . "
	    			+ "?resourceDepartement igeo:codeDepartement ?codeDepartement . "
					+ "igeo:Departement ?p ?o . "
					+ "?o skos:definition ?label . "
					+ "?resourceDepartement trsm:info ?resourceTourismeDepartement . "
					+ "?resourceTourismeDepartement trsm:arrivees ?arrivees . "
					+ "?resourceTourismeDepartement trsm:nuitees ?nuitees . "
					+ "?resourceTourismeDepartement trsm:tauxOccupation ?tauxOccupation . "
					+ "?resourceTourismeDepartement dc:date ?date . "
					+ "OPTIONAL { "
					+ "?resourceDepartement idemo:population ?resourcePopulation . "
					+ "?resourcePopulation idemo:populationTotale ?pop . "
					+ "?resourcePopulation idemo:date ?datepop . "
					+ "FILTER (str(?datepop) = \"" + year + "-01-01\")"
					+ "} "
					+ "FILTER (lang(?label) = 'en' && ?date = \"" + year + "\" && ?codeDepartement = \"" + codedep + "\") "
					+"}";
    	
    	Model m = launch();
    	ResultSet results = SparqlEndpoint.queryData(m, query);
    	
    	for ( ; results.hasNext() ; )
		{
			QuerySolution qsolution = results.nextSolution();
	
			if(qsolution.contains("nomDepartement")) {
				departement.setName(qsolution.getLiteral("nomDepartement").toString());
			}
			if(qsolution.contains("label")) {
				departement.setLabelGeonames(qsolution.getLiteral("label").toString());
			}

			if(qsolution.contains("pop")) {
				departement.setPopulation(qsolution.getLiteral("pop").getInt());
			}
			
			if(qsolution.contains("arrivees")) {
				tourismeANT.setArrivees(qsolution.getLiteral("arrivees").toString());
			}
			
			if(qsolution.contains("nuitees")) {
				tourismeANT.setNuitees(qsolution.getLiteral("nuitees").toString());
			}
			
			if(qsolution.contains("tauxOccupation")) {
				tourismeANT.setTauxOccupation(qsolution.getLiteral("tauxOccupation").toString());
			}
		}
    	
    	departement.setTourismeant(tourismeANT);
        
		return ok(depttourisme.render(departement, year));
    }
    
    /**
     * This method handle sparql request. 
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
    	
    	Model m = launch();
    	
    	// CHECKING FOR VALID QUERY
    	ResultSet results = SparqlEndpoint.queryData(m, query);
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
    
    public static Result namespaceprefixes()
    {
		Model m = launch();
		Map<String, String> NS = m.getNsPrefixMap();
		System.out.println(m.getNsURIPrefix("geo"));
		m.close();

        return ok(namespaceprefixes.render(NS));
    }
}
