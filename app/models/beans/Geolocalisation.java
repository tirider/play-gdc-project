package models.beans;

public class Geolocalisation 
{
	private String name;
	private String codedep;    
	private float latitude; 
	private float longitude;    
	
	public Geolocalisation() 
	{
		this.name = new String("Departement");
		this.codedep  = new String("");;
		this.latitude = 0;
		this.longitude = 0;
	}

	public Geolocalisation(String name, String codedep, float latitude,
			float longitude) {
		super();
		this.name = name;
		this.codedep = codedep;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getCodedep() {
		return codedep;
	}

	public void setCodedep(String codedep) {
		this.codedep = codedep;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
