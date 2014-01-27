package models.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class HBase 
{
	/**
	 * Configuration object
	 */
	private static Configuration conf = null;
	
	/**
	 * HBase configuration render
	 * @return
	 */
	public static Configuration getInstance()
	{
		return conf = HBaseConfiguration.create();
	}
	
	/**
	 * Clear configuration
	 */
	public void close()
	{
		conf.clear();
	}
}
