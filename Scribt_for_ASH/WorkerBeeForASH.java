package mainScribt;

import java.io.FileNotFoundException;
import java.io.IOException;

public class WorkerBeeForASH {
	
	String textContent = "";
	String filenamePDF = "U:/Ökostromdecklung - OSD Team Liste/Scribt_for_ASH/Scribt_for_ASH/files//Anschreiben.pdf";
	
	parsePdf parser;
	
	public WorkerBeeForASH() {
		super();
		work();
	}
	
	public void work() {
		
		convertPdfToTxt();
		
		try {
			//parser = new parsePdf("U:/Ökostromdecklung - OSD Team Liste/Scribt_for_ASH/Scribt_for_ASH/files//test.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void convertPdfToTxt() {
		
        PDFTextParser pdfTextParserObj = new PDFTextParser();
        textContent = pdfTextParserObj.pdftoText(filenamePDF);
        
        if (textContent == null) {
        	System.out.println("PDF to Text Conversion failed.");
        }
        else {
//        	System.out.println("\nThe text parsed from the PDF Document....\n" + textContent);
//        	pdfTextParserObj.writeTexttoFile(pdfToText, arguments[1]);
        }
    }
	
}
