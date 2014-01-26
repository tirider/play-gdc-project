package models.dao.dataimporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;



public class TXTReader 
{
	/**
	 * This method read commun .txt file
	 * @param filePathName
	 */
	public static void reade(String filePathName)
	{
		if(new File(filePathName).exists())
		{
			Scanner scanner = null;
			ArrayList<String> objSet = new ArrayList<String>();
			
			try 
	        {
	            scanner = new Scanner(new File(filePathName));
	            
	            // SKIP (HEADER) THE FIRST LINE FROM FILE
	            scanner.nextLine();
	            
	            while (scanner.hasNextLine()) 
	            {
	    			// GETTING THE FIRST LINE
	    			String line = scanner.nextLine();
	    			
	    			// CKECKS WHETHER THE LINE ISN'T EMPTY
	    			if(!line.isEmpty())
	    			{
	    				// PROVIDE LINE TOKENS
	    				StringTokenizer tokens = new StringTokenizer(line);
	    				
	    				// GETTING TOKENS
	    				String codedep   = tokens.nextToken();
	    				String latitude  = tokens.nextToken();
	    				String longitude = tokens.nextToken();
	    				
	    				objSet.add(codedep+" "+latitude+" "+longitude);
	    			}
	            }
	        } 
	        catch (FileNotFoundException e) 
	        {
	        	System.err.println(e.getMessage());
	        }	
	        finally
	        {
	        	scanner.close();
	        }
			
			// AFFICHAGE DU CONTENU
			for(String s : objSet) System.out.println(s);
		}
		else System.err.println("This file does not exists...");
			
	}
	
	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) 
	{
		TXTReader.reade("data/depLatLong.txt");
	}

}
