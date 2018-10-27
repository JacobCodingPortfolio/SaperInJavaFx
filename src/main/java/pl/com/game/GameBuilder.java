package pl.com.game;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JNartowicz
 */
public class GameBuilder extends BorderPane {

    private List<Icon> icons;
    private MenuBar menuBar;
    private AnchorPane gameContent;

    public GameBuilder() throws IOException {
        this.menuBar = new MenuBar();
        this.gameContent = new AnchorPane();
        this.setTop(menuBar);
        this.setCenter(gameContent);
        this.setBehaviour();
    }

    private void setBehaviour() {
        this.menuBar.initAction((x, y, bombs) -> {
            this.removeAllFromGameContent();
            final VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            for(int i=0; i < y; i++){
                final HBox hBox = new HBox();
                vBox.getChildren().add(hBox);
                for(int j=0; j < x; j++){
                    final Icon icon = new Icon();
                    icon.setXY(j+1, i+1);
                    icon.getBombButton().setOnMouseClicked(event -> {
                        iconBehaviour(icon);
                    });
                    hBox.getChildren().add(icon);
                }
            }
            Util.setElementSize(this.gameContent, Double.valueOf((Integer.valueOf(y)) * Util.ICON_SIZE_PIXEL_SQUARE),  Double.valueOf((Integer.valueOf(x)) * Util.ICON_SIZE_PIXEL_SQUARE));
            addComponentsToGameContent(vBox);
            initializeIconList();
            initializeBombs(bombs);
        });
    }

    private void iconBehaviour(Icon icon){
        //Przejście po wszystkich ikonach
        if(icon == null){
            return;
        }
        ICON_LOOP:
        for(Icon icon1: icons){
            if(icon.equals(icon1)){

                break ICON_LOOP;
            }
        }
    }

    private void initializeBombs(Integer count){
        System.out.println("Znalazło bomby: " + String.valueOf(count));
    }

    private void addComponentsToGameContent(Node... nodes){
        for(Node node: nodes){
            this.gameContent.getChildren().add(node);
        }
    }

    private void removeAllFromGameContent(){
        List<Node> nodes = new ArrayList<>();
        for(Node node: this.gameContent.getChildren()){
            nodes.add(node);
        }
        this.gameContent.getChildren().removeAll(nodes);
    }

    private void initializeIconList(){
        ObservableList<Node> gameContentNodes = this.gameContent.getChildren();
        if(gameContentNodes.size() == 1){
            Pane pane = (Pane) gameContentNodes.get(0); //Pierwszy element z listy
            //Tworzenie pętli która przejdzie po wierszach
            ObservableList<Node> rowsIcons = pane.getChildren();
            for(Node node: rowsIcons){
                //Teraz będzie przechodzić po kolumnach
                Pane pane1 = (Pane) node;
                ObservableList<Node> columnsIcon = pane1.getChildren();
                for(Node icon: columnsIcon){
                    Icon iconCell = (Icon) icon;
                    if(icons == null){
                        icons = new ArrayList<>();
                    }
                    icons.add(iconCell);
                }
            }
        } else {
            System.out.println("Błąd inicjacji panelu gry.");
        }
    }

}
