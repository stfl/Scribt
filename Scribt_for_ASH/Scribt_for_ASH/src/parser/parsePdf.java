package parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

public abstract class parsePdf implements parserInterface{
	
	DecimalFormat df = new DecimalFormat("###.###");
	
	String[] excludeTitles = {"di","mag","mba","dr","ddr","mmag","ing","dipl.-ing","herr","frau"};
	
	protected String Nachname = "";
	protected String Datum = "";
	protected String Kennzahl = "";
	protected String Leistung;
	protected String inputString = "";
	protected String LeistungNeu = "";
	protected String LeistungAlt = "";
	protected String Differenz = "";
	
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
		if (this.Differenz.equals("")) {
			if (this.LeistungAlt.equals("") || this.LeistungNeu.equals(""))
				return "";
			else {
				float tmpAlt = Float.parseFloat(LeistungAlt.replace(',', '.'));
				float tmpNeu = Float.parseFloat(LeistungNeu.replace(',', '.'));
				float tmpDiff = tmpNeu-tmpAlt;
				this.Differenz = df.format(tmpDiff).toString().replace('.', ',');
			}
		}
		if (this.Differenz.equals("0"))
			return "";
		else
			return this.Differenz.replace("-", "");
	}
	
	public String getErweiterung() {
		if (this.Differenz.equals("")) return "";
		else if (this.Differenz.equals("0"))
			return "im Betrieb";
		else if (this.Differenz.charAt(0) == '-') 
			return "Verringerung";
		else 
			return "Erweiterung";
	}

}
