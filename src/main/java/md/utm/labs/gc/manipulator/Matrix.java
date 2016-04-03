package md.utm.labs.gc.manipulator;

import java.util.ArrayList;
import java.util.List;

public class Matrix {

	private List<List<Double>> elements = new ArrayList<List<Double>>();

	public static Matrix getIdentityMatrix(int rows) {
		return new Matrix(rows, rows);
	}

	protected Matrix(int rows, int columns) {
		generateIdentityMatrix(rows, columns);
	}

	Matrix(List<List<Double>> matrix) {
		this.elements = matrix;
	}

	public int getNumberOfRows() {
		return elements.size();
	}

	public int getNumberOfColumns() {
		return elements.get(0).size();
	}

	public Double getElementAt(int i, int j) {
		return elements.get(i).get(j);
	}

	private void generateIdentityMatrix(int rows, int columns) {
		for (int i = 0; i < rows; ++i) {
			List<Double> row = new ArrayList<Double>();
			elements.add(row);
			for (int j = 0; j < columns; ++j)
				row.add(i == j ? 1.0 : 0.0);
		}
	}

	@Override
	public String toString() {
		return elements.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrix other = (Matrix) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}

	public void set(int row, int column, Double value) {
		elements.get(row).set(column, value);
	}
}
