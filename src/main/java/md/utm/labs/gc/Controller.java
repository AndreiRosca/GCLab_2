package md.utm.labs.gc;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import md.utm.labs.gc.manipulator.ShapeManipulator;
import md.utm.labs.gc.shapes.Point;

public class Controller {

	private FXShapeTransformer shapeTransformer = new FXShapeTransformer(ShapeManipulator.newInstance());

	@FXML
	private MenuItem translateMenuItem;

	@FXML
	private MenuItem scaleMenuItem;

	@FXML
	private MenuItem rotateMenuItem;

	@FXML
	private MenuItem clearMenuItem;

	@FXML
	private MenuItem drawMenuItem;

	@FXML
	private MenuItem quitMenuItem;

	@FXML
	private MenuItem rotateWithTrailMenuItem;
	
	@FXML
	private MenuItem negativeSlopeReflectionMenuItem;

	@FXML
	private MenuItem positiveSlopeReflectionMenuItem;
	
	@FXML
	private MenuItem drawAirplaneMenuItem;
	
	@FXML
	private MenuItem startAirplaneMenuItem;
	
	@FXML
	private MenuItem stopAirplaneMenuItem;
	
	@FXML
	private Polyline squareShape;

	@FXML
	private Polyline triangleShape;
	
	@FXML
	private Polyline airplane;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	public void translateShapes() {
		shapeTransformer.translateShape(squareShape);
		shapeTransformer.translateShape(triangleShape);
		swapShapeColors();
	}

	@FXML
	public void scaleShapes() {
		shapeTransformer.scaleShape(squareShape);
		shapeTransformer.scaleShape(triangleShape);
		swapShapeColors();
	}

	@FXML
	public void rotateShapes() {
		Point center = new Point(50, 100);
		shapeTransformer.rotateShape(squareShape, center);
		shapeTransformer.rotateShape(triangleShape, center);
		swapShapeColors();
	}

	@FXML
	public void clearShapes() {
		squareShape.setVisible(false);
		triangleShape.setVisible(false);
		airplane.setVisible(false);
		ObservableList<Node> nodes = anchorPane.getChildren();
		if (nodes.size() > 3)
			nodes.remove(3, nodes.size());
	}

	@FXML
	public void drawShapes() {
		squareShape.setVisible(true);
		triangleShape.setVisible(true);
		swapShapeColors();
	}

	@FXML
	public void closeApplication() {
		shapeTransformer.stopRotation();
		System.exit(0);
	}

	@FXML
	public void rotateShapesWithTrail() {
		triangleShape.setVisible(false);
		squareShape.setFill(null);
		List<Shape> shapes = shapeTransformer.rotateShapeWithTrail(squareShape);
		anchorPane.getChildren().addAll(shapes);
	}
	
	@FXML
	public void positiveSlopeReflection() {
		shapeTransformer.reflectShape(squareShape, 1);
		triangleShape.setVisible(false);
	}
	
	@FXML
	public void negativeSlopeReflection() {
		shapeTransformer.reflectShape(squareShape, -1);
		triangleShape.setVisible(false);	
	}
	
	@FXML
	public void drawAirplane() {
		squareShape.setVisible(false);
		triangleShape.setVisible(false);
		airplane.setVisible(true);
	}
	
	@FXML
	public void startAirplane() {
		shapeTransformer.rotateShape(airplane, new Point(10, 10));
		airplane.setLayoutX(airplane.getLayoutX() + 100);
		airplane.setLayoutY(airplane.getLayoutY() + 100);
		shapeTransformer.startRotation(airplane, new Point(10, 40));
		startAirplaneMenuItem.setDisable(true);
		stopAirplaneMenuItem.setDisable(false);
	}
	
	@FXML
	public void stopAirplane() {
		shapeTransformer.stopRotation();
		startAirplaneMenuItem.setDisable(false);
		stopAirplaneMenuItem.setDisable(true);
	}

	private void swapShapeColors() {
		String squareColor = squareShape.getFill() != null ? squareShape.getFill().toString() : "blue";
		String triangleColor = triangleShape.getFill() != null ? triangleShape.getFill().toString() : "green";
		squareShape.setFill(Paint.valueOf(triangleColor));
		triangleShape.setFill(Paint.valueOf(squareColor));
	}
}
