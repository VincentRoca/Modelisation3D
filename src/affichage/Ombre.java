package affichage;


import coordonnees.Modele;

public class Ombre {
	
	Modele modele;
	
	public Ombre(Modele modele){
		this.modele=modele;
	}
	
	public float[][] devientOmbre(){
		float[][] base = modele.getPoints();
		
		
		float[][] retour = modele.getPoints();
		
		for(int i=0;i<base.length;i++){			
			int dist=(int)((base[i][0]+base[i][1]+base[i][2])/Math.sqrt(2));
				retour[i][1]= base [i][1]+dist;
		}
		
		return retour;
	}
	
}
