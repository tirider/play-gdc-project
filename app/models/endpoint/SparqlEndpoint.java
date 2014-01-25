package models.endpoint;

import java.io.ByteArrayOutputStream; 
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory; 
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class SparqlEndpoint {

	public static ResultSet queryData(String q)
	{
		Model taweb = null;

        // GET NAMESPACES FROM MODELS
        @SuppressWarnings("null")
		String dbpediaowlNS = taweb.getNsPrefixURI("dbpedia-owl");
    	String dbpediaNS = taweb.getNsPrefixURI("dbpedia");
    	String dbppropNS = taweb.getNsPrefixURI("dbpprop");

    	// PREFIXES
    	String prefixes = 	"PREFIX rdfs: <"+RDFS.getURI()+"> "+
    						"PREFIX rdf: <"+RDF.getURI()+"> " +
    						"PREFIX owl: <"+OWL.getURI()+"> " +
    						"PREFIX dc: <"+DC.getURI()+"> " +
    						"PREFIX foaf: <"+FOAF.getURI()+"> "+
    						"PREFIX dbpedia-owl: <"+dbpediaowlNS+"> "+
    						"PREFIX dbpedia: <"+dbpediaNS+"> " +
    						"PREFIX dbpprop: <"+dbppropNS+"> " +
    						"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";

        // SPARQL
    	String q1 = prefixes + q;

    	ResultSet results = null; 
    			
    	try {
	        Query query = QueryFactory.create(q1);
	        QueryExecution qexec = QueryExecutionFactory.create(query, taweb);
	        results = qexec.execSelect();
    	}
    	catch(Exception e) {
    		results = null;
    	}
    	finally 
    	{
    		try 
    		{
        		taweb.close();
			} 
    		catch (Exception e2) 
    		{
				System.out.println(e2.getMessage());
			}
    	}
    	return results;
	}
	
	public static String outputHtml(ResultSet results)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ResultSetFormatter.out(baos, results);
	    String resultStr = baos.toString();
	    resultStr = resultStr.replace("<", "&lt;");
	    resultStr = resultStr.replace(">", "&gt;");
	    resultStr = resultStr.replace("-", " ");
	    resultStr = resultStr.replace("=", " ");
	    resultStr = resultStr.replace("\r", "</td></tr>");
	    resultStr = resultStr.replace("\n", "<tr><td>");
	    resultStr = resultStr.replace("|</td></tr><tr><td>|", "</td></tr><tr><td>");
	    resultStr = resultStr.replace("|", "</td><td>");
	    resultStr = resultStr.trim();
	    resultStr = "<table>" + resultStr;
	    resultStr += "</table>";
	    resultStr = resultStr.replace("<table></td></tr><tr><td></td>", "<table border=\"1\"><tr>");
	    resultStr = resultStr.replace("</td></tr><tr><td></table>", "</tr></td></table>");
	    resultStr = resultStr.replace("</td><td></td></tr><tr><td>", " ");
	    resultStr = resultStr.replace("</td></tr><tr><td></td><td>", "</td></tr><tr><td>");
	    
	    return resultStr;
	}
}