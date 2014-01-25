package models.service;

import models.beans.Region;
import models.query.QueryStringProvider;
import models.query.QueryRunner;

public class DBPediaService
{
	/**
	 * 
	 * @param regionName
	 * @return
	 */
	public static Region parse(String regionName)
	{
		// CHECKS WHETHER THE DESTINATION IS A CITY
		if(QueryRunner.exists(QueryStringProvider.ASK, regionName))
		{
			return  QueryRunner.execute(QueryStringProvider.REGION, regionName);
		}
		// CHECKS WHETHER THE DESTINATION IS A SETTLEMENT OTHERWISE
		else
		{
			return QueryRunner.execute(QueryStringProvider.REGION, regionName);
		}
	}
}