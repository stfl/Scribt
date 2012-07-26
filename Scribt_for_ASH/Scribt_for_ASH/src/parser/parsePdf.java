package parser;

import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class parsePdf implements parserInterface{
	
	protected String Nachname = "";
	protected String Datum = "";
	protected String Kennzahl = "";
	protected String Leistung;
	protected String inputString = "";
	protected String LeistungNeu = "";
	protected String LeistungAlt = "";
	protected float Differenz = 0;
	
	public synchronized void setString(String inputString) {
		this.inputString = inputString;
	}
	
	public void parse() throws FileNotFoundException, IOException{
	}
		
	public parsePdf(){		
	}
	
	public String getNachname() {
		return Nachname;
	}

	public String getDatum() {
		return Datum;
	}

	public String getKennzahl() {
		return Kennzahl;
	}

	public String getLeistung() {
		return Leistung;
	}
	
	public String getLeistungNeu() {
		return LeistungNeu;
	}
	
	public String getLeistungAlt() {
		return LeistungAlt;
	}
	
	public String getDifferenz() {
		if (this.Differenz == 0) return "";
		else return abs(this.Differenz).toString().replace('.', ',');
	}
	
	public String getErweiterung() {
		if (this.Differenz == 0) return "";
		else if (this.Differenz > 0) return "Erweiterung";
		else return "Reduzierung";
	}
	
	private Float abs(float diff) {
		if (diff  < 0) {
			diff *= -1;
		}
		return diff;
	}

}
