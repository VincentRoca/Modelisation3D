


import static org.junit.Assert.*;

import org.junit.Test;

import maths.MatriceFloat;

public class MatriceFloatTest {

	@Test
	public void testProduit() {
		float [][]f1=new float[2][2];
		f1[0][0]=2;
		f1[0][1]=2;
		f1[1][0]=3;
		f1[1][1]=1;

		float [][]f2=new float[2][2];
		f2[0][0]=1;
		f2[0][1]=3;
		f2[1][0]=2;
		f2[1][1]=4;
		MatriceFloat m1=new MatriceFloat (f1);
		MatriceFloat m2=new MatriceFloat (f2);
		MatriceFloat m3=m1.produit(m2);

		//	System.out.println(m3.get(0,0));
		assertEquals(m3.get(0,0),6, 0.001);
		assertEquals(m3.get(0,1),14, 0.001);
		assertEquals(m3.get(1,0),5, 0.001);
		assertEquals(m3.get(1,1),13, 0.001);
	}

	@Test
	public void testTransformation() {
		float [][]f1=new float[2][2];
		f1[0][0]=2;
		f1[0][1]=2;
		f1[1][0]=3;
		f1[1][1]=1;

		float [][]f2=new float[2][2];
		f2[0][0]=1;
		f2[0][1]=3;
		f2[1][0]=2;
		f2[1][1]=4;
		MatriceFloat m1=new MatriceFloat (f1);
		MatriceFloat m2=new MatriceFloat (f2);

		m1.transformation(m2);
		assertEquals(m1.get(0,0),6, 0.001);
		assertEquals(m1.get(0,1),14, 0.001);
		assertEquals(m1.get(1,0),5, 0.001);
		assertEquals(m1.get(1,1),13, 0.001);
		
	}

}
