package models.datamodel;

import com.hp.hpl.jena.rdf.model.Model;

public interface IDataModel 
{	
	/**
	 * My job is to generate model content
	 * @return
	 */
	public Model generate();
}
