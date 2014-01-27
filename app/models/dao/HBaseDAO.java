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

import models.beans.TourismeANT;

public class HBaseDAO 
{
	private static Configuration conf = null;
	
	/**
	 * Execution just once
	 */
	static
    {
		System.out.println("Getting HBase instance...");
		
        conf = HBaseConfiguration.create();
    }
	
	/**
	 * Get data by table and column family
	 * @param tableName
	 * @param columnFamily
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static List<TourismeANT> getDataByTblColFmly(String tableName, String columnFamily) throws IOException
	{
		System.out.println("Reading data from hbase...");
		
		List<TourismeANT> list = new ArrayList<TourismeANT>();
		
		Filter famFilter = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(columnFamily)));
	
		HTable htable = new HTable(conf, tableName);
		Scan scan = new Scan();
	    scan.setFilter(famFilter);
	    ResultScanner scanner = htable.getScanner(scan);
	    
	    for (Result result : scanner) 
	    {
	    	TourismeANT t = new TourismeANT();
	    	
	    	t.setCodeDepartement(Bytes.toString(result.getRow()));
	    	
	    	for (KeyValue kv : result.raw()) 
	    	{
	    		if(Bytes.toString(kv.getQualifier()).equals("Arrivees")) 
	    		{
	    			t.setArrivees(Bytes.toString(kv.getValue()));
	    		}
	    		
	    		if(Bytes.toString(kv.getQualifier()).equals("Nuitees")) 
	    		{
	    			t.setNuitees(Bytes.toString(kv.getValue()));
	    		}
	    		
	    		if(Bytes.toString(kv.getQualifier()).equals("TauxOccupation")) 
	    		{
	    			t.setTauxOccupation(Bytes.toString(kv.getValue()));
	    		}
	    	}
	    	
	    	list.add(t);
	    }
	    
	    scanner.close();
	    htable.close();
	    
		return list;
	}
	
	/**
	 * This function create tables on hbase
	 * @param tableName
	 * @param familys
	 */
	public static void creatTable(String tableName, String[] familys)
	{
		System.out.println("This function is not yet implemented...");
	}
}
