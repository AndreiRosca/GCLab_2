package md.utm.labs.gc.manipulator;

public class MatrixCalculator {

	public static MatrixCalculator newInstance() {
		return new MatrixCalculator();
	}

	protected MatrixCalculator() {
		super();
	}

	public Matrix multiply(Matrix first, Matrix second) {
		Matrix product = new Matrix(first.getNumberOfRows(), second.getNumberOfColumns());
		for (int i = 0; i < first.getNumberOfRows(); ++i) {
			for (int j = 0; j < second.getNumberOfColumns(); ++j) {
				double sum = 0;
				for (int k = 0; k < first.getNumberOfColumns(); ++k)
					sum += first.getElementAt(i, k) * second.getElementAt(k, j);
				product.set(i, j, sum);
			}
		}
		return product;
	}
}
