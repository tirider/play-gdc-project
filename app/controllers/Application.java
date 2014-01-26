package controllers;
 
import java.io.ByteArrayOutputStream; 

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import models.beans.Geolocalisation;
import models.datamodel.DataModelFactory;
import models.endpoint.SparqlEndpoint;
import models.global.Core;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.sparqlresults;

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
//		Model tdb = DataModelFactory.createTDBModel();
//		
//		// RECUPERER LE MODEL DE MONGODB
//		Model mongodb = DataModelFactory.createMongoModel();

		// RECUPERER LE MODEL DE D2RQ
		//Model d2rq = DataModelFactory.createD2RQModel();

		// RECUPERER LE MODEL DE HBASE
		//Model hbase = DataModelFactory.createHBaseModel();
		
		// RECUPERER LE MODEL DE NEO4J
		Model neo4j = DataModelFactory.createNeo4jModel();
		
		// RECUPERER LE MODEL DE NEO4J
		
		// FOUSSIONER DES MODELS -- EXAMPLE
//		global.add(tdb);
//		//global.add(hbase);
//		global.add(mongodb);
		global.add(neo4j);
		
		//global.add(d2rq);
		
		// GERER LES LIENS ENTRE MODELS
		
		// EXECUTER DES REQUETES SPARQL
		
		// QUERY STRING -- FIND ALL TRIEPLETS -- EXAMPLE QUERY
//		String queryString = "SELECT * WHERE { ?s ?p ?o} limit 50";
//        Query query = QueryFactory.create(queryString) ;
//        
//        // PREPARING QUERY
//        QueryExecution qexec = QueryExecutionFactory.create(query, global);
//        
//		ResultSet rs = qexec.execSelect() ;
//		ResultSetFormatter.out(System.out, rs, query);
		
		// FERMETURE TOUS LES REFERENCES
		//d2rq.close();
		//mongodb.close();
//		tdb.close();
		//global.close();
		
		return global;
	}
	
    /**
     * This method provide access to the result page.
     * @param codeDept
     * @return
     */
    public static Result results(String codeDept)
    {
        Geolocalisation depGeoInfo = new Geolocalisation();
        depGeoInfo.setCodedep(codeDept);
        
		return ok();
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
}
