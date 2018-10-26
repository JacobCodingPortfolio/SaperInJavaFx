package pl.com.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * @author JNartowicz
 */
public class MenuBar extends AnchorPane {

    private FXMLLoader loader;

    @FXML
    private AnchorPane mainAnchorMenu;
    @FXML
    private Button newGame;
    @FXML
    private TextField xContainer;
    @FXML
    private TextField yContainer;
    @FXML
    private TextField logContainer;

    public MenuBar() throws IOException {
        String s = "/" + MenuBar.this.getClass().getSimpleName() + ".fxml";
        this.loader = new FXMLLoader(getClass().getResource(s));
        this.mainAnchorMenu = this.loader.load();
        try {
            Util.initializeFxControls(MenuBar.class, MenuBar.this, loader);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.getChildren().add(mainAnchorMenu);
    }

    public void initAction(InitializeGameAction initializeGameAction){
        newGame.setOnMouseClicked(event ->{
            try {
                initializeGameAction.initNewGameAction(xContainer.getText(), yContainer.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void logAction(Logging logging){
        logContainer.setText(logging.info());
    }

    @FunctionalInterface
    public interface InitializeGameAction{
        void initNewGameAction(String x, String y) throws IOException;
    }

    @FunctionalInterface
    public interface Logging{
        String info();
    }
}
