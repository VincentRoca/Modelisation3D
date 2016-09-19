import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LectureModele {

	
	public LectureModele(){
		String ligne="";
		File inputFile ;
		FileReader myReader;
		BufferedReader myBufferedReader;
		try {
			inputFile = new File("TextFileFolder/Modele.txt");
			myReader= new FileReader(inputFile);
			myBufferedReader = new BufferedReader(myReader);
			try {
				while((ligne= myBufferedReader.readLine()) !=null){
					System.out.println(ligne);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// à completer
		
	}
}
