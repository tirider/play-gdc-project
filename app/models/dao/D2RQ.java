package models.dao;

import com.hp.hpl.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class D2RQ 
{
	/**
	 * Hols the d2rq mapping file path
	 */
	private static final String MAPPING_PATH  = "/app/models/dao/d2rq-mapping.n3";
	
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
		//File file = Play.current().getFile(MAPPING_FILE_PATH);
		
		d2rqModel = new ModelD2RQ(MAPPING_PATH);
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
