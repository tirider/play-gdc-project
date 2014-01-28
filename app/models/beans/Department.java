package models.beans;

public class Department 
{
	private String name;
	private String labelGeonames;
	private int population;
	private Geolocalisation geolocalisation;
	private TourismeANT tourismeant;
	private TourismeHA tourismeha;
	private Impot impot;
	
	public Department() 
	{
		this.population 				= 0;
		String name 					= new String();
		String labelGeonames 			= new String();
		Geolocalisation geolocalisation = new Geolocalisation();
		TourismeANT tourismeant 		= new TourismeANT();
		TourismeHA tourismeha 			= new TourismeHA();
	}
	
	public Department(String labelGeonames, int population,
			Geolocalisation geolocalisation, TourismeANT tourismeant,
			TourismeHA tourismeha, Impot impot) 
	{
		super();
		this.name 			= new String();
		this.labelGeonames 	= labelGeonames;
		this.population 	= population;
		this.geolocalisation= geolocalisation;
		this.tourismeant 	= tourismeant;
		this.tourismeha 	= tourismeha;
		this.impot 	= impot;
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

	public Impot getImpot() {
		return impot;
	}

	public void setImpot(Impot impot) {
		this.impot = impot;
	}
	
	
}
