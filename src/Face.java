
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
	
	public boolean equals(Object o) {
		if(!(o instanceof Face)) return false;
		Face f=(Face)o;
		Point[] points=f.getPoints();
		if(this.points.length!=points.length) return false;
		for(Point p : this.points) {
			boolean present=false;
			for(int i=0; i<points.length && !present; i++) 
				if(p.equals(points[i])) present=true;
			if(present==false) return false;
		}
		return true;
	}
	
}

