package models.query;

import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class URIProvider 
{
	/**
	 * OWL Schema uniform resource identifier
	 */
	public static final String owl 		= OWL.getURI();
	
	/**
	 * RDF uniform resource identifier
	 */
	public static final String rdf 		= RDF.getURI();
	
	/**
	 * RDF Schema uniform resource identifier
	 */
	public static final String rdfs		= RDFS.getURI();
	
	/**
	 * FOAF uniform resource identifier 
	 */
	public static final String foaf 	= FOAF.getURI();
	
	/**
	 * Dublin Core uniform resource identifier 
	 */
	public static final String dc 		= DC.getURI();
	
	/**
	 * Dublin Core uniform resource identifier  
	 */
	public static final String dcterms 	= DCTerms.getURI();
	
	/**
	 * XSD uniform resource identifier
	 */
	public static final String xsd 		= "http://www.w3.org/2001/XMLSchema#";
	
	/**
	 * INSEE prop uniform resource identifier
	 */
	public static final String idemo    = "http://rdf.insee.fr/def/demo#";
	
	/**
	 * INSEE uniform resource identifier
	 */
	public static final String geo  	= "http://id.insee.fr/geo/";
	
	/**
	 * INSEE uniform resource identifier
	 */
	public static final String igeo 	= "http://rdf.insee.fr/def/geo#";
	
	/**
	 * GeoPos uniform resource identifier
	 */
	public static final String gpos 	= "http://www.w3.org/2003/01/geo/wgs84_pos#";
	
	/**
	 * Tourisme uniform resource identifier
	 */
	public static final String trsm 	= "http://www.tourisme.fr/";
	
	/**
	 * SKOS uniform resource identifier
	 */
	public static final String skos 	= "hhttp://www.w3.org/2004/02/skos/core#";	
}
