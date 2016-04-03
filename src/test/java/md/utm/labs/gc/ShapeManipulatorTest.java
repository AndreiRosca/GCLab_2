package md.utm.labs.gc;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import md.utm.labs.gc.manipulator.ShapeManipulator;
import md.utm.labs.gc.shapes.Point;
import md.utm.labs.gc.shapes.Shape;
import md.utm.labs.gc.shapes.Triangle;

public class ShapeManipulatorTest {

	ShapeManipulator manipulator = ShapeManipulator.newInstance();

	@Test
	public void canInstantiateShapeManipulator() {
		assertNotNull(manipulator);
	}

	@Test
	public void canTranslateHorizontallyAPoint() {
		Shape shape = new Triangle(new Point(-5, 0), new Point(0, 10), new Point(5, 0));
		manipulator.translateHorizontallyBy(shape, 10);
		assertThat(shape.getEdges(), is(asList(new Point(5, 0), new Point(10, 10), new Point(15, 0))));
	}

	@Test
	public void canTranslateVerticallyAPoint() {
		Shape shape = new Triangle(new Point(-5, 0), new Point(0, 10), new Point(5, 0));
		manipulator.translateVerticallyBy(shape, 10);
		assertThat(shape.getEdges(), is(asList(new Point(-5, 10), new Point(0, 20), new Point(5, 10))));
	}

	@Test
	public void canScaleVerticallyAPoint() {
		Shape shape = new Triangle(new Point(-5, 1), new Point(0, 11), new Point(5, 1));
		manipulator.scaleVerticallyBy(shape, 10);
		assertThat(shape.getEdges(), is(asList(new Point(-5, 10), new Point(0, 110), new Point(5, 10))));
	}

	@Test
	public void canScaleHorizontallyAPoint() {
		Shape shape = new Triangle(new Point(-5, 1), new Point(1, 11), new Point(5, 1));
		manipulator.scaleHorizontallyBy(shape, 10);
		assertThat(shape.getEdges(), is(asList(new Point(-50, 1), new Point(10, 11), new Point(50, 1))));
	}
	
	@Test
	public void canRotateAPoint() {
		Shape shape = new Triangle(new Point(1, 0), new Point(0, 1), new Point(-1, 0));
		manipulator.rotateBy(shape, new Point(0, 0), 90);
		assertThat(shape.getEdges(), isCloseTo(asList(new Point(0, 1), new Point(-1, 0), new Point(0, -1))));
	}
	
	@Test
	public void canReflectAShapeAroundTheLineWithPositiveSlope() {
		Shape shape = new Triangle(new Point(2, 1), new Point(2, 3), new Point(3, 1));
		manipulator.reflectAround(shape, 1);
		assertThat(shape.getEdges(), isCloseTo(asList(new Point(1, 2), new Point(3, 2), new Point(1, 3))));
	}
	
	@Test
	public void canReflectAShapeAroundTheLineWithNegativeSlope() {
		Shape shape = new Triangle(new Point(-1, -1), new Point(2, -1), new Point(-2, 1));
		manipulator.reflectAround(shape, -1);
		assertThat(shape.getEdges(), isCloseTo(asList(new Point(1, 1), new Point(1, -2), new Point(-1, 2))));
	}
	
	private Matcher<List<Point>> isCloseTo(List<Point> points) {
		return new IsCloseToMatcher(points);
	}
	
	private static class IsCloseToMatcher extends BaseMatcher<List<Point>> {
		private static final double PRECISION = 0.00001;
		private List<Point> points;

		public IsCloseToMatcher(List<Point> points) {
			this.points = points;
		}
		
		@Override
		public boolean matches(Object other) {
			if (!(other instanceof List))
				return false;
			@SuppressWarnings("unchecked")
			List<Point> otherPoints = (List<Point>) other;
			if (points.size() != otherPoints.size())
				return false;
			for (int i = 0; i < points.size(); ++i) {
				Point first = points.get(i);
				Point second = otherPoints.get(i);
				if (Math.abs(first.getX() - second.getX()) > PRECISION)
					return false;
				if (Math.abs(first.getY() - second.getY()) > PRECISION)
					return false;
			}
			return true;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText(points.toString());
		}
	}
}
