package md.utm.labs.gc;

import static org.junit.Assert.*;

import org.junit.Test;

import md.utm.labs.gc.manipulator.Matrix;
import md.utm.labs.gc.manipulator.MatrixBuilder;

public class MatrixTest {
	
	Matrix matrix = new MatrixBuilder().newRow().add(1).add(2).newRow().add(3).add(4).build();

	@Test
	public void canGenerateTheIdentityMatrix() {
		Matrix identity = Matrix.getIdentityMatrix(2);
		int i = 0, j = 0;
		for (i = 0; i < identity.getNumberOfRows(); ++i) {
			for (j = 0; j < identity.getNumberOfColumns(); ++j) {
				if (i == j)
					assertEquals(Double.valueOf(1), identity.getElementAt(i, j));
				else
					assertEquals(Double.valueOf(0), identity.getElementAt(i, j));
			}
		}
		assertNotEquals(0, i);
		assertNotEquals(0, j);
	}
	
	@Test
	public void canBuildAMatrix() {
		assertEquals("[[1.0, 2.0], [3.0, 4.0]]", matrix.toString());
	}
	
	@Test
	public void canSetAnItem() {
		matrix.set(0, 1, 66.0);
		assertEquals(Double.valueOf(66), matrix.getElementAt(0, 1));
	}
}
