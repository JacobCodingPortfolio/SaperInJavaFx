package pl.com.game;

import com.sun.scenario.effect.impl.prism.PrImage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Random;

public class Icon extends AnchorPane {

    private FXMLLoader loader;

    @FXML
    private Button bombButton;
    @FXML
    private AnchorPane bombPane;

    //Game attributes
    private Integer x;
    private Integer y;

    private Integer probably = Util.PROBABLY;

    private Boolean bomb;

    public Icon() throws IOException {
        String s = "/" + Icon.this.getClass().getSimpleName() + ".fxml";
        this.loader = new FXMLLoader(getClass().getResource(s));
        this.bombPane = this.loader.load();
        try {
            Util.initializeFxControls(Icon.class, Icon.this, loader);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.getChildren().add(bombPane);
        this.setBomb();
    }

    private void setBomb() {
        Random random = new Random();
        Long i = random.nextLong();
        if((i % probably) == 0){
            this.bomb = true;
        } else {
            this.bomb = false;
        }
    }

    public Button getBombButton() {
        return bombButton;
    }

    public void setXY(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    public String getPosition(){
        return "X: " + String.valueOf(x) + ", Y: " + String.valueOf(y);
    }



}
