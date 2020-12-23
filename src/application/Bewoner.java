package application;


public class Bewoner {

	public Bewoner(String naam, int kamer, int afdeling, boolean bad, String baddag, boolean flat, boolean ophalen, String tel) {
		this.naam = naam;
		this.kamer = kamer;
		this.afdeling = afdeling;
		this.bad = bad;
		this.baddag = baddag;
		this.flat = flat;
		this.ophalen = ophalen;
		this.tel = tel;
	}
	
	public void setNaam(String naam) {
		this.naam = naam;
	}
	
	public String getNaam() {
		return this.naam;
	}
	
	public void setKamer(int kamer) {
		this.kamer = kamer;
	}
	
	public int getKamer() {
		return this.kamer;
	}
	
	public void setAfdeling(int afdeling) {
		this.afdeling = afdeling;
	}
	
	public int getAfdeling() {
		return this.afdeling;
	}
	
	public void setBad(boolean bad) {
		this.bad = bad;
	}
	
	public boolean getBad() {
		return this.bad;
	}
	
	public void setBaddag(String baddag) {
		this.baddag = baddag;
	}
	
	public String getBaddag() {
		return this.baddag;
	}
	
	public void setFlat(boolean flat) {
		this.flat = flat;
	}
	
	public boolean getFlat() {
		return this.flat;
	}
	
	public void setOphalen(boolean ophalen) {
		this.ophalen = ophalen;
	}
	
	public boolean getOphalen() {
		return this.ophalen;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getTel() {
		return this.tel;
	}
	
	public String getGegevens() {
		return naam + " " + String.valueOf(kamer) + " " + String.valueOf(afdeling) + " " + String.valueOf(bad) + " " + baddag + " " + String.valueOf(flat) + " " + String.valueOf(ophalen) + " " + tel;
	}
	
	private String naam;
	private int kamer;
	private int afdeling;
	private boolean bad;
	private String baddag;
	private boolean flat;
	private boolean ophalen;
	private String tel;

}
