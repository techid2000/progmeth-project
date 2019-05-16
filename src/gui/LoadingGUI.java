package gui;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import app.MainApp;
import constants.FontHolder;
import constants.SystemCache;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LoadingGUI extends StackPane {

	Canvas loadingCanvas;

	public LoadingGUI() {
		loadingCanvas = new Canvas(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
		GraphicsContext gc = loadingCanvas.getGraphicsContext2D();
		this.getChildren().add(loadingCanvas);

		Transition textLoaing = new Transition() {
			{
				setInterpolator(Interpolator.EASE_BOTH);
				setCycleCount(INDEFINITE);
				setCycleDuration(new Duration(2000));
			}

			@Override
			protected void interpolate(double frac) {
				gc.clearRect(0, 0, MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT / 2); // must fixed
				gc.setFill(Color.RED);
				gc.setFont(FontHolder.getInstance().font36);
				String text;
				if (frac < 0.25) {
					text = "Loading";
				} else if (frac < 0.5) {
					text = "Loading .";
				} else if (frac < 0.75) {
					text = "Loading . .";
				} else {
					text = "Loading . . .";
				}

				double font_width = Toolkit.getToolkit().getFontLoader().computeStringWidth(text,
						FontHolder.getInstance().font48);
				gc.fillText(text, MainApp.WINDOW_WIDTH / 2 - font_width / 2, MainApp.WINDOW_HEIGHT / 2 - 15);
			}
		};
		textLoaing.play();

		Transition loadingBar = new Transition() {
			{
				setInterpolator(Interpolator.EASE_BOTH);
				setCycleDuration(new Duration(5000));
			}

			@Override
			protected void interpolate(double frac) {
				gc.setFill(Color.BLACK);
				if (frac < 0.25 || frac > 0.75) {
					gc.clearRect(0, MainApp.WINDOW_HEIGHT / 2, MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
					gc.fillRect(MainApp.WINDOW_WIDTH / 10, MainApp.WINDOW_HEIGHT / 2 + 15,
							2 * frac * MainApp.WINDOW_WIDTH * 4 / 10, 30);
				}
			}
		};

		loadingBar.play();

	}
}
