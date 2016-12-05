package affichage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import coordonnees.Face;
import coordonnees.Modele;

/**
 * Cette classe permet d'afficher un modele et fourni et gère les actions à effectuer en fonction des évènements utilisateur. 
 */
class Dessin extends JPanel {
	
	static final byte ALL=0, ARETES=1, FACES=2;
	private final Modele modele;
	private byte type;
	
	Dessin(final Modele modele) {
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
		addMouseMotionListener(new MouseMotionAdapter() {
			
			private Point p;
			
			public void mouseDragged(MouseEvent e) {
				if(p==null)
					p=e.getPoint();
				else {
					Point nouveau=e.getPoint();
					modele.rotation(p,nouveau);
					repaint();
					p=nouveau;
				}
				((JPanel)e.getSource()).addMouseListener(new MouseAdapter() {
					
					public void mouseReleased(MouseEvent e) {
						p=null;
					}
				});
				
			}
		});
		initPanelControle();
	}
	
	void setType(byte type) {
		this.type=type;
	}
	
	private void initPanelControle() {
		JPanel controle=new JPanel();
		setLayout(null);
		controle.setLayout(new BoxLayout(controle, BoxLayout.Y_AXIS));
		add(controle);
		controle.setBounds((int)(Main.fenetre.width*0.8),(int)(Main.fenetre.height*0.8), (int)(Main.fenetre.width*0.2), (int)(Main.fenetre.height*0.2));
		controle.setBackground(Color.lightGray);
		JButton rotation=new JButton("rotationZ");
		controle.add(rotation);
		JButton cadrage=new JButton("cadrage");
		controle.add(cadrage);
		rotation.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				modele.rotationZ(Math.PI/15);
				repaint();
			}
		});
		cadrage.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				modele.ajustePoints();
				repaint();
			}
		});
	}
		
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		Dimension d=getSize();
		g.fillRect(0, 0, d.width, d.height);
		Face[] faces=modele.getFaces();
		for(int i=0; i<faces.length; i++) {
			Face f=faces[i];
			float[][] points=f.getPoints();
			int[] x=null, y=null;
			if(type !=ARETES) {
				x=new int[points.length];
				y=new int[points.length]; 
			}
			if(type!=FACES) g.setColor(new Color(0,0,0));
			for(int j=0; j<points.length; j++) {
				if(type!=FACES) g.drawLine(Math.round(points[j][0]), Math.round(points[j][1]), Math.round(points[(j+1)%points.length][0]), Math.round(points[(j+1)%points.length][1]));
				if(type!=ARETES){
					x[j]=Math.round(points[j][0]);
					y[j]=Math.round(points[j][1]);
				}
			}
			if(type!=ARETES) {
				g.setColor(f.getColor());
				g.fillPolygon(x, y, x.length);
			}
		}
	}
	
}
