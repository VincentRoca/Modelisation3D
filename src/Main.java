
public class Main {
	public Main(){
		
	}
	
	public static void verificationFormat(String fichier){
		String ext=fichier.substring(fichier.lastIndexOf("."));
		
		if(!ext.equals(".txt")){
			System.out.println("Format fichier incorrect.");
		}else{
			System.out.println("Format Ok");
		}
	}
	
	public static void verificationOption(String option){
		if(option.equals("-f")){
			System.out.println("Affichage des faces uniquement.");
		}else if(option.equals("-s")){
			System.out.println("Affichage des segments uniquement.");
		}
	}
	
	public static void main (String args[]){
		verificationFormat(args[0]);
		verificationOption(args[1]);
		
		
	}
}
