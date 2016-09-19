import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LectureModele {

	private int nbVertex;
	private int nbFace;
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
				while(!(ligne= myBufferedReader.readLine()).equals("end_header")){
					String[] parts = ligne.split(" ");
					if(parts[0].equals("element") && parts[1].equals("vertex")){
						nbVertex=Integer.parseInt(parts[2]);
						System.out.println(nbVertex);
					}
					
					if(parts[0].equals("element") && parts[1].equals("face")){
						nbFace=Integer.parseInt(parts[2]);
						System.out.println(nbFace);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public int getNbVertex() {
		return nbVertex;
	}
	public int getNbFace() {
		return nbFace;
	}
	
	
}
