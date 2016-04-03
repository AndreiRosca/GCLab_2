package md.utm.labs.gc;

import static org.junit.Assert.*;

import org.junit.Test;

import md.utm.labs.gc.manipulator.Matrix;
import md.utm.labs.gc.manipulator.MatrixBuilder;
import md.utm.labs.gc.manipulator.MatrixCalculator;

public class MatrixCalculatorTest {

	MatrixCalculator calculator = MatrixCalculator.newInstance();
	
	@Test
	public void canMultiplyTwoMatrices() {
		Matrix identity = Matrix.getIdentityMatrix(2);
		Matrix matrix = new MatrixBuilder().newRow().add(1).add(2).newRow().add(3).add(4).build();
		Matrix product = calculator.multiply(matrix, identity);
		assertEquals(matrix, product);
	}
}
