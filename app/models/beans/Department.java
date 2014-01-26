package models.beans;

public class Department {

	public String name;
	public String labelGeonames;
	public int population;
	public Geolocalisation geolocalisation;
	public TourismeANT tourismeant;
	public TourismeHA tourismeha;
	
	public Department() {
		this.population = 0;
	}
	
	public Department(String labelGeonames, int population,
			Geolocalisation geolocalisation, TourismeANT tourismeant,
			TourismeHA tourismeha) {
		super();
		this.name = name;
		this.labelGeonames = labelGeonames;
		this.population = population;
		this.geolocalisation = geolocalisation;
		this.tourismeant = tourismeant;
		this.tourismeha = tourismeha;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabelGeonames() {
		return labelGeonames;
	}

	public void setLabelGeonames(String labelGeonames) {
		this.labelGeonames = labelGeonames;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public Geolocalisation getGeolocalisation() {
		return geolocalisation;
	}

	public void setGeolocalisation(Geolocalisation geolocalisation) {
		this.geolocalisation = geolocalisation;
	}

	public TourismeANT getTourismeant() {
		return tourismeant;
	}

	public void setTourismeant(TourismeANT tourismeant) {
		this.tourismeant = tourismeant;
	}

	public TourismeHA getTourismeha() {
		return tourismeha;
	}

	public void setTourismeha(TourismeHA tourismeha) {
		this.tourismeha = tourismeha;
	}
	
	
}
