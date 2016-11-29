class MatriceFloat {

    private float[][] matrice;
    
    MatriceFloat(float[][] matrice) {
        this.matrice=matrice;
    }
    
    /**
     * Effectue le produit de la matrice courante avec celle du paramètre
     * @param m2 matrice en facteur
     * @return matrice résultante ou null si l'opération est impossible
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
    
    /**
     * Effectue le produit de la matrice courante avec celle du paramètre et range le résultat dans l'objet courant
     * @param m2 matrice en facteur
     * @return true si l'opération est possible
     */
    boolean transformation(MatriceFloat m2) {
        MatriceFloat m=produit(m2);
        if(m==null) return false;
        for(int i=0; i<matrice.length; i++)
            for(int j=0; j<matrice[0].length; j++)
                matrice[i][j]=m.matrice[i][j];
        return true;
    }
    
    float[][] getMatrice() {
        return matrice;
    }
    
    static MatriceFloat translation(float x, float y, float z) {
        float[][] m=new float[4][4];
        m[0][0]=1; m[0][1]=0; m[0][2]=0; m[0][3]=0;
        m[1][0]=0; m[1][1]=1; m[1][2]=0; m[1][3]=0;
        m[2][0]=0; m[2][1]=0; m[2][2]=0; m[3][2]=0;
        m[3][0]=x; m[3][1]=y; m[3][2]=z; m[3][3]=1;
        return new MatriceFloat(m);
    }
    
    static MatriceFloat homothetie(float rapport, float x, float y) {
        float[][] m=new float[4][4];
        m[0][0]=rapport; m[0][1]=0; m[0][2]=0; m[0][3]=0;
        m[1][0]=0; m[1][1]=rapport; m[1][2]=0; m[1][3]=0;
        m[2][0]=0; m[2][1]=0; m[2][2]=rapport; m[3][2]=0;
        m[3][0]=-x; m[3][1]=-y; m[3][2]=0; m[3][3]=1;
        return new MatriceFloat(m);
    }
}