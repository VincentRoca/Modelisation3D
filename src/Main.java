import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


class Main {

	public static void main(String[] args) {
		try {
			String filename=null;
			if(args.length==2)
				filename=args[1];
			else if(args.length==1)
				filename=args[0];
			Modele m = new Modele(filename);
			final Dessin d=new Dessin(m);
			final float zoom = 1;
			
			//****JFrame****\\
			Dimension dimension =java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			int screenX =(int) dimension.getWidth();
			int screenY =(int) dimension.getHeight();
			
			final JFrame frame=new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension(screenX, screenY-45));
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setLayout(new BorderLayout());
			
			final JPanel figure = new JPanel();
			figure.setLayout(new BorderLayout());
			figure.add(d, BorderLayout.CENTER);
			
			 JPanel menu=new JPanel();
			 JButton zoomPlus=new JButton("+");
			 zoomPlus.setPreferredSize(new Dimension(50,50));
			 JButton zoomMoin=new JButton("-");
			 zoomMoin.setPreferredSize(new Dimension(50,50));
			 menu.add(zoomMoin);
			 menu.add(zoomPlus);
			
			 
			 zoomPlus.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					//Effacer le dessin precedent
					figure.removeAll();
					figure.paint(figure.getGraphics());
					//Ajouter le nouveau dessin
					d.setZoom(zoom+(float)0.1);
					figure.add(d);
					figure.paint(d.getGraphics());
				}
			 });
			 
			 zoomMoin.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					//Effacer le dessin precedent
					figure.removeAll();
					figure.paint(figure.getGraphics());
					//Ajouter le nouveau dessin
					d.setZoom(zoom-(float)0.1);
					figure.add(d);
					figure.paint(d.getGraphics());
				}
			 });
			 
			
			//gestion de l'option d'affichage
			if(args.length==2) {
				if(args[0].equals("-f"))
					d.setType(Dessin.FACES);
				else if(args[0].equals("-s"))
					d.setType(Dessin.ARETES);
				else
					throw new Exception();
			}
			
			frame.getContentPane().add(figure,BorderLayout.CENTER);
			frame.getContentPane().add(menu,BorderLayout.SOUTH);

			d.repaint();
			frame.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Mauvaise utilisation du logiciel");
			System.out.println(e.getMessage());
		}
		
	}

}
