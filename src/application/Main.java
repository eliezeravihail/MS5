package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new Thread(()->{
			try {
				speaker.speak();
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}).start();
//		new Thread(()->{
//			try {
//				listener.listen();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//			}
//		}).start();
		launch(args);
	}
}
