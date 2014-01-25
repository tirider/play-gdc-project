package models.datamodel;

import models.dao.D2RQ;

import com.hp.hpl.jena.rdf.model.Model;

public class DataModelD2RQ implements IDataModel 
{
	@Override
	public Model generate() 
	{ 
		D2RQ d2rq = D2RQ.getInstance();
		
		return d2rq.getD2RQModel();
	}
}
