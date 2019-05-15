package utility;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;

public class Utility {
	public static Point2D rotatePoint2D(Point2D a, double degree) {
		double sin = Math.sin(Math.toRadians(degree));
		double cos = Math.cos(Math.toRadians(degree));
		return new Point2D(a.getX()*cos - a.getY()*sin, a.getX()*sin + a.getY()*cos);
	}
	public static Point2D lerpPoint2D(Point2D a, Point2D b, double t) {
		return a.multiply(1-t).add(b.multiply(t));
	}
	public static Point2D moveTowardsPoint2D(Point2D a, Point2D b, double t) {
		if(a.distance(b) <= t) return b;
		return a.add(b.subtract(a).normalize().multiply(t));
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
	public static Point2D rotateToDirection(Rotate r) {
		return new Point2D(Math.cos(Math.toRadians(r.getAngle())), Math.sin(Math.toRadians(r.getAngle())));
	}
	
	//Brute force (BFS)
	public static List<Pair<Integer,Integer>> Pathfinding(boolean[][] obstacleMap, Pair<Integer,Integer> source, Pair<Integer,Integer> target) {
		source = new Pair<Integer,Integer>(source.getValue(), source.getKey());
		target = new Pair<Integer,Integer>(target.getValue(), target.getKey());

		LinkedList<Pair<Integer,Integer>> list = new LinkedList<>();
		Queue<Pair<Integer,Integer>> queue = new LinkedList<>();
		Stack<Pair<Integer,Integer>> stack = new Stack<>();

		Pair[][] from = new Pair[obstacleMap.length][obstacleMap[0].length];

		if(obstacleMap[source.getKey()][source.getValue()] || obstacleMap[target.getKey()][target.getValue()]) return list;
		from[source.getKey()][source.getValue()] = new Pair<Integer, Integer>(-1, -1);
		queue.add(new Pair<Integer,Integer>(source.getKey(), source.getValue()));
		
		
		final int dir[][] = {{0,1},{0,-1},{1,0},{-1,0},{-1,1},{-1,-1},{1,1},{1,-1}};
		
		while(!queue.isEmpty()) {
			Pair<Integer,Integer> top = queue.poll();
			Pair<Integer,Integer> push = new Pair<Integer,Integer>(0,0);
			if(top.getKey() == target.getKey() && top.getValue() == target.getValue()) {
				while(top.getKey() != -1 && top.getValue() != -1) {
					stack.add(top);
					top = new Pair<Integer,Integer>((int)from[top.getKey()][top.getValue()].getKey(), (int)from[top.getKey()][top.getValue()].getValue());
				}
				break;
			}
			for(int i = 0; i < 8; i++) {
				push = new Pair<Integer,Integer>(top.getKey() + dir[i][0], top.getValue() + dir[i][1]);
				if(push.getKey() < 0 || push.getKey() >= obstacleMap.length || push.getValue() < 0 || push.getValue() >= obstacleMap[0].length ||
						obstacleMap[push.getKey()][push.getValue()] || from[push.getKey()][push.getValue()] != null) continue;
				from[push.getKey()][push.getValue()] = top;
				queue.add(push);
			}
		}
		
		Pair<Integer,Integer> newDelta = new Pair<Integer,Integer>(0,0);
		Pair<Integer,Integer> delta = new Pair<Integer,Integer>(0,0);
		while(!stack.isEmpty()) {
			if(list.size() > 0) {
				newDelta = new Pair<Integer,Integer>(stack.peek().getKey() - list.getLast().getValue(),
						stack.peek().getValue() - list.getLast().getKey());
				if(newDelta.getKey() == delta.getKey() && newDelta.getValue() == delta.getValue()) {
					list.removeLast();
				}
				delta = newDelta;
			}
			list.add(new Pair<Integer,Integer>(stack.peek().getValue(), stack.peek().getKey()));
			stack.pop();
		}
		return list;
	}
}
