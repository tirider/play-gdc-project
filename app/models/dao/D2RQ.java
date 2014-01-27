package models.dao;

import play.Play;

import com.hp.hpl.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class D2RQ 
{
	/**
	 * Hols the d2rq mapping file path
	 */
	private static final String MAPPING_FILE_PATH = Play.application().path() + "/conf/d2rq-mapping.n3";
	
	/**
	 * Model D2RQ.
	 */
	private static Model d2rqModel = null;
			
	/**
	 * Getting d2rq database model (ready to mapping)
	 * @return
	 */
	public static Model getModel()
	{
		System.out.println("Getting D2RQ model...");
		
		return d2rqModel = new ModelD2RQ(MAPPING_FILE_PATH);
	}
	
	/**
	 * Close model
	 */
	public void close()
	{
		d2rqModel.close();
	}
}
