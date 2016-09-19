
public class Main {
	public Main(){
		
	}
	
	public static void main (String args[]){
		String ext;
		ext=args[0].substring(args[0].lastIndexOf("."));
		
		if(!ext.equals(".txt")){
			System.out.println("Format fichier incorrect.");
		}else{
			System.out.println("Ok");
		}
		
	}
}
