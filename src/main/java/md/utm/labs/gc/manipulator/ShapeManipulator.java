package md.utm.labs.gc.manipulator;

import md.utm.labs.gc.shapes.Point;
import md.utm.labs.gc.shapes.Shape;

public class ShapeManipulator {

	private MatrixCalculator calculator = MatrixCalculator.newInstance();

	public static ShapeManipulator newInstance() {
		return new ShapeManipulator();
	}

	protected ShapeManipulator() {
		super();
	}

	public void scaleVerticallyBy(Shape shape, double sy) {
		new ScaleOperationTemplate(1, sy).transform(shape);
	}

	public void scaleHorizontallyBy(Shape shape, double sx) {
		new ScaleOperationTemplate(sx, 1).transform(shape);
	}

	public void translateHorizontallyBy(Shape shape, double dx) {
		new TranslateOperationTemplate(dx, 0).transform(shape);
	}

	public void translateVerticallyBy(Shape shape, double dy) {
		new TranslateOperationTemplate(0, dy).transform(shape);
	}
	
	public void rotateBy(Shape shape, Point rotationPoint, int degrees) {
		new TranslateOperationTemplate(-rotationPoint.getX(), -rotationPoint.getY()).transform(shape);
		new RotationOperationTemplate(degrees * Math.PI / 180.0).transform(shape);
		new TranslateOperationTemplate(rotationPoint.getX(), rotationPoint.getY()).transform(shape);
	}
	
	public void reflectAround(Shape shape, double slope) {
		new RotationOperationTemplate(Math.atan(slope)).transform(shape);
		new ReflectionOperationTemplate().transform(shape);
		new RotationOperationTemplate(-Math.atan(slope)).transform(shape);
	}

	private class ScaleOperationTemplate extends ShapeTransformationTemplate {
		private final double sx;
		private final double sy;

		public ScaleOperationTemplate(double sx, double sy) {
			this.sx = sx;
			this.sy = sy;
		}

		protected Matrix prepareTransformationMatrix() {
			Matrix scaleMatrix = Matrix.getIdentityMatrix(3);
			scaleMatrix.set(0, 0, scaleMatrix.getElementAt(0, 0) * sx);
			scaleMatrix.set(1, 1, scaleMatrix.getElementAt(1, 1) * sy);
			return scaleMatrix;
		}
	}

	private class TranslateOperationTemplate extends ShapeTransformationTemplate {
		private final double dx;
		private final double dy;

		public TranslateOperationTemplate(double dx, double dy) {
			this.dx = dx;
			this.dy = dy;
		}

		protected Matrix prepareTransformationMatrix() {
			Matrix translationMatrix = Matrix.getIdentityMatrix(3);
			int lastColumn = translationMatrix.getNumberOfColumns() - 1;
			translationMatrix.set(0, lastColumn, dx);
			translationMatrix.set(1, lastColumn, dy);
			return translationMatrix;
		}
	}
	
	private class RotationOperationTemplate extends ShapeTransformationTemplate {
		private final double angle;

		public RotationOperationTemplate(double angle) {
			this.angle = angle;
		}

		protected Matrix prepareTransformationMatrix() {
			Matrix  rotationMatrix = new MatrixBuilder()
					.newRow().add(Math.cos(angle)).add(-Math.sin(angle)).add(0)
					.newRow().add(Math.sin(angle)).add(Math.cos(angle)).add(0)
					.newRow().add(0).add(0).add(1)
					.build();
			return rotationMatrix;
		}
	}
	
	private class ReflectionOperationTemplate extends ShapeTransformationTemplate {

		protected Matrix prepareTransformationMatrix() {
			Matrix scaleMatrix = Matrix.getIdentityMatrix(3);
			scaleMatrix.set(0, 0, scaleMatrix.getElementAt(0, 0) * -1);
			scaleMatrix.set(1, 1, scaleMatrix.getElementAt(1, 1) * 1);
			return scaleMatrix;
		}
	}

	private abstract class ShapeTransformationTemplate {

		public final void transform(Shape shape) {
			for (Point point : shape.getEdges()) {
				Matrix transformationMatrix = prepareTransformationMatrix();
				Matrix coordinateMatrix = buildCoordinateMatrix(point);
				Matrix newCoordinates = calculator.multiply(transformationMatrix, coordinateMatrix);
				updatePointPosition(point, newCoordinates);
			}
		}

		protected abstract Matrix prepareTransformationMatrix();

		private void updatePointPosition(Point point, Matrix newCoordinates) {
			point.setX(newCoordinates.getElementAt(0, 0));
			point.setY(newCoordinates.getElementAt(1, 0));
		}

		private Matrix buildCoordinateMatrix(Point point) {
			Matrix matrix = new MatrixBuilder()
					.newRow().add(point.getX())
					.newRow().add(point.getY())
					.newRow().add(1)
					.build();
			return matrix;
		}
	}
}
