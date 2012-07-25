package mainScribt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class parsePdf {
	
	private enum parse_state {
		waitName,
		searchName,
		searchLeistung,
		searchKNZ,
		searchDate,
		
		waitLeistungAltNeu,
		searchLeistungAlt,
		searchLeistungNeu,
		
		done;
	}
	
	private String Nachname = "";
	private String Datum = "";
	private String Kennzahl = "";
	private String Leistung;
	private String inputString = "";
	private String LeistungNeu = "";
	private String LeistungAlt = "";
	private float Differenz = 0;
	parse_state state = parse_state.waitName;
	

		public parsePdf()
		{
			super();
		}
		
		public synchronized void setString(String inputString) {
			this.inputString = inputString;
		}
		
		public void parse() throws FileNotFoundException, IOException
		{
			boolean aenderung = false;
			
			
			
			String[] excludeTitles = {"di","mag","mba","dr","ddr","mmag","ing","dipl.-ing","herr","frau"};
			
			try {
				BufferedReader in = new BufferedReader(new StringReader(inputString));
				String zeile = null;
				String zeile_old = "";
				while ((zeile = in.readLine()) != null) {
					System.out.println(state + " Gelesene Zeile: " + zeile);

					switch(state) {
						case waitName:
							if (zeile.equals("_ ")){
								this.state = parse_state.searchName; 
							}
						break;
						case searchName:
							if (zeile.length() > 5) {
								if (zeile.startsWith("Netzgeführte Photovoltaikanlage")){
									this.state = parse_state.waitLeistungAltNeu;
									aenderung = true;
									this.Leistung = "";
										//TODO Änderungen?!
									break;
								}
								if (zeile.indexOf(',') != -1)
									this.Nachname = zeile.substring(0, zeile.indexOf(','));
								else
									this.Nachname = zeile;
								this.state = parse_state.searchLeistung;
							}
						break;
						
						case waitLeistungAltNeu: {
							if (zeile.startsWith("Ursprüngliche Daten")) {
								this.state = parse_state.searchLeistungAlt;
							}
							if (zeile.startsWith("aktuelle Daten")) {
								this.state = parse_state.searchLeistungNeu;
							}
						}
						
						case searchLeistung:
							if (zeile.indexOf(" kWpeak ") != -1){
								String tmp = zeile.substring(0, zeile.indexOf(" kWpeak ")-1);
								this.Leistung = tmp.substring(tmp.lastIndexOf(' ')+1);
								this.state = parse_state.searchKNZ;
							}
						break;
						
						case searchLeistungAlt:
							if (zeile.indexOf(" kWpeak ") != -1){
								String tmp = zeile.substring(0, zeile.indexOf(" kWpeak ")-1);
								this.LeistungAlt = tmp.substring(tmp.lastIndexOf(' ')+1);
								if (this.LeistungNeu.equals(""))
									this.state = parse_state.waitLeistungAltNeu;
								else 		//TODO irgendwas funktioniert noch ned => morgen debuggen ;)
									this.state = parse_state.searchKNZ;
							}
						break;
						
						case searchLeistungNeu:
							if (zeile.indexOf(" kWpeak ") != -1){
								String tmp = zeile.substring(0, zeile.indexOf(" kWpeak ")-1);
								this.LeistungNeu = tmp.substring(tmp.lastIndexOf(' ')+1);
								if (this.LeistungAlt.equals(""))
									this.state = parse_state.waitLeistungAltNeu;
								else 
									this.state = parse_state.searchKNZ;
							}
						break;
						
						case searchKNZ:
							if (zeile.startsWith("EnRo-")){
								this.Kennzahl = zeile;
								this.state = parse_state.searchDate;
							}
						break;
						case searchDate:
							if (zeile.startsWith("Linz, ")){
								this.Datum = zeile.substring(6);
								this.state = parse_state.done;
								break;
							}
						break;
					
					}
					
					
			/*		switch(state) {
						
						case searchTop:
							if (zeile.startsWith("GZ:")) {
								this.Kennzahl = zeile.substring(4);
								//System.out.println("GZ found");
							} else if (zeile.startsWith("Ggst.:")) {
								
						/*		String partNachname = zeile.substring(7);
								
								for (int j=0; j< excludeTitles.length ;j++) {
									if (partNachname.toLowerCase().startsWith(excludeTitles[j]) {
										partNachname = partNachname.substring(excludeTitles[j].length());
										break;
									} if (partNachname.toLowerCase().startsWith(excludeTitles[j]+".")) {
										partNachname = partNachname.substring(excludeTitles[j].length()+1);
										break;
									}
								}
										TODO: Herr Frau Titel.. entfernen evntl Nachname suchen
											
								*
								
								this.Nachname = zeile.substring(7);
								//System.out.println("NN found");
								this.state = parse_state.date;
							}
							break;
							
						case date:
							//if (zeile.equals("B e s c h e i d ")){
							if (zeile.indexOf(", am ") != -1) {
								this.Datum = zeile.substring( zeile.indexOf(", am ")+5);
								//System.out.println("Date found");
								this.state = parse_state.searchBottom;
							}
							break;
							
						case searchBottom:
							if (aenderung == false) {
								if (zeile.startsWith("Engpassleistung:")) {	
									//System.out.println("Engpassleistung:".length());
									int end = zeile.indexOf("kW");
									if (end > 19) {
										this.Leistung = zeile.substring(16, end-1);
										//System.out.println("Leistung found");
										state = parse_state.done;
									}
								}
							} else {
								//System.out.println("\tsearch for Aenderung");
								Pattern MY_PATTERN = Pattern.compile("[0-9]+[,]?[0-9]*[\n ]?kWP[\n ]?+[(]an[\n ]?+Stelle[\n ]?+[0-9]+[,]?[0-9]*");
								Matcher m = MY_PATTERN.matcher(inputString);
								String s = "";
								while (m.find()) {
								    s = m.group(0);
								    System.out.println(s);
								    break;
								}
								int end = s.indexOf("kW");
								if (end >= 1) {
									this.LeistungNeu = s.substring(0, s.indexOf("kW"));
									this.Leistung = "";
									this.LeistungAlt = s.substring(s.lastIndexOf(" "));
									this.Differenz = Float.parseFloat(LeistungNeu.replace(',', '.')) - Float.parseFloat(LeistungAlt.replace(',', '.'));
								}					
								state = parse_state.done;
							}
							break;
					}*/
					
					
					if (state == parse_state.done){
						return;
					}
					zeile_old = zeile;
										
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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
