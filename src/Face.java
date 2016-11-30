import java.awt.Color;

class Face {
	
	private float[][] points;
	private Color color=new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
	
	float[][] getPoints() {
		return points;
	}
	
	Face(float[][] points) {
		this.points=points;
	}
	
	Color getColor() {
		return color;
	}

	float getSommeZ() {
		float res=0;
		for(int i=0; i<points.length; i++)
			res+=points[i][2];
		return res;
	}
	
}

