package md.utm.labs.gc;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.shape.Polyline;
import md.utm.labs.gc.manipulator.ShapeManipulator;
import md.utm.labs.gc.shapes.Point;
import md.utm.labs.gc.shapes.Shape;

public class FXShapeTransformer {
	private static final int HOSIZONTAL_DISPLACEMENT = 5;
	private static final int VERTICAL_DISPLACEMENT = 20;
	private static final double SCALE_FACTOR = 0.5;
	private static final int ROTATION_ANGLE = 360 / 10;
	private static final int NUMBER_OF_TRAILING_SHAPES = 16;
	private ShapeManipulator manipulator;
	private ContinuousShapeRotater rotater = new ContinuousShapeRotater();

	public FXShapeTransformer(ShapeManipulator manipulator) {
		this.manipulator = manipulator;
	}

	public void translateShape(Polyline fxShape) {
		new FXShapeTransformationTemplate() {
			protected void transformShape(Shape shape) {
				manipulator.translateHorizontallyBy(shape, HOSIZONTAL_DISPLACEMENT);
				manipulator.translateVerticallyBy(shape, VERTICAL_DISPLACEMENT);
			}
		}.transform(fxShape);
	}

	public void scaleShape(Polyline fxShape) {
		new FXShapeTransformationTemplate() {
			protected void transformShape(Shape shape) {
				manipulator.scaleHorizontallyBy(shape, SCALE_FACTOR);
				manipulator.scaleVerticallyBy(shape, SCALE_FACTOR);
			}
		}.transform(fxShape);
	}

	public void rotateShape(Polyline fxShape, Point center) {
		rotateShape(fxShape, center, ROTATION_ANGLE);
	}

	private void rotateShape(Polyline fxShape, Point center, int angle) {
		new FXShapeTransformationTemplate() {
			protected void transformShape(Shape shape) {
				manipulator.rotateBy(shape, center, angle);
			}
		}.transform(fxShape);
	}

	public void reflectShape(Polyline fxShape, double slope) {
		new FXShapeTransformationTemplate() {
			protected void transformShape(Shape shape) {
				manipulator.reflectAround(shape, slope);
			}
		}.transform(fxShape);
	}

	public void stopRotation() {
		rotater.stopRotation();
	}

	public void startRotation(Polyline shape, Point center) {
		rotater.startRotation(shape, center);
	}

	private class ContinuousShapeRotater implements Runnable {
		private static final int ANGLE = -2;
		private volatile boolean rotating;
		private Polyline shape;
		private Point rotationCenter;

		public void run() {
			while (rotating) {
				rotateShape(shape, rotationCenter, ANGLE);
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					;
				}
			}
		}

		public void stopRotation() {
			rotating = false;
		}

		public void startRotation(Polyline shape, Point center) {
			this.shape = shape;
			this.rotationCenter = center;
			rotating = true;
			new Thread(this).start();
		}
	}

	public List<javafx.scene.shape.Shape> rotateShapeWithTrail(Polyline fxShape) {
		double width = fxShape.getScene().getWidth();
		double height = fxShape.getScene().getHeight();
		Point rotationCenter = new Point(width / 8, height / 8);
		List<javafx.scene.shape.Shape> trailingShapes = new ArrayList<>();
		for (int i = 0; i <= 360; i += 360 / NUMBER_OF_TRAILING_SHAPES) {
			Polyline trailShape = new Polyline(toArray(fxShape.getPoints()));
			trailShape.setLayoutX(fxShape.getLayoutX());
			trailShape.setLayoutY(fxShape.getLayoutY());
			trailingShapes.add(trailShape);
			rotateShape(fxShape, rotationCenter);
		}
		return trailingShapes;
	}

	private double[] toArray(ObservableList<Double> shapePoints) {
		double[] points = new double[shapePoints.size()];
		int i = 0;
		for (Double point : shapePoints)
			points[i++] = point;
		return points;
	}

	private abstract class FXShapeTransformationTemplate {

		public final void transform(Polyline fxShape) {
			Shape shape = convertToShape(fxShape);
			transformShape(shape);
			updateShapePosition(fxShape, shape);
		}

		protected abstract void transformShape(Shape shape);

		private Shape convertToShape(Polyline shape) {
			return new Shape() {
				private List<Point> points = new ArrayList<Point>();

				{
					ObservableList<Double> fxShapePoints = shape.getPoints();
					for (int i = 0, j = 0; i < fxShapePoints.size() / 2; ++i, j += 2) {
						points.add(new Point(fxShapePoints.get(j), fxShapePoints.get(j + 1)));
					}
				}

				public List<Point> getEdges() {
					return points;
				}
			};
		}

		private void updateShapePosition(Polyline fxShape, Shape shape) {
			List<Point> points = shape.getEdges();
			ObservableList<Double> fxShapePoints = fxShape.getPoints();
			for (int i = 0, j = 0; i < fxShapePoints.size() / 2; ++i, j += 2) {
				Point point = points.get(i);
				fxShapePoints.set(j, point.getX());
				fxShapePoints.set(j + 1, point.getY());
			}
		}
	}
}
