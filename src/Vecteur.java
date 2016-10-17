
public class Vecteur {
	private float x,y,z;
	
	Vecteur(float x,float y, float z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Point)) return false;
		Point p=(Point)o;
		return p.x==x && p.y==y && p.z==z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
}
