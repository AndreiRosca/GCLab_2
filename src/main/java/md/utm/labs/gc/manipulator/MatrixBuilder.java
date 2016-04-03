package md.utm.labs.gc.manipulator;

import java.util.ArrayList;
import java.util.List;

public class MatrixBuilder {

	private List<List<Double>> matrix = new ArrayList<List<Double>>();

	public MatrixBuilder newRow() {
		matrix.add(new ArrayList<Double>());
		return this;
	}

	public MatrixBuilder add(double value) {
		matrix.get(matrix.size() - 1).add(value);
		return this;
	}

	public Matrix build() {
		return new Matrix(matrix);
	}
}
