package affichage;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import coordonnees.Modele;


public class Main {
	
	public static Point milieu;
	public static Rectangle fenetre;

	public static void main(String[] args) {
		try {
			String filename=null;
			if(args.length==2)
				filename=args[1];
			else if(args.length==1)
				filename=args[0];
			JFrame frame=new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fenetre=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
			frame.setPreferredSize(fenetre.getSize());
			milieu=milieu();
			frame.setResizable(false);
			frame.pack();
			frame.setLocationRelativeTo(null);
			Dessin d=new Dessin(new Modele(filename));
			if(args.length==2) {
				if(args[0].equals("-f"))
					d.setType(Dessin.FACES);
				else if(args[0].equals("-s"))
					d.setType(Dessin.ARETES);
				else
					throw new Exception();
			}
			frame.getContentPane().add(d);
			frame.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Mauvaise utilisation du logiciel");
		}
	}

	private static Point milieu() {
		Point res=new Point();
		res.setLocation(fenetre.getWidth()/2, fenetre.getHeight()/2);
		return res;	
	}

}
