package models.query;

import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

public class URI 
{
	/**
	 * 
	 */
	public static final String owl 		= OWL.getURI();
	
	/**
	 * 
	 */
	public static final String rdf 		= RDF.getURI();
	
	/**
	 * 
	 */
	public static final String rdfs		= RDFS.getURI();
	
	/**
	 * 
	 */
	public static final String foaf 	= FOAF.getURI();
	
	/**
	 * 
	 */
	public static final String dc 		= DC.getURI();
	
	/**
	 * 
	 */
	public static final String dcterms 	= DCTerms.getURI();
	
	/**
	 * 
	 */
	public static final String xsd 		= XSD.getURI();
	
	/**
	 * 
	 */
	public static final String idemo    = "http://rdf.insee.fr/def/demo#";
	
	/**
	 * 
	 */
	public static final String geo  	= "http://id.insee.fr/geo/";
	
	/**
	 * 
	 */
	public static final String igeo 	= "http://rdf.insee.fr/def/geo#";
	
	/**
	 * 
	 */
	public static final String gpos 	= "http://www.w3.org/2003/01/geo/wgs84_pos#";
	
	/**
	 * 
	 */
	public static final String trsm 	= "http://www.tourisme.fr/";
}
