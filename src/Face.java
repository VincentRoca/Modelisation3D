import java.awt.Color;

class Face {
	
	private float[][] points;
	private Color color=Color.BLUE;
	
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

