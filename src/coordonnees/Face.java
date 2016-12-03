package coordonnees;
import java.awt.Color;


public class Face {
	
	private float[][] points;
	private int r=(int) (Math.random()*256);
	private Color color=new Color(r,r,r);
	
	public float[][] getPoints() {
		return points;
	}
	
	Face(float[][] points) {
		this.points=points;
	}
	
	public Color getColor() {
		return color;
	}

	float moyenneZ() {
		float s=0;
		for(int i=0; i<points.length; i++)
			s+=points[i][2];
		return s/points.length;
	}
	
}

