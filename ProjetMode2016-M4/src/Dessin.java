import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;


class Dessin extends JPanel {
	
	static final byte ALL=0, ARETES=1, FACES=2;
	private Modele modele;
	private byte type;
	
	Dessin(Modele modele) {
		this.modele=modele;
		addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation()>0)
					modele.zoom((float)0.8,e.getPoint());
				else
					modele.zoom((float)1.2,e.getPoint());
				repaint();
			}
		});
		addMouseMotionListener(new DragListener());
	}
	
	void setType(byte type) {
		this.type=type;
	}
		
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		Dimension d=getSize();
		g.fillRect(0, 0, d.width, d.height);
		Face[] faces=modele.getFaces();
		for(int i=0; i<faces.length; i++) {
			Face f=faces[i];
			Point3D[] points=f.getPoints();
			int[] x=null, y=null;
			if(type !=ARETES) {
				x=new int[points.length];
				y=new int[points.length]; 
			}
			if(type!=FACES) g.setColor(new Color(0,0,0));
			for(int j=0; j<points.length; j++) {
				if(type!=FACES) g.drawLine(Math.round(points[j].x), Math.round(points[j].y), Math.round(points[(j+1)%points.length].x), Math.round(points[(j+1)%points.length].y));
				if(type!=ARETES){
					x[j]=Math.round(points[j].x);
					y[j]=Math.round(points[j].y);
				}
			}
			if(type!=ARETES) {
				g.setColor(f.getColor());
				g.fillPolygon(x, y, x.length);
			}
		}
	}
	
	private class DragListener extends MouseMotionAdapter {
		
		private Point p;
		
		public void mouseDragged(MouseEvent e) {
			if(p==null)
				p=e.getPoint();
			else {
				Point nouveau=e.getPoint();
				modele.translation((float)(nouveau.getX()-p.getX()), (float)(nouveau.getY()-p.getY()));
				repaint();
				p=nouveau;
			}
			((JPanel)e.getSource()).addMouseListener(new MouseAdapter() {
				
				public void mouseReleased(MouseEvent e) {
					p=null;
				}
			});
		}
	}
	
}
