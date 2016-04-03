package md.utm.labs.gc.shapes;

import java.util.Arrays;
import java.util.List;

public class Triangle implements Shape {

	private Point left;
	private Point top;
	private Point right;

	public Triangle(Point left, Point top, Point right) {
		this.left = left;
		this.top = top;
		this.right = right;
	}

	@Override
	public List<Point> getEdges() {
		return Arrays.asList(left, top, right);
	}
}
