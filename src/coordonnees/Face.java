package coordonnees;
import java.awt.Color;
import java.util.Comparator;

import javax.vecmath.Vector3d;

public class Face {
	
	private float[][] points;
	private int g;
	//private Color color=new Color(r,r,r);
	static Comparator<Face> comparateurZ=new Comparator<Face>() {
		public int compare(Face f1, Face f2) {
			float a=f1.moyenneZ(), b=f2.moyenneZ();
			if(a-b>0) return 1;
			if(a==b) return 0;
			return -1;
		}
	};
	
	public float[][] getPoints() {
		return points;
	}
	
	Face(float[][] points) {
		this.points=points;
	}
	
	/**
	 * d�finit le niveau de gris de la face en fonction de la lumi�re re�u
	 * @param lumiere vecteur lumiere
	 */
	public void defineG(Vector3d lumiere) {
		Vector3d v=vecteurNormal();
		v.normalize();
		g=(int)(v.dot(lumiere)*255);
		if(g<0)g=-g;
		//System.out.println(g);
	}
	
	public Color getColor() {
		return new Color(g,g,g);
	}

	private float moyenneZ() {
		float s=0;
		for(int i=0; i<points.length; i++)
			s+=points[i][2];
		return s/points.length;
	}
	
	/**
	 * Retourne un vecteur normal au plan d�fini par les points de la face
	 */
	private Vector3d vecteurNormal() {
		// on calcule les coordonnees de 2 vecteurs directeurs du plan
		float a1=points[0][0]-points[1][0]; float a2=points[0][0]-points[2][0];
		float b1=points[0][1]-points[1][1]; float b2=points[0][1]-points[2][1];
		float c1=points[0][2]-points[1][2]; float c2=points[0][2]-points[2][2];
		return new Vector3d(((-b1*c2*(a2+a1))/(b2*a1-a2*b1)-c2)/a1,
				(a2*c1-c2*a1)/(b2*a1-a2*b1),1);
	}
	
}

