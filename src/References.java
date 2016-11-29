import java.awt.Dimension;

/**
 * @author lecomtea
 * Cette classe sert a récuperer les dimensions de l'ecran et les coordonnees du centre de l'ecran.
 *
 */
public class References {
	static private Dimension dimension =java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	
	public static int screenX =(int) dimension.getWidth();
	public static int screenY =(int) dimension.getHeight();
	
	public static int origineX=(int)(dimension.getWidth()/2);
	public static int origineY=(int)(dimension.getHeight()/2);
	
	public static int echelleX=30;
	public static int echelleY=30;
	public static int echelleZ=15;
	


}
