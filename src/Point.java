
class Point {
	
	float x,y,z;
	
	Point(float x,float y, float z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Point)) return false;
		Point p=(Point)o;
		return p.x==x && p.y==y && p.z==z;
	}

}
