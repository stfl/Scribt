package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class parseSTMK extends parsePdf {
	
	private enum parse_state {
		searchTop,
		searchBottom,
	//	name,
		date,
	//	searchLeistungAlt,
	//	searchAenderung,
		done;
	}
	
	parse_state state = parse_state.searchTop;
	

		public parseSTMK()
		{
			super();
		}
	
		@Override
		public void parse() throws FileNotFoundException, IOException
		{
			boolean aenderung = false;
				
			try {
				BufferedReader in = new BufferedReader(new StringReader(inputString));
				String zeile = null;
				while ((zeile = in.readLine()) != null) {
					//System.out.println("Gelesene Zeile: " + zeile);
					
					
					if (zeile.equals("Abänderung des Anerkennungsbescheides  ")){
						aenderung = true;
						this.Leistung = "";
					}
					if (zeile.equals("S p r u c h")) {
						state = parse_state.searchBottom;
						//System.out.println("Top finished");
					}
					
					switch(state) {
						
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
											
								*/
								
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
								}					
								state = parse_state.done;
							}
							break;
							
							case done:
								return;
					}	
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
}
