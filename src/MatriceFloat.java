class MatriceFloat {

	private float[][] matrice;
	
	MatriceFloat(float[][] matrice) {
		this.matrice=matrice;
	}
	
	/**
	 * @param m2
	 * @return une matrice etant le produit de this et de m2
	 */
	MatriceFloat produit(MatriceFloat m2) {
		if(matrice[0].length!=m2.matrice.length) return null;
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
}
