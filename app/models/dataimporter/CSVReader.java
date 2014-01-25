package dataimporter;

import java.io.File;

public class CSVReader 
{
	private String filePath;

	/**
	 * Constructor dealing with csv files.
	 * @param file
	 */
	public CSVReader(String file)
	{
		if(new File(file).exists())
			this.filePath = file;
		else
			this.filePath = new String();
	}
	
	/**
	 * Reaf a csv file to import into mongodb.
	 * @param file
	 * @return
	 */
	public Object read(File file) 
	{
		if(!filePath.equals(""))
		{
			System.out.println("I found another way to do this...");
			
			return new Object();
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		// HELPER FUNCTION HERE
	}
}
