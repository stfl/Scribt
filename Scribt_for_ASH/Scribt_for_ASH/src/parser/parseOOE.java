package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

public class parseOOE extends parsePdf {
	
	private enum parse_state {
		waitName,
		searchName,
		searchLeistung,
		waitKNZ,
		searchKNZ,
		searchDate,
		
		waitLeistungAltNeu,
		searchLeistungAlt,
		searchLeistungNeu,
		
		done;
	}
	
		parse_state state = parse_state.waitName;

		public parseOOE()
		{
			super();
		}
		
		@Override
		public void parse() throws FileNotFoundException, IOException
		{
			
			try {
				BufferedReader in = new BufferedReader(new StringReader(inputString));
				String zeile = null;
				while ((zeile = in.readLine()) != null) {
					//System.out.println(state + " Gelesene Zeile: " + zeile);

					switch(this.state) {
						case waitName:
							if (zeile.equals("_ ")){
								this.state = parse_state.searchName; 
							}
						break;
						case searchName:
							if (zeile.length() > 5) {
								if (zeile.startsWith("Netzgeführte Photovoltaikanlage")){
									this.state = parse_state.waitLeistungAltNeu;
									this.Leistung = "";
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
							if (zeile.startsWith("Sehr geehrte")) {		//Nachname doch noch gefunden
								String tmp = zeile.substring(10);
								String tmpName = tmp.substring(tmp.indexOf(' ')+1).replace("!", "");
							/*	for (String title : excludeTitles){
								//	tmpName = tmpName.replace(title, "")
								} TODO Titel raus löschen
								*/
								this.Nachname = tmpName;
							}
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
								this.state = parse_state.waitKNZ;
							}
						break;
						
						case searchLeistungAlt:
							if (zeile.indexOf(" kWpeak ") != -1){
								String tmp = zeile.substring(0, zeile.indexOf(" kWpeak ")-1);
								this.LeistungAlt = tmp.substring(tmp.lastIndexOf(' ')+1);
								if (this.LeistungNeu.equals(""))
									this.state = parse_state.waitLeistungAltNeu;
								else {
									this.state = parse_state.waitKNZ;
								}
							}
						break;
						
						case searchLeistungNeu:
							if (zeile.indexOf(" kWpeak ") != -1){
								String tmp = zeile.substring(0, zeile.indexOf(" kWpeak ")-1);
								this.LeistungNeu = tmp.substring(tmp.lastIndexOf(' ')+1);
								if (this.LeistungAlt.equals(""))
									this.state = parse_state.waitLeistungAltNeu;
								else {
									this.state = parse_state.waitKNZ;
									
								}							
							}
						break;
						
						case waitKNZ:
							if (zeile.startsWith("Geschäftszeichen:")){
								this.state = parse_state.searchKNZ;
							}
						break;
						
						case searchKNZ:
								this.Kennzahl = zeile.replace(" ", "");
								this.state = parse_state.searchDate;
						break;
						case searchDate:
							if (zeile.startsWith("Linz, ")){
								this.Datum = zeile.substring(6);
								this.state = parse_state.done;
								break;
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
