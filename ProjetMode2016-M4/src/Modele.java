import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Modele {

	private Face[] faces;
	private Point3D[] points;

	Modele(String fileName) throws IOException  {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String ligne;
		while(!(ligne= br.readLine()).equals("end_header")){
			String[] parts = ligne.split(" ");
			if(parts[0].equals("element") && parts[1].equals("vertex"))
				points=new Point3D[Integer.valueOf(parts[2])];
			if(parts[0].equals("element") && parts[1].equals("face"))
				faces=new Face[Integer.valueOf(parts[2])];
		}
		for(int i=0; i<points.length; i++) {
			ligne=br.readLine();
			String[] parts=ligne.split(" ");
			points[i]=new Point3D(Float.valueOf(parts[0]),Float.valueOf(parts[1]),Float.valueOf(parts[2]));
		}
		for(int i=0; i<faces.length; i++) {
			ligne=br.readLine();
			String[] parts=ligne.split(" ");
			Point3D[] tmp=new Point3D[Integer.valueOf(parts[0])];
			for(int j=0; j<tmp.length; j++)
				tmp[j]=points[Integer.valueOf(parts[j+1])];
			faces[i]=new Face(tmp);
		}
		br.close();
		ajustePoints();
		triFaces();
	}

	Face[] getFaces() {
		return faces;
	}
	
	/**
	 * Trie les faces suivant la valeur moyenne du parametre z
	 */
	private void triFaces() {
		boolean sorted=false;
		for(int i=faces.length-1; i>=1 && !sorted; i--) {
			sorted=true;
			for(int j=0; j<i; j++)
				if(faces[j].getSommeZ()<faces[j+1].getSommeZ()) {
					Face tmp=faces[j];
					faces[j]=faces[j+1];
					faces[j+1]=tmp;
				}
		}
	}
	
	private float getXMin() {
		float min=Float.MAX_VALUE;
		for(Point3D p : points)
			if(p.x<min) min=p.x;
		return min;
	}
	
	private float getXMax() {
		float max=Float.MIN_VALUE;
		for(Point3D p : points)
			if(p.x>max) max=p.x;
		return max;
	}
	
	private float getYMin() {
		float min=Float.MAX_VALUE;
		for(Point3D p : points)
			if(p.y<min) min=p.y;
		return min;
	}
	
	private float getYMax() {
		float max=Float.MIN_VALUE;
		for(Point3D p : points)
			if(p.y>max) max=p.y;
		return max;
	}
	
	/**
	 * ajuste les coordonnees de la figure pour un centrage et un zoom correct
	 */
	private void ajustePoints() {
		//centrage : 
		float middleX=(getXMin()+getXMax())/2, middleY=(getYMin()+getYMax())/2;
		translation(Main.milieu.x-middleX, Main.milieu.y-middleY);
		//zoom : 
		float dx=getXMax()-getXMin();
		float width=(float)(Main.fenetre.getWidth()*0.9), height=(float)(Main.fenetre.getHeight()*0.9);
		zoom(width/dx,Main.milieu);
		float dy=getYMax()-getYMin();
		if(dy>Main.fenetre.getHeight())
			zoom(height/dy,Main.milieu);
	}
	
	
	void zoom(float coeff,Point point) {
		double x=point.getX(), y=point.getY();
		for(Point3D p : points) {
			p.x=(float)(x+coeff*(p.x-x));
			p.y=(float)(y+coeff*(p.y-y));
			p.z/=coeff;
		}	
	}
	
	void translation(float x, float y) {
		for(Point3D p : points) {
			p.x+=x;
			p.y+=y;
		}
	}
}