package models.datamodel;

import com.hp.hpl.jena.rdf.model.Model;

public interface IDataModel 
{	
	/**
	 * Generate with the mongo database content.
	 * @return
	 */
	public Model generate();
}
