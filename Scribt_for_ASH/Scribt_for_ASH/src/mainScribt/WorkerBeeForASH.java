package mainScribt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WorkerBeeForASH {
	
	String textContent = "";
	String filenamePDF = "U:/Ökostromdecklung - OSD Team Liste/ASH_files//Gemeinde Weistrach_2- 10.10.2011.pdf";
	
	parsePdf parser;
	PDFTextParser pdfTextParserObj;
	String result;
	
	public WorkerBeeForASH() {
		super();
		work();
	}
	
	public void work() {
		
		findFiles();
		
		convertPdfToTxt();
		
		parser = new parsePdf();
		parser.setString(textContent);
		try {
			parser.parse();
			System.out.println(filenamePDF + "\n" +
					"Datum: " + parser.getDatum() + "\t" + 
					"Kennzahl: " + parser.getKennzahl() + "\t" + 
					"Leistung: " + parser.getLeistung() + " kWp\t" + 
					"Nachname: " + parser.getNachname() + "\t");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = parser.getNachname() + ";" + 
    			parser.getLeistung() + ";" + 
    			parser.getKennzahl() + ";" + 
    			parser.getDatum();
		pdfTextParserObj.writeTexttoFile(result, "C:/ASH_temp//result.csv");
	}
	
	public void convertPdfToTxt() {
		
        pdfTextParserObj = new PDFTextParser();
        textContent = pdfTextParserObj.pdftoText(filenamePDF);
        
        if (textContent == null) {
        	System.out.println("PDF to Text Conversion failed.");
        }
        else {
//        	System.out.println("\nThe text parsed from the PDF Document....\n" + textContent);
//        	pdfTextParserObj.writeTexttoFile(pdfToText, arguments[1]);
        	
        	
        }
    }
	
	private void findFiles() {
		
		File f;
        
		File folder = new File("U:/Ökostromdecklung - OSD Team Liste/ASH_files");
        File[] listOfFiles = folder.listFiles();
        
        int j = 0;
        for (int i = 0; i < listOfFiles.length; i++) {
        	if (listOfFiles[i].getName().toLowerCase().endsWith("pdf")) {
        		System.out.println(listOfFiles[i]);
        	}
        }
        
	}
	
}
