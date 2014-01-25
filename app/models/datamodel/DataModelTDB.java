package models.datamodel;

import models.dao.TDB;
import models.dao.TDBDatasetDAO;

import com.hp.hpl.jena.rdf.model.Model;

public class DataModelTDB implements IDataModel
{
	@Override
	public Model generate() 
	{
		// RECUPERATION D'UNE INSTANCE DE TDB
		TDBDatasetDAO tdb = new TDBDatasetDAO(TDB.getInstance());
		
		// RENVOIE DU MODEL TDB
		return tdb.getModel();
	}
}
