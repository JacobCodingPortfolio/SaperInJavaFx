package pl.com.game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author JNartowicz
 */
public class Game extends Application {

    private Stage stage;
    private Scene scene;
    private AnchorPane mainContent;
    private GameBuilder gameBuilder;

    private static Game game;


    public void start(Stage primaryStage) throws Exception {
        game = Game.this;
        this.gameBuilder = new GameBuilder();
        Util.setElementSize(this.gameBuilder, Util.SCENE_WIDTH, Util.SCENE_HEIGHT);
        this.mainContent = new AnchorPane();
        this.mainContent.getChildren().add(gameBuilder);
        this.stage = primaryStage;
        this.scene = new Scene(mainContent, Util.SCENE_WIDTH, Util.SCENE_HEIGHT);
        this.stage.setResizable(false);
        this.stage.setScene(this.scene);
        this.stage.sizeToScene();
        this.stage.show();
    }

    public static Game getGame() {
        return game;
    }

    public AnchorPane getMainContent() {
        return mainContent;
    }
}
