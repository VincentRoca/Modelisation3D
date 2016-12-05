package coordonnees;
import java.awt.Color;
import java.util.Comparator;


public class Face {
	
	private float[][] points;
	private int r=(int) (Math.random()*256);
	private Color color=new Color(r,r,r);
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
	
	public Color getColor() {
		return color;
	}

	private float moyenneZ() {
		float s=0;
		for(int i=0; i<points.length; i++)
			s+=points[i][2];
		return s/points.length;
	}
	
}

