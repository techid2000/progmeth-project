package utility;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;

public class Utility {
	public static Point2D rotatePoint2D(Point2D a, double degree) {
		double sin = Math.sin(Math.toRadians(degree));
		double cos = Math.cos(Math.toRadians(degree));
		return new Point2D(a.getX()*cos - a.getY()*sin, a.getX()*sin + a.getY()*cos);
	}
	public static Point2D lerpPoint2D(Point2D a, Point2D b, double t) {
		return a.multiply(1-t).add(b.multiply(t));
	}
	public static Point2D clonePoint(Point2D a) {
		return new Point2D(a.getX(), a.getY());
	}
	public static Point2D timesAxis(Point2D a, Point2D b) {
		return new Point2D(a.getX()*b.getX(), a.getY()*b.getY());
	}
	public static Rotate pointToRotate(Point2D a) {
		if(a.getY() >= 0) {
			return new Rotate(Math.toDegrees(Math.acos(a.getX() / a.magnitude())));
		} else {
			return new Rotate(360 - Math.toDegrees(Math.acos(a.getX() / a.magnitude())));
		}
	}
}
