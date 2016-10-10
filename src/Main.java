import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.font.ImageGraphicAttribute;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


class Main {

	public static void main(String[] args) {
		try {
			String filename=null;
			if(args.length==2)
				filename=args[1];
			else if(args.length==1)
				filename=args[0];
			Modele m = new Modele(filename);
			
			//****JFrame****\\
			JFrame frame=new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension((int)m.getXMax()+100, (int)m.getYmax()+100));
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setLayout(new BorderLayout());

			JPanel menu=new JPanel();
			menu.setLayout(new GridLayout(2, 5,10,10));
			 menu.add(new JButton("1"));
			 menu.add(new JButton("2"));
			 menu.add(new JButton("3"));
			 menu.add(new JButton("4")); 
			 menu.add(new JButton("5"));

			Dessin d=new Dessin(m);
			
			//gestion de l'option d'affichage
			if(args.length==2) {
				if(args[0].equals("-f"))
					d.setType(Dessin.FACES);
				else if(args[0].equals("-s"))
					d.setType(Dessin.ARETES);
				else
					throw new Exception();
			}
			
			frame.getContentPane().add(d,BorderLayout.CENTER);
			frame.getContentPane().add(menu,BorderLayout.SOUTH);

			d.repaint();
			frame.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Mauvaise utilisation du logiciel");
		}
		
	}

}
