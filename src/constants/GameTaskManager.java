package constants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.animation.Transition;

public class GameTaskManager {
	public static List<Transition> transitions = new ArrayList<Transition>();
	public static List<Thread> threads = new ArrayList<Thread>();
	public static List<AnimationTimer> animationTimers = new ArrayList<AnimationTimer>();
	public static List<Timeline> timelines = new ArrayList<Timeline>();

	public static void clearBackgroundTasks() {
		Iterator<Transition> iter1 = transitions.iterator();
		while (iter1.hasNext()) {
			iter1.next().stop();
		}
		Iterator<AnimationTimer> iter2 = animationTimers.iterator();
		while (iter2.hasNext()) {
			iter2.next().stop();
		}
		Iterator<Thread> iter3 = threads.iterator();
		while (iter3.hasNext()) {
			iter3.next().interrupt();
		}
		Iterator<Timeline> iter4 = timelines.iterator();
		while (iter4.hasNext()) {
			iter4.next().stop();
		}
		transitions.clear();
		threads.clear();
		animationTimers.clear();
		timelines.clear();
	}
}