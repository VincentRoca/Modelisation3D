
abstract class Geometrie {

	static MatriceFloat translation(float x, float y, float z) {
		float[][] m=new float[4][4];
		m[0][0]=1; m[0][1]=0; m[0][2]=0; m[0][3]=0;
		m[1][0]=0; m[1][1]=1; m[1][2]=0; m[1][3]=0;
		m[2][0]=0; m[2][1]=0; m[2][2]=1; m[3][2]=0;
		m[3][0]=x; m[3][1]=y; m[3][2]=z; m[3][3]=1;
		return new MatriceFloat(m);
	}
	
	static MatriceFloat homothetie(float rapport, float x, float y, float z) {
		float[][] m=new float[4][4];
		m[0][0]=rapport; m[0][1]=0; m[0][2]=0; m[0][3]=0;
		m[1][0]=0; m[1][1]=rapport; m[1][2]=0; m[1][3]=0;
		m[2][0]=0; m[2][1]=0; m[2][2]=rapport; m[3][2]=0;
		m[3][0]=x*(1-rapport); m[3][1]=y*(1-rapport); m[3][2]=z*(1-rapport); m[3][3]=1;
		return new MatriceFloat(m);
	}
	
	/**
	 * Matrice permettant de cadrer un ensemble de points au centre et à taille suffisante sur l'écran
	 * @param middleX milieu des points sur la dimension X
	 * @param middleY milieu des points sur la dimension Y
	 * @param dx différence entre valeur maximale et valeur minimale sur la dimension X
	 * @param dy différence entre valeur maximale et valeur minimale sur la dimens
	 * @param width nouvelle largeur de la figure
	 * @param height nouvelle hauteur de la figure
	 * @return la matrice de transformation
	 */
	static MatriceFloat cadrage(float middleX, float middleY, float dx, float dy, float width, float height) {
		float rapport;
		if(width/dx>height/dy) rapport=height/dy;
		else rapport=width/dx;
		return translation((float)Main.milieu.getX()-middleX, (float)Main.milieu.getY()-middleY, 0).produit(homothetie(rapport, Main.milieu.x, Main.milieu.y, 0));
	}
	
	static MatriceFloat rotationX(double angle,float[] centre) {
		float[][] m=new float[4][4];
		m[0][0]=1; m[0][1]=0; m[0][2]=0; m[0][3]=0;
		m[1][0]=0; m[1][1]=(float)Math.cos(angle); m[1][2]=(float)Math.sin(angle); m[1][3]=0;
		m[2][0]=0; m[2][1]=(float)-Math.sin(angle); m[2][2]=(float)Math.cos(angle); m[3][2]=0;
		m[3][0]=0; m[3][1]=(float)(centre[1]*(-Math.cos(angle)+1)+centre[2]*Math.sin(angle));
		m[3][2]=(float)(centre[2]*(-Math.cos(angle)+1)-centre[1]*Math.sin(angle)); m[3][3]=1;
		return new MatriceFloat(m);
	}
	
	static MatriceFloat rotationZ(double angle, float[] centre) {
		float[][] m=new float[4][4];
		m[0][0]=(float)Math.cos(angle); m[0][1]=(float)Math.sin(angle); m[0][2]=0; m[0][3]=0;
		m[1][0]=(float)-Math.sin(angle); m[1][1]=(float)Math.cos(angle); m[1][2]=0; m[1][3]=0;
		m[2][0]=0; m[2][1]=0; m[2][2]=1; m[3][2]=0;
		m[3][0]=(float)(centre[0]*(-Math.cos(angle)+1)+centre[1]*Math.sin(angle)); 
		m[3][1]=(float)(centre[0]*(-Math.cos(angle)+1)-centre[0]*Math.sin(angle)); 
		m[3][2]=0; m[3][3]=1;
		return new MatriceFloat(m);
	}
	
	static MatriceFloat rotationY(double angle, float[] centre) {
		float[][] m=new float[4][4];
		m[0][0]=(float)Math.cos(angle); m[0][1]=0; m[0][2]=(float)-Math.sin(angle); m[0][3]=0;
		m[1][0]=0; m[1][1]=1; m[1][2]=0; m[1][3]=0;
		m[2][0]=(float)Math.sin(angle); m[2][1]=0; m[2][2]=(float)Math.cos(angle); m[3][2]=0;
		m[3][0]=(float)(centre[0]*(-Math.cos(angle)+1)-centre[2]*Math.sin(angle)); 
		m[3][1]=0; 
		m[3][2]=(float)(centre[2]*(-Math.cos(angle)+1)+centre[0]*Math.sin(angle)); m[3][3]=1;
		return new MatriceFloat(m);
	}

	static float[] isobarycentre(float[][] points) {
		float[] res=new float[]{0,0,0};
		for(int i=0; i<points.length; i++) 
			for(int j=0; j<3; j++)
				res[j]+=points[i][j];
		for(int i=0; i<3; i++)
			res[i]/=points.length;
		return res;
	}
}
