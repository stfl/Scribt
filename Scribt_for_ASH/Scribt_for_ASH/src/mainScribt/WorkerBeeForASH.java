package mainScribt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WorkerBeeForASH {
	
	String textContent = "";
	String pathToFiles = "U:/Ökostromdecklung - OSD Team Liste/ASH_files";
	String filenamePDF = "U:/Ökostromdecklung - OSD Team Liste/ASH_files//Anschreiben_6.pdf";
	
	parsePdf parser;
	PDFTextParser pdfTextParserObj;
	String result;
	
	File[] listOfFiles;
	
	public WorkerBeeForASH() {
		super();
		work();
	}
	
	public void work() {
		
		findFiles(pathToFiles);
		
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].getName().toLowerCase().endsWith("pdf")) {
				
				convertPdfToTxt(listOfFiles[i].getName());
				
				parser = new parsePdf();
				parser.setString(textContent);
				try {
					parser.parse();
					System.out.println(listOfFiles[i] + "\n" +
							"Datum: " + parser.getDatum() + "\t" + 
							"Kennzahl: " + parser.getKennzahl() + "\t" + 
							"Leistung: " + parser.getLeistung() + " kWp\t" + 
							"Nachname: " + parser.getNachname() + "\t\n");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result += parser.getNachname() + ";" + 
		    			parser.getLeistung() + ";" + 
		    			parser.getKennzahl() + ";" + 
		    			parser.getDatum() + "\n";
				pdfTextParserObj.writeTexttoFile(result, "C:/ASH_temp//result.csv");
			}
		}
	}
	
	public void convertPdfToTxt(String filenamePDF) {
		
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
	
	/**
	 * read all files in a folder
	 */
	private void findFiles(String path) {
		
//		File folder = new File("U:/Ökostromdecklung - OSD Team Liste/ASH_files");
		File folder = new File(path);
        listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
        	if (listOfFiles[i].getName().toLowerCase().endsWith("pdf")) {
        		System.out.println(listOfFiles[i]);
        	}
        }
        
	}
	
}
