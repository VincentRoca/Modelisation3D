import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


class Main {

	public static void main(String[] args) {
		try {
			String filename=null;
			if(args.length==2)
				filename=args[1];
			else if(args.length==1)
				filename=args[0];
			Modele m = new Modele(filename);
			JFrame frame=new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setPreferredSize(new Dimension((int)m.getXMax()+100, (int)m.getYmax()+100));
			frame.pack();
			frame.setLocationRelativeTo(null);
			Dessin d=new Dessin(m);
			if(args.length==2) {
				if(args[0].equals("-f"))
					d.setType(Dessin.FACES);
				else if(args[0].equals("-s"))
					d.setType(Dessin.ARETES);
				else
					throw new Exception();
			}
			frame.getContentPane().add(d);
			d.repaint();
			frame.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Mauvaise utilisation du logiciel");
		}
		
	}

}
