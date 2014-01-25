package models.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import models.beans.Tourisme;

public class HBaseDAO {

	private static final String geo = "http://id.insee.fr/geo/";
	private static final String igeo = "http://rdf.insee.fr/def/geo#";
	private static final String trsm = "http://data.gouv.fr/tourism/#";
	private static Configuration conf = null;
	
	static
    {
        conf = HBaseConfiguration.create();
    }
	
	@SuppressWarnings("deprecation")
	public static List<Tourisme> getDataByTableAndColumnFamily(String tableName, String columnFamily) throws IOException
	{
		List<Tourisme> list = new ArrayList<Tourisme>();
		Tourisme t = null;
		Filter famFilter = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(columnFamily)));
	
		HTable htable = new HTable(conf, tableName);
		Scan scan = new Scan();
	    scan.setFilter(famFilter);
	    ResultScanner scanner = htable.getScanner(scan);
	    for (Result result : scanner) {
	    	t = new Tourisme();
	    	t.setCodeDepartement(Bytes.toString(result.getRow()));
	    	for (KeyValue kv : result.raw()) {
	    		if(Bytes.toString(kv.getQualifier()).equals("Arrivees")) {
	    			t.setArrivees(Bytes.toString(kv.getValue()));
	    		}
	    		if(Bytes.toString(kv.getQualifier()).equals("Nuitees")) {
	    			t.setNuitees(Bytes.toString(kv.getValue()));
	    		}
	    		if(Bytes.toString(kv.getQualifier()).equals("TauxOccupation")) {
	    			t.setTauxOccupation(Bytes.toString(kv.getValue()));
	    		}
	    	}
	    	list.add(t);
	    }
	    
	    scanner.close();
	    htable.close();
	    
		return list;
	}
	
	public Model getModel() throws Exception {
		
		List<Tourisme> list = getDataByTableAndColumnFamily("Tourisme", "2012");
		
		Model m = ModelFactory.createDefaultModel();
		
		Property tourismeProp = m.createProperty(igeo + "tourism");
		Property arriveesProp = m.createProperty(igeo + "arrivees");
		Property nuiteesProp = m.createProperty(igeo + "nuitees");
		Property tauxOccupationProp = m.createProperty(igeo + "tauxOccupation");
		Resource tourisme = m.createResource(trsm + "Tourisme");
		
		for(Tourisme t : list) {
			// Resource inseeDepartement = m.getResource(geo + "departement/" + t.getCodeDepartement()); GET RESOURCE FROM MODEL CREATED AND ATTACH TOURISM RESOURCE
			Resource inseeDepartement = m.createResource(geo + "departement/" + t.getCodeDepartement());
			Resource tourismR = m.createResource(trsm + t.getCodeDepartement());
			
			inseeDepartement.addProperty(tourismeProp, tourismR);
			tourismR.addProperty(RDF.type, tourisme);
			tourismR.addProperty(arriveesProp, t.getArrivees());
			tourismR.addProperty(nuiteesProp, t.getNuitees());
			tourismR.addProperty(tauxOccupationProp, t.getTauxOccupation());
		}
		
		return m;
	}
}
