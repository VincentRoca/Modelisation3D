import java.awt.Color;

class Face {
	
	private Point3D[] points;
	private Color color=new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
	
	Point3D[] getPoints() {
		return points;
	}
	
	Face(Point3D[] points) {
		this.points=points;
	}
	
	Color getColor() {
		return color;
	}
	
	float getSommeZ() {
		float res=0;
		for(Point3D p : points)
			res+=p.z;
		return res;
	}
	
}
