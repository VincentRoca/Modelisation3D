
class Face {
	
	private Point[] points;
	
	Point[] getPoints() {
		return points;
	}
	
	Face(Point[] points) {
		this.points=points;
	}
	
	float getSommeZ() {
		float res=0;
		for(Point p : points)
			res+=p.z;
		return res;
	}
	
}

