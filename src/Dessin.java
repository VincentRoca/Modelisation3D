import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


class Dessin extends JPanel {
	
	public static final byte ALL=0, ARETES=1, FACES=2;
	private Modele modele;
	private byte type;
	Face[] faces;
	
	Dessin(Modele modele) {
		this.modele=modele;
	}
	
	void setType(byte type) {
		this.type=type;
	}
		
	protected void paintComponent(Graphics g) {
		faces=modele.getFaces();
		for(int i=0; i<faces.length; i++) {
			Face f=faces[i];
			Point[] points=f.getPoints();
			int[] x=null, y=null;
			if(type !=ARETES) {
				x=new int[points.length];
				y=new int[points.length]; 
			}
			if(type!=FACES) g.setColor(new Color(0,0,0));
			for(int j=0; j<points.length; j++) {
				if(type!=FACES) g.drawLine((int)points[j].x, (int)points[j].y, (int)points[(j+1)%points.length].x, (int)points[(j+1)%points.length].y);
				if(type!=ARETES){
					x[j]=(int)points[j].x;
					y[j]=(int)points[j].y;
				}
			}
			if(type!=ARETES) {
				g.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
				g.fillPolygon(x, y, x.length);
			}
		}
	}
	
}
