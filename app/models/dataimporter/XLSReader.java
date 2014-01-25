package dataimporter;
/*
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

public class XLSReader 
{
	private static final String sources = "/home/matthew/Desktop/M2/GDC/Projet/Sources/";
	private static Configuration conf = null;

    static
    {
    	conf = HBaseConfiguration.create();
    }
    
	@Override
	public Object read(File file) 
	{
		System.out.println("Reading csv file...");
		return null;
	}
	
// 
	public static String getValue(Cell cell)
	{
		switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_STRING:
	            return cell.getRichStringCellValue().getString();
	        case Cell.CELL_TYPE_NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	                return cell.getDateCellValue().toString();
	            } else {
	                return String.valueOf(Math.round(cell.getNumericCellValue()));
	            }
	        case Cell.CELL_TYPE_BOOLEAN:
	            return String.valueOf(cell.getBooleanCellValue());
	        case Cell.CELL_TYPE_FORMULA:
	            return cell.getCellFormula();
	        case Cell.CELL_TYPE_BLANK:
	        	return null;
	        default:
	            System.err.println("No Cell Type found");
		}
		return null;
	}

 
	public static HTable createTable(String tableName) throws Exception
	{
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (admin.tableExists(tableName)) {
            System.out.println("table already exists!");
            HTable table = new HTable(conf, tableName);
            return table;
	        }
	    else {
            HTableDescriptor tableDesc = new HTableDescriptor(tableName);
            admin.createTable(tableDesc);
            System.out.println("create table " + tableName + " ok.");
            HTable table = new HTable(conf, tableName);
            return table;
        }
    }
	
 
	public static void addColumnFamily(String tableName, String columnFamily) throws IOException
	{
		// ALWAYS DISABLE TABLE IN ORDER TO EDIT THE SCHEMA OF HBASE TABLE
		HBaseAdmin admin = new HBaseAdmin(conf);
		admin.disableTable(tableName);
		HColumnDescriptor cf = new HColumnDescriptor(columnFamily);
		admin.addColumn(tableName, cf);
		admin.enableTable(tableName);
	}
 
	public static Put insertToHBase(String NDepartement, String Departement, String Arrivees, String Nuitees, String tauxOccupation, String Annee)
	{
		Put dataPut = new Put(new String(NDepartement).getBytes());
		dataPut.add(new String(Annee).getBytes(), new String("Departement").getBytes(), new String(Departement).getBytes());
		dataPut.add(new String(Annee).getBytes(), new String("Arrivees").getBytes(), new String(Arrivees).getBytes());
		dataPut.add(new String(Annee).getBytes(), new String("Nuitees").getBytes(), new String(Nuitees).getBytes());
		dataPut.add(new String(Annee).getBytes(), new String("TauxOccupation").getBytes(), new String(tauxOccupation).getBytes());
		
		return dataPut;
	}

	public static void main(String[] args) throws Exception 
	{
		// ICI IMPLEMENTER LA RECUPERATION DES DONNEES DU FICHIER EXCEL
		
		// APPEL A L INTERFACE QUI VA INSERER LES DONNEES DANS MONGODB
		
		String tableStr = "Tourisme";
		HTable ht = createTable(tableStr);
		
		String[][] files = new String[][] {
			{ "tourisme-2012.xls", "2012", "17" },
			{ "tourisme-2011.xls", "2011", "15" },
			{ "tourisme-2010.xls", "2010", "15" },
			{ "tourisme-2009.xls", "2009", "15" },
			{ "tourisme-2008.xls", "2008", "15" },
			{ "tourisme-2007.xls", "2007", "15" }
		};
		
		String fileTourisme = "";
		String sheetIndex = "";
		String year = "";
		int i = 0;
		int y = 0;
		String[] values = null;
		
		for(String[] z : files) 
		{
			fileTourisme = z[0];
			year = z[1];
			sheetIndex = z[2];
			
			addColumnFamily(tableStr, year);
		
			Put dataPut = null;
			
			File file = new File(sources + fileTourisme);
			
			FileInputStream f = new FileInputStream(file);
	
			//Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(f);
	
			//Get the sheet from the workbook we need
			HSSFSheet sheet = workbook.getSheetAt(Integer.parseInt(sheetIndex));
			
			//Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = sheet.iterator();
	
			values = new String[5];
			i = 0;
			y = 0;
			
			Outer:
			while (rowIterator.hasNext()) {
			      Row row = rowIterator.next();
			      Iterator <Cell> cellIterator = row.cellIterator();
			      if(i > 3) {
				      while (cellIterator.hasNext()) {
				        Cell cell = cellIterator.next();
				        if(y == 1) {
				        	if(getValue(cell) == null || getValue(cell).equals("Département")) { // skip line when there is no data or when a second page is found with headers titles
				        		y = 0;
				        		continue Outer;
				        	}
				        	values[0] = getValue(cell);
				        }
				        else if(y == 2) {
				        	values[1] = getValue(cell);
				        }
				        else if(y == 3) {
				        	values[2] = getValue(cell);
				        }
				        else if(y == 6) {
				        	values[3] = getValue(cell);
				        }
				        else if(y == 9) {
				        	values[4] = getValue(cell);
				        }
				        y++;
				      }
				      System.out.print(values[0]+"|"+values[1]+"|"+values[2]+"|"+values[3]+"|"+values[4]+"\n");
				      dataPut = insertToHBase(values[0],values[1],values[2],values[3],values[4], year);
				      ht.put(dataPut);
			      }
			      y = 0;
			      i++;
			}
		}
		ht.close();
	}
}
*/