package gui;

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
import scene.MainMenuScene;

public class LoadingGUI extends StackPane {

	private Canvas loadingCanvas;
	private Transition loadingBar;
	private Transition textLoading;

	public LoadingGUI() {
		loadingCanvas = new Canvas(MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT);
		GraphicsContext gc = loadingCanvas.getGraphicsContext2D();
		this.getChildren().add(loadingCanvas);

		textLoading = new Transition() {
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
		textLoading.play();

		loadingBar = new Transition() {
			{
				setInterpolator(Interpolator.EASE_BOTH);
				setCycleDuration(new Duration(10000));
			}

			@Override
			protected void interpolate(double frac) {
				gc.setFill(Color.BLACK);
				if (frac >= 1) {
					textLoading.stop();
					loadingBar.stop();
					SystemCache.getInstance().sceneHolder.switchScene(
							SystemCache.getInstance().sceneHolder.mainMenuScene = new MainMenuScene());
				}
				if (frac <= 0.25) {
					gc.clearRect(0, MainApp.WINDOW_HEIGHT / 2, MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT / 2);
					gc.fillRoundRect(0.1 * MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT / 2,
							MainApp.WINDOW_WIDTH * (0.5 * 0.8) * 4 * frac, 30, 20, 20);
				}else if(0.50 <= frac && frac<=0.75) {
					gc.fillRoundRect((0.1 + 0.5 * 0.8) * MainApp.WINDOW_WIDTH - 20, MainApp.WINDOW_HEIGHT / 2 ,
							MainApp.WINDOW_WIDTH * (0.3 * 0.8) * 4 * (frac-0.5), 30, 20, 20);
				}else if(0.88 <= frac) {
					gc.fillRoundRect(((0.1 + 0.5 * 0.8) + (0.3*0.8)) * MainApp.WINDOW_WIDTH - 40, MainApp.WINDOW_HEIGHT / 2 ,
							MainApp.WINDOW_WIDTH * (0.2 * 0.8) * (1/0.12) * (frac-0.88), 30, 20, 20);
				}
//				
			}
		};

		loadingBar.play();

	}
}
