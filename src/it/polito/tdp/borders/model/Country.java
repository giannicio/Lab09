package it.polito.tdp.borders.model;

public class Country {
	
	@Override
	public String toString() {
		return StateNme;
	}
	public Country(int cCode, String stateAbb, String stateNme) {
		super();
		CCode = cCode;
		StateAbb = stateAbb;
		StateNme = stateNme;
	}
	private int CCode;
	private String StateAbb;
	private String StateNme;
	
	public int getCCode() {
		return CCode;
	}
	public void setCCode(int cCode) {
		CCode = cCode;
	}
	public String getStateAbb() {
		return StateAbb;
	}
	public void setStateAbb(String stateAbb) {
		StateAbb = stateAbb;
	}
	public String getStateNme() {
		return StateNme;
	}
	public void setStateNme(String stateNme) {
		StateNme = stateNme;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CCode;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (CCode != other.CCode)
			return false;
		return true;
	}
}
