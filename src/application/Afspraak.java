package application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Afspraak {

	public Afspraak(Bewoner bewoner, LocalDate date, LocalTime uur, String wat, int freq, String info) {
		this.bewoner = bewoner;
		this.date = date;
		this.uur = uur;
		this.wat = wat;
		this.freq = freq;
		this.info = info;
	}
	
	public void setBewoner(Bewoner bewoner) {
		this.bewoner = bewoner;
	}
	
	public Bewoner getBewoner() {
		return this.bewoner;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public void setUur(LocalTime uur) {
		this.uur = uur;
	}
	
	public LocalTime getUur() {
		return this.uur;
	}
	
	public void setWat(String wat) {
		this.wat = wat;
	}
	
	public String getWat() {
		return this.wat;
	}
	
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	public int getFreq() {
		return this.freq;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getInfo() {
		return this.info;
	}
	
	public String getGegevens() {
		return bewoner.getNaam() + " " + String.valueOf(date) + " " +  String.valueOf(uur) + " " +  wat + " " +  String.valueOf(freq) + " " +  info;
	}
	
	private Bewoner bewoner;
	private LocalDate date;
	private LocalTime uur;
	private String wat;
	private int freq;
	private String info;
	
	static ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
	
}
