package coordonnees;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;

import javax.vecmath.Vector3f;

import affichage.Affichage3D;
import maths.Geometrie;
import maths.MatriceFloat;

public class Modele extends Observable {

	private Face[] faces;
	
	private MatriceFloat ensemblePoints;
	
	public final Vector3f LUMIERE=new Vector3f(-1, 0, -1);
	
	public final float[] PLAN=new float[]{-1,0,-1,300};

	public Modele(String fileName) throws IOException  {
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
		LUMIERE.normalize();
	}
	
	public float[][] getPoints() {
		return ensemblePoints.getMatrice();
	}
	
	/**
	 * Renvoie les coordonnees de la projection sur le plan PLAN parralelement au vecteur LUMIERE
	 */
	public float[][] getProjection() {
		return ensemblePoints.produit(Geometrie.projection(PLAN, LUMIERE)).getMatrice();
	}

	public Face[] getFaces() {
		return faces;
	}
	
	/**
	 * Trie les faces suivant la valeur moyenne de la coordonnee z 
	 */
	private void triFaces() {
		Arrays.sort(faces,Face.comparateurZ);
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
	public void ajustePoints() {
		float middleX=(getXMin()+getXMax())/2, middleY=(getYMin()+getYMax())/2;
		float dx=getXMax()-getXMin(), dy=getYMax()-getYMin();
		float width=(float)(Affichage3D.fenetre.getWidth()*0.9), height=(float)(Affichage3D.fenetre.getHeight()*0.9);
		ensemblePoints.transformation(Geometrie.cadrage(middleX, middleY, dx, dy, width, height));
		setChanged();
		notifyObservers();
	}
	
	public void zoom(float coeff,Point point) {
		float x=(float)point.getX(), y=(float)point.getY();
		ensemblePoints.transformation(Geometrie.homothetie(coeff, x, y,0));
		setChanged();
		notifyObservers();
	}
	
	public void translation(float x, float y, float z) {
		ensemblePoints.transformation(Geometrie.translation(x, y, z));
		setChanged();
		notifyObservers();
	}
	
	public void rotationZ(double angle) {
		ensemblePoints.transformation(Geometrie.rotationZ(angle,Geometrie.isobarycentre(getPoints())));
		triFaces();
		setChanged();
		notifyObservers();
	}

	public void rotation(Point p, Point nouveau) {
		float[] point=Geometrie.isobarycentre(getPoints());
		ensemblePoints.transformation(Geometrie.rotationX(-((nouveau.getY()-p.getY())/400)%(2*Math.PI),point)
				.produit(Geometrie.rotationY(((nouveau.getX()-p.getX())/400)%(2*Math.PI), point)));
		triFaces();
		setChanged();
		notifyObservers();
	}
	
	

}