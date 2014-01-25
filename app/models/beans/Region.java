package models.beans;

public class Region 
{
	private String country;      // dbpedia-owl:country
	private String name;         // rdfs:label
	private double latitude;     //geo:long
	private double logitude;     //geo:lat
	
	public Region() 
	{
		this.country = new String();
		this.name = new String();
		this.latitude = 0.0;
		this.logitude = 0.0;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = Double.parseDouble(latitude);
	}

	public double getLongitude() {
		return logitude;
	}

	public void setLogitude(String logitude) {
		this.logitude = Double.parseDouble(logitude);
	}
}
