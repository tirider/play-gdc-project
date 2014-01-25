package models.dao;

import com.hp.hpl.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;


public class D2RQ 
{
	/**
	 * Instance class D2RQ.
	 */
	private static D2RQ instance = null;
	
	/**
	 * Model D2RQ.
	 */
	private static Model d2rqModel = null;
			
	/**
	 * Constructor D2RQ.
	 */
	private D2RQ()
	{
		d2rqModel = new ModelD2RQ("file:public/data/d2rq/mapping_manual.n3");
	}
	
	/**
	 * Constructor singleton.
	 * @return
	 */
	public static D2RQ getInstance()
	{
		if(instance == null)
			return new D2RQ();
		return instance;
	}

	/**
	 * Retrieve the D2RQ model.
	 * @return
	 */
	public Model getD2RQModel()
	{
		return d2rqModel;
	}
}
