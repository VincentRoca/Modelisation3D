import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.*;

import maths.Geometrie;
import maths.MatriceFloat;
import references.References;

public class GeometrieTest {
	MatriceFloat m1;
	//@Before
	public void genereMatrice(){
		float [][]f1=new float[4][4];
		f1[0][0]=2; f1[0][1]=2; f1[0][2]=2; f1[0][3]=1;
		f1[1][0]=2; f1[1][1]=2; f1[1][2]=2; f1[1][3]=1;
		f1[2][0]=2; f1[2][1]=2; f1[2][2]=2; f1[2][3]=1;
		f1[3][0]=2; f1[3][1]=2; f1[3][2]=2; f1[3][3]=1;
		
		m1 = new MatriceFloat(f1);
	}

	@Test
	public void testTranslation() {
		genereMatrice();
		m1.transformation(Geometrie.translation(1, 1, 1));
		
		assertEquals(m1.get(0,0),3,0.1);
		assertEquals(m1.get(0,1),3,0.1);
		assertEquals(m1.get(0,2),3,0.1);
		assertEquals(m1.get(0,3),1,0.1);

		assertEquals(m1.get(1,0),3,0.1);
		assertEquals(m1.get(1,1),3,0.1);
		assertEquals(m1.get(1,2),3,0.1);
		assertEquals(m1.get(1,3),1,0.1);

		assertEquals(m1.get(2,0),3,0.1);
		assertEquals(m1.get(2,1),3,0.1);
		assertEquals(m1.get(2,2),3,0.1);
		assertEquals(m1.get(2,3),1,0.1);

		assertEquals(m1.get(3,0),3,0.1);
		assertEquals(m1.get(3,1),3,0.1);
		assertEquals(m1.get(3,2),3,0.1);
		assertEquals(m1.get(3,3),1,0.1);
	
	}
	@Test
	public void testHomothetie() {
		genereMatrice();
		m1.transformation(Geometrie.homothetie((float) 1.5,1,1,1));
		assertEquals(m1.get(0,0),2.5,0.1);
		assertEquals(m1.get(0,1),2.5,0.1);
		assertEquals(m1.get(0,2),2.5,0.1);
		assertEquals(m1.get(0,3),1,0.1);

		assertEquals(m1.get(1,0),2.5,0.1);
		assertEquals(m1.get(1,1),2.5,0.1);
		assertEquals(m1.get(1,2),2.5,0.1);
		assertEquals(m1.get(1,3),1,0.1);

		assertEquals(m1.get(2,0),2.5,0.1);
		assertEquals(m1.get(2,1),2.5,0.1);
		assertEquals(m1.get(2,2),2.5,0.1);
		assertEquals(m1.get(2,3),1,0.1);

		assertEquals(m1.get(3,0),2.5,0.1);
		assertEquals(m1.get(3,1),2.5,0.1);
		assertEquals(m1.get(3,2),2.5,0.1);
		assertEquals(m1.get(3,3),1,0.1);
	}
}
