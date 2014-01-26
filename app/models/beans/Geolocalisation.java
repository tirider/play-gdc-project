package models.beans;

public class Geolocalisation 
{
	private String name;
	private int codedep;    
	private double latitude; 
	private double longitude;    
	
	public Geolocalisation() 
	{
		this.name = new String("Departement");
		this.codedep  = 0;
		this.latitude = 0.0;
		this.longitude = 0.0;
	}

	public int getCodedep() {
		return codedep;
	}

	public void setCodedep(int codedep) {
		this.codedep = codedep;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
