package models.datamodel;

import models.dao.D2RQ;

import com.hp.hpl.jena.rdf.model.Model; 

public class DataModelD2RQ implements IDataModel 
{
	@Override
	public Model generate() 
	{ 
		System.out.println("Request d2rq model...");
		
		// REQUESTING MODEL
		Model model = D2RQ.getModel();
		
		System.out.println("D2RQ model creation done !!!");
		
		return model;
	}
}
