package utility;

import javafx.geometry.Point2D;

public class Utility {
	public static Point2D rotatePoint2D(Point2D a, double degree) {
		double sin = Math.sin(Math.toRadians(degree));
		double cos = Math.cos(Math.toRadians(degree));
		return new Point2D(a.getX()*cos - a.getY()*sin, a.getX()*sin + a.getY()*cos);
	}
}
