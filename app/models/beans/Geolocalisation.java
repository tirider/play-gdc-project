package models.beans;

public class Geolocalisation 
{
	private String name;
	private String codedep;    
	private double latitude; 
	private double longitude;    
	
	public Geolocalisation() 
	{
		this.name = new String("Departement");
		this.codedep  = new String("");;
		this.latitude = 0.0;
		this.longitude = 0.0;
	}

	public String getCodedep() {
		return codedep;
	}

	public void setCodedep(String codedep) {
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
