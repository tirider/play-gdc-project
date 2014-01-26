package models.beans;

public class TourismeANT 
{
	public String codeDepartement;
	public String arrivees;
	public String nuitees;
	public String tauxOccupation;
	
	public TourismeANT(String codeDepartement, String arrivees, String nuitees, String tauxOccupation) 
	{
		this.codeDepartement = codeDepartement;
		this.arrivees = arrivees;
		this.nuitees = nuitees;
		this.tauxOccupation = tauxOccupation;
	}

	public TourismeANT() {
		// TODO Auto-generated constructor stub
	}

	public String getCodeDepartement() {
		return codeDepartement;
	}

	public void setCodeDepartement(String codeDepartement) {
		this.codeDepartement = codeDepartement;
	}

	public String getArrivees() {
		return arrivees;
	}

	public void setArrivees(String arrivees) {
		this.arrivees = arrivees;
	}

	public String getNuitees() {
		return nuitees;
	}

	public void setNuitees(String nuitees) {
		this.nuitees = nuitees;
	}

	public String getTauxOccupation() {
		return tauxOccupation;
	}

	public void setTauxOccupation(String tauxOccupation) {
		this.tauxOccupation = tauxOccupation;
	};

}
