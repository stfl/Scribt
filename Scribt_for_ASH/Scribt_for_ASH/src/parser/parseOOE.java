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
	
		parse_state state = parse_state.searchName; //waitName;

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
					System.out.println(state + " Gelesene Zeile: " + zeile);

					switch(this.state) {
						case waitName:
							if (zeile.equals("_ ")){
								this.state = parse_state.searchName; 
							}
						break;
						case searchName:
							if (zeile.length() > 5) {
								String tmpName = "";
								if (zeile.startsWith("Netzgeführte Photovoltaikanlage")){
									this.state = parse_state.waitLeistungAltNeu;
									this.Leistung = "";
									break;
								}
								if (zeile.indexOf(',') != -1)
									tmpName = zeile.substring(0, zeile.indexOf(','));
								else
									tmpName = zeile;
								
								int i= 1;	//Nachname normal an zweiter Stelle
								String[] zeileArray = tmpName.split(" ",5);
								for (int j=0; j< excludeTitles.length ;j++) {
									if (zeileArray[i-1].toLowerCase().equals(excludeTitles[j]) || zeileArray[i-1].toLowerCase().equals(excludeTitles[j]+".")) {
										i++;
									}
								}
								if (zeileArray.length >= i+1) {
									if (zeileArray[i].equals("und")) i+=2;
								}
								if (zeileArray.length < i+1) {
									System.out.println("_NO_ Nachname found\n");
								} else {
									this.Nachname = zeileArray[i];
								}
								
								this.state = parse_state.waitKNZ; //searchLeistung;
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
								
								int i= 1;
								String[] zeileArray = tmpName.split(" ",5);
								for (int j=0; j< excludeTitles.length ;j++) {
									if (zeileArray[i].toLowerCase().equals(excludeTitles[j]) || zeileArray[i].toLowerCase().equals(excludeTitles[j]+".")) {
										i++;
									}
								}
								if (zeileArray.length < i+1) {
									System.out.println("_NO_ Nachname found\n");
								} else {
									if (zeileArray[i].equals("und"))
										this.Nachname = tmpName;
									else
										this.Nachname = zeileArray[i];
								}
								
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
								this.state = parse_state.done; //waitKNZ;
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
								this.state = parse_state.searchLeistung;
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
