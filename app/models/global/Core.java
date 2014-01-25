package models.global;

import play.*;

import java.io.BufferedReader; 
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader; 
import java.net.HttpURLConnection; 
import java.net.URL; 
import java.util.LinkedHashMap; 

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files; 

public class Core 
{
	/**
	 * Downloads data from url given
	 * @param urlString
	 * @return
	 * @throws Exception
	 */
	public static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        if(!checkUrl(url)) {
	        	return null;
	        }
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	/**
	 * Returns cities found by user's query. This method uses a json file dump from dbpedia.
	 * @param query
	 * @return cityName, countryName
	 */
	public static String getRegionByQuery(String query)
	{
		query = query.toLowerCase();

		LinkedHashMap<String, String> resultArray = new LinkedHashMap<String, String>(); // LinkedHashMap preserves order insertion
		
		File file = Play.application().getFile("/public/json/cities_en.json");
		String resultsStr = "{\"cities\":[";
		String jsonStr;
		try {
			jsonStr = Files.toString(file, Charsets.UTF_8);
			if(jsonStr == null) {
				return null;
			}
			ObjectMapper mapper = new ObjectMapper();
			JsonNode actualObj = mapper.readTree(jsonStr);
			JsonNode results = actualObj.get("cities");
			int size = 12;
			outer:
			for (JsonNode element: results) {
				if(element.get("city").asText().toLowerCase().equals(query)) {
					if (--size > 0) {
						resultArray.put(element.get("city").asText(), element.get("country").asText());
	    		    }
					else {
						break outer;
					}
				}
			}
			outer2:
			for (JsonNode element: results) {
				if(element.get("city").asText().toLowerCase().startsWith(query)) {
					if (--size > 0) {
						if(!resultArray.containsKey(element.get("city").asText())) {
							resultArray.put(element.get("city").asText(), element.get("country").asText());
						}
	    		    }
					else {
						break outer2;
					}
				}
			}
			
			for (String key : resultArray.keySet()) {
				resultsStr += "{\"value\" : \"" + key + "\",";
				resultsStr += "\"name\" : \"" + key + ", " + resultArray.get(key) + "\"},";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		resultsStr += "]}";
		resultsStr = resultsStr.replace(",]}", "]}"); // remove last comma
		return resultsStr;
	}
	
	/**
	 * Parse integer
	 * @param string
	 * @return
	 */
	public static int parseInt(String string) {
		try {
			return Integer.parseInt(string);
		}
		catch(NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * Checks url by HTTP HEAD method
	 * @param url
	 * @return
	 */
	public static boolean checkUrl(URL url) {

        HttpURLConnection huc;
		try {
			huc = ( HttpURLConnection )  url.openConnection();
			huc.setRequestMethod("HEAD");
	        if(huc.getResponseCode() != HttpURLConnection.HTTP_OK)
	        {
	        	return false;
	        }
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
        
        return true;
	}
}
