import java.awt.Color;


class Face {
	
	private float[][] points;
	int r=(int) (Math.random()*256);
	private Color color=new Color(r,r,r);
	
	float[][] getPoints() {
		return points;
	}
	
	Face(float[][] points) {
		this.points=points;
	}
	
	Color getColor() {
		return color;
	}

	float[] isobarycentre() {
		return Geometrie.isobarycentre(points);
	}
	
}

