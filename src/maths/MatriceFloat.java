package maths;

public class MatriceFloat {

	private float[][] matrice;
	
	public MatriceFloat(float[][] matrice) {
		this.matrice=matrice;
	}
	
	/**
	 * Effectue le produit de la matrice courante avec celle du param�tre
	 * @param m2 matrice en facteur
	 * @return matrice resultante 
	 */
	public MatriceFloat produit(MatriceFloat m2) {
		float[][] res=new float[matrice.length][m2.matrice[0].length];
		for(int l=0; l<res.length; l++)
			for(int c=0; c<res[0].length; c++) {
				float val=0;
				for(int i=0; i<matrice[0].length; i++)
					val+=(matrice[l][i]*m2.matrice[i][c]);
				res[l][c]=val;
			}
		return new MatriceFloat(res);
	}
	
	/**
	 * Effectue le produit de la matrice courante avec celle du param�tre et modifie dans l'objet courant
	 * @param m2 matrice en facteur
	 */
	public void transformation(MatriceFloat m2) {
		MatriceFloat m=produit(m2);
		for(int i=0; i<matrice.length; i++)
			for(int j=0; j<matrice[0].length; j++)
				matrice[i][j]=m.matrice[i][j];
	} 
	
	public float[][] getMatrice() {
		return matrice;
	}
	
	/** 
	 * 
	 * @param i coordonne du tableau de matrice
	 * @param j
	 * @return le float en position i,j de la matrice 
	 */
	public float get(int i,int j){
		return matrice[i][j];
	}

	@Override
	public String toString() {
		System.out.println("toString ");
		String res ="[[";
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice.length; j++) {
				res+="; "+get(i,j);
			}
			res+="]\n[";
		}
		res+="]]";
		return res;
	}
}
