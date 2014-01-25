package models.dao;

import java.io.File;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

public class TDBDatasetDAO
{
	private TDB tdb = null;
	
	/**
	 * 
	 * @param tdb
	 */
	public TDBDatasetDAO(TDB tdb) { this.tdb = tdb; }
	
	/**
	 * 
	 * @return
	 */
	public Model getModel()
	{
		// RECUPERATION DU DATASET
		Dataset ds = tdb.getDataset();
		
		// RECUPERATION D'UN MODEL NOMMME
		Model model = ds.getNamedModel("geonames");
		
		// CLOSE THE DATASET
		ds.close();
		
		// REVOIE DU MODEL
		return model;
	}
	
	/**
	 * 
	 * @param file
	 */
	public void createData(File file)
	{
		if(tdb != null)
		{
			if(file.exists())
			{
				Model model = getModel();
				
				FileManager.get().readModel(model, file.getAbsolutePath());
			}
		}
	}
}
