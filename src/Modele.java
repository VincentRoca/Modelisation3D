import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Modele {

	private Face[] faces;
	
	private MatriceFloat ensemblePoints;

	Modele(String fileName) throws IOException  {
		float[][] points=null;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String ligne;
		while(!(ligne= br.readLine()).equals("end_header")){
			String[] parts = ligne.split(" ");
			if(parts[0].equals("element") && parts[1].equals("vertex"))
				points=new float[Integer.valueOf(parts[2])][4];
			if(parts[0].equals("element") && parts[1].equals("face"))
				faces=new Face[Integer.valueOf(parts[2])];
		}
		for(int i=0; i<points.length; i++) {
			ligne=br.readLine();
			String[] parts=ligne.split(" ");
			points[i][0]=Float.valueOf(parts[0]);
			points[i][1]=Float.valueOf(parts[1]);
			points[i][2]=Float.valueOf(parts[2]);
			points[i][3]=1;
		}
		ensemblePoints=new MatriceFloat(points);
		for(int i=0; i<faces.length; i++) {
			ligne=br.readLine();
			String[] parts=ligne.split(" ");
			float[][] tmp=new float[Integer.valueOf(parts[0])][4];
			for(int j=0; j<tmp.length; j++)
				tmp[j]=points[Integer.valueOf(parts[j+1])];
			faces[i]=new Face(tmp);
		}
		br.close();
		ajustePoints();
		triFaces();
	}
	
	float[][] getPoints() {
		return ensemblePoints.getMatrice();
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
		float[][] points=ensemblePoints.getMatrice();
		float min=Float.MAX_VALUE;
		for(int i=0; i<points.length; i++)
			if(points[i][0]<min) min=points[i][0];
		return min;
	}
	
	private float getXMax() {
		float[][] points=ensemblePoints.getMatrice();
		float max=Float.MIN_VALUE;
		for(int i=0; i<points.length; i++)
			if(points[i][0]>max) max=points[i][0];
		return max;
	}
	
	private float getYMin() {
		float[][] points=ensemblePoints.getMatrice();
		float min=Float.MAX_VALUE;
		for(int i=0; i<points.length; i++)
			if(points[i][1]<min) min=points[i][1];
		return min;
	}
	
	private float getYMax() {
		float[][] points=ensemblePoints.getMatrice();
		float max=Float.MIN_VALUE;
		for(int i=0; i<points.length; i++)
			if(points[i][1]>max) max=points[i][1];
		return max;
	}
	
	/**
	 * ajuste les coordonnees de la figure pour un centrage et un zoom correct
	 */
	private void ajustePoints() {
		//centrage : 
		float middleX=(getXMin()+getXMax())/2, middleY=(getYMin()+getYMax())/2;
		translation(Main.milieu.x-middleX, Main.milieu.y-middleY,0);
		//zoom : 
		float dx=getXMax()-getXMin();
		float width=(float)(Main.fenetre.getWidth()*0.9), height=(float)(Main.fenetre.getHeight()*0.9);
		zoom(width/dx,Main.milieu);
		float dy=getYMax()-getYMin();
		if(dy>Main.fenetre.getHeight())
			zoom(height/dy,Main.milieu);
	}
	
	
	boolean zoom(float coeff,Point point) {
		float x=(float)point.getX(), y=(float)point.getY();
		return ensemblePoints.transformation(MatriceFloat.homothetie(coeff, x, y));
	}
	
	boolean translation(float x, float y, float z) {
		return ensemblePoints.transformation(MatriceFloat.translation(x, y, z));
	}
}