package models.datamodel;

import models.dao.HBaseDAO;

import com.hp.hpl.jena.rdf.model.Model;

public class DataModelHBase implements IDataModel
{
	@Override
	public Model generate() 
	{
		// RENVOIE DU MODEL TDB
		return HBaseDAO.getModel();
	}
}