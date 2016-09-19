
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
		}else if(!option.equals("-f") || !option.equals("-s")){
			System.out.println("Option incorrecte.");
		}
	}
	
	public static void main (String args[]){
		if(args.length>2 || args.length==0){
			System.out.println("Nombre d'arguments incorrect.");
		}else{
			verificationFormat(args[0]);
			if(args.length==2){
				verificationOption(args[1]);
			} 
		}
	}
}
