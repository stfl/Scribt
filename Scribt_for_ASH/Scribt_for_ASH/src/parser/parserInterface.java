package parser;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface parserInterface {

	public void setString(String inputString);
	
	public void parse() throws FileNotFoundException, IOException;
			
	public String getNachname();

	public String getDatum();

	public String getKennzahl();

	public String getLeistung();
	
	public String getLeistungNeu();
	
	public String getLeistungAlt();
	
	public String getDifferenz();
	
	public String getErweiterung();
	
}
