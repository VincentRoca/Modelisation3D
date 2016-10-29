import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Modele {

	private Face[] faces;
	private Point[] points;
	
	Modele(String fileName) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String ligne;
		while(!(ligne= br.readLine()).equals("end_header")){
			String[] parts = ligne.split(" ");
			if(parts[0].equals("element") && parts[1].equals("vertex"))
				points=new Point[Integer.valueOf(parts[2])];
			if(parts[0].equals("element") && parts[1].equals("face"))
				faces=new Face[Integer.valueOf(parts[2])];
		}
		for(int i=0; i<points.length; i++) {
			ligne=br.readLine();
			String[] parts=ligne.split(" ");
			points[i]=new Point(Float.valueOf(parts[0]),Float.valueOf(parts[1]),Float.valueOf(parts[2]));
		}
		for(int i=0; i<faces.length; i++) {
			ligne=br.readLine();
			String[] parts=ligne.split(" ");
			Point[] tmp=new Point[Integer.valueOf(parts[0])];
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
	
	float getXMax() {
		float max=Float.MIN_VALUE;
		for(Point p : points)
			if(p.x>max)
				max=p.x;
		return max;
	}
	
	float getYmax() {
		float max=Float.MIN_VALUE;
		for(Point p : points)
			if(p.y>max)
				max=p.y;
		return max;
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
	
	private void ajustePoints() {
		float minX=0, minY=0;
		for(Point p : points) {
			if(p.x<minX) 
				minX=p.x;
			if(p.y<minY)
				minY=p.y;
		}
		for(Point p : points) {
			p.x=p.x-minX+50;
			p.y=p.y-minY+50;
		}
	}
	public void translation(Vecteur vec){
		for(Point p: points){
			p.x+=vec.getX();
			p.y+=vec.getY();
			
		}
		
	}
}