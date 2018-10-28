package pl.com.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Icon extends AnchorPane {

    private FXMLLoader loader;

    @FXML
    private Button bombButton;
    @FXML
    private AnchorPane bombPane;

    //Game attributes
    private Integer x;
    private Integer y;

    private Boolean bomb;
    private boolean unHide;

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
    }

    public void setBomb() {
        this.bomb = true;
    }

    public Boolean hasBomb(){
        if(bomb == null){
            return false;
        } else if(bomb.equals(false)){
            return false;
        } else {
            return true;
        }
    }

    public Button getBombButton() {
        return bombButton;
    }

    public void setXY(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Boolean isCurrentPosition(Integer x, Integer y){
        if(this.x.equals(x) && this.y.equals(y)){
            return true;
        } else {
            return false;
        }
    }

    public void disableButton(){
        bombButton.setDisable(true);
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public boolean isUnHide() {
        return unHide;
    }

    public void setUnHide(boolean unHide) {
        this.unHide = unHide;
    }

    public String getItemPosString(){
        return "X: " + String.valueOf(this.x) + ", Y: " + String.valueOf(this.y);
    }

}
