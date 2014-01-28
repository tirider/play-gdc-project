package models.query;

import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class QueryStringProvider 
{
	/**
	 * Application prefixes
	 */
	public static final String prefixes = 	
			"PREFIX rdfs: <"+RDFS.getURI()+"> "+
			"PREFIX rdf: <"+RDF.getURI()+"> " +
			"PREFIX owl: <"+OWL.getURI()+"> " +
			"PREFIX dc: <"+DC.getURI()+"> " +
			"PREFIX foaf: <"+FOAF.getURI()+"> "+
			"PREFIX geo: <"+URIProvider.geo+">" +
			"PREFIX igeo: <http://rdf.insee.fr/def/geo#>" +
			"PREFIX skos: <http://www.w3.org/2004/02/skos/core#>" +
			"PREFIX idemo: <http://rdf.insee.fr/def/demo#>" +
			"PREFIX trsm: <http://www.tourisme.fr/>" +
			"PREFIX gpos: <" + URIProvider.gpos + ">" +
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";
	
	/**
	 * All markers query
	 */
	public static final String queryMap = 
			"SELECT ?nomDepartement ?codeDepartement ?lat ?long " +
			"WHERE { "
			+ "?resourceDepartement rdf:type igeo:Departement . "
			+ "?resourceDepartement igeo:nom ?nomDepartement . "
			+ "?resourceDepartement igeo:codeDepartement ?codeDepartement . "
			+ "?resourceDepartement gpos:lat ?lat . "
			+ "?resourceDepartement gpos:long ?long . "
			+"}";
	
	/**
	 * Simple marker query
	 */
	public static final String queryDeptTourisme = 
			"SELECT ?nomDepartement ?label ?pop ?arrivees ?nuitees ?tauxOccupation ?date ?h0e ?h1e ?h2e ?h3e ?h4e ?h5e ?lat ?long " +
			"WHERE { "
    			+ "?resourceDepartement rdf:type igeo:Departement . "
    			+ "?resourceDepartement igeo:nom ?nomDepartement . "
    			+ "?resourceDepartement igeo:codeDepartement ?codeDepartement . "
    			+ "?resourceDepartement gpos:lat ?lat . "
    			+ "?resourceDepartement gpos:long ?long . "
				+ "igeo:Departement ?p ?o . "
				+ "?o skos:definition ?label . "
				+ "?resourceDepartement trsm:info ?resourceTourismeDepartement . "
				+ "?resourceTourismeDepartement trsm:arrivees ?arrivees . "
				+ "?resourceTourismeDepartement trsm:nuitees ?nuitees . "
				+ "OPTIONAL { ?resourceTourismeDepartement trsm:h0e ?h0e } "
				+ "OPTIONAL { ?resourceTourismeDepartement trsm:h1e ?h1e } "
				+ "OPTIONAL { ?resourceTourismeDepartement trsm:h2e ?h2e } "
				+ "OPTIONAL { ?resourceTourismeDepartement trsm:h3e ?h3e } "
				+ "OPTIONAL { ?resourceTourismeDepartement trsm:h4e ?h4e } "
				+ "OPTIONAL { ?resourceTourismeDepartement trsm:h5e ?h5e } "
				+ "?resourceTourismeDepartement trsm:tauxOccupation ?tauxOccupation . "
				+ "?resourceTourismeDepartement dc:date ?date . "
				+ "OPTIONAL { "
				+ "?resourceDepartement idemo:population ?resourcePopulation . "
				+ "?resourcePopulation idemo:populationTotale ?pop . "
				+ "?resourcePopulation idemo:date ?datepop . "
				+ "FILTER (str(?datepop) = \"%s-01-01\")"
				+ "} "
				+ "FILTER (lang(?label) = 'en' && ?date = \"%s\" && ?codeDepartement = \"%s\") "
				+"}";
	
	/**
	 * Compute Impots query
	 */
	public static final String queryImpots = 
			"SELECT "
			+ "(GROUP_CONCAT(DISTINCT ?departement_code;separator='') AS ?dep_code)  "
			+ "?departement_name  "
			+ "(COUNT(?localite_code) AS ?nb_communes)  "
			+ "(AVG(?impot_localite_impotmoyen) AS ?moyenne_general) "
			+ "(AVG(?patrimoine) AS ?moyenne_patrimoine) "
			+ "WHERE  "
			+ "{ "
			+ "?impot_resource geo:impot_codeinsee ?localite_resource "
			+ "{ "
			+ "SELECT ?localite_resource ?localite_code ?cog_r_name ?departement_name ?departement_code "
			+ "WHERE  "
			+ "{  "
			+ "?cog_r_resource geo:cogr_code ?localite_resource. "
			+ "?cog_r_resource geo:cogr_nccenr ?cog_r_name. "
			+ "?localite_resource geo:localite_code ?localite_code. "
			+ "?cog_r_resource geo:cogr_departement ?departement_resource. "
			+ "?departement_resource geo:dep_nccenr ?departement_name. "
			+ "?departement_resource geo:dep_code ?departement_code. "
			+ "FILTER (str(?departement_code)='%s') "
			+ "} "
			+ "} "
			+ "?impot_resource geo:impot_impotmoyen ?impot_localite_impotmoyen. "
			+ "?impot_resource geo:impot_patrimoinem ?patrimoine ."
			+" ?impot_resource geo:impot_annee ?year "
			+" FILTER (str(?year) = \"%s\")"
			+ "} "
			+ "GROUP BY (?departement_name) "
			+ "ORDER BY DESC(?moyenne_general) ";	
}