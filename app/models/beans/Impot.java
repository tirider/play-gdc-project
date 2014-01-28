package models.beans;

public class Impot {
	public String impots;
	public String patrimoine;
	
	public Impot() {

	}
	
	public Impot(String impots, String patrimoine) {
		super();
		this.impots = impots;
		this.patrimoine = patrimoine;
	}
	
	public String getImpots() {
		return impots;
	}
	public void setImpots(String impots) {
		this.impots = impots;
	}
	public String getPatrimoine() {
		return patrimoine;
	}
	public void setPatrimoine(String patrimoine) {
		this.patrimoine = patrimoine;
	}
	
	
}
