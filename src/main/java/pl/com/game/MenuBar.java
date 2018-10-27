package pl.com.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private ComboBox levelMenu;
    @FXML
    private ComboBox sizeMenu;
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
        this.fillComboBoxes();
    }

    private void fillComboBoxes() {
        //Remove all objects
        this.levelMenu.getItems().removeAll();
        this.sizeMenu.getItems().removeAll();

        for(Util.GameLevel level: Util.GameLevel.values()){
            levelMenu.getItems().add(level);
        }

        for(Util.GameSize size: Util.GameSize.values()){
            sizeMenu.getItems().add(size);
        }
    }

    public void initAction(InitializeGameAction initializeGameAction){
        newGame.setOnMouseClicked(event ->{
            try {
                if(levelMenu.getValue() != null && sizeMenu.getValue() != null){
                    Util.GameSize size = (Util.GameSize) sizeMenu.getValue();
                    Util.GameLevel level = (Util.GameLevel) levelMenu.getValue();
                    Double bombCountDouble = (level.getBombsProportion() * (size.getX() * size.getY()));
                    Integer bombCount = bombCountDouble.intValue();
                    initializeGameAction.initNewGameAction(size.getX(), size.getY(), bombCount);
                    logAction(() -> {
                        return "Zbudowano grę :) Powodzenia!";
                    });
                } else {
                    logAction(() -> {
                        return "Wybierz opcje ...";
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void logAction(Logging logging){
        logContainer.setText(logging.info());
    }

    @FunctionalInterface
    public interface InitializeGameAction{
        void initNewGameAction(Integer x, Integer y, Integer bombs) throws IOException;
    }

    @FunctionalInterface
    public interface Logging{
        String info();
    }
}
