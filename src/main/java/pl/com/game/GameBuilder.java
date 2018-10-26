package pl.com.game;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JNartowicz
 */
public class GameBuilder extends VBox {

    private List<Icon> icons;
    private MenuBar menuBar;
    private AnchorPane gameContent;

    public GameBuilder() throws IOException {
        this.menuBar = new MenuBar();
        this.gameContent = new AnchorPane();
        this.addComponents(menuBar, gameContent);
        this.setBehaviour();
    }

    private void setBehaviour() {
        this.menuBar.initAction((x, y) -> {
            this.removeAllFromGameContent();
            final VBox vBox = new VBox();
            for(int i=0; i < Integer.valueOf(x); i++){
                final HBox hBox = new HBox();
                vBox.getChildren().add(hBox);
                for(int j=0; j <Integer.valueOf(y); j++){
                    final Icon icon = new Icon();
                    icon.setXY(j+1, i+1);
                    icon.getBombButton().setOnMouseClicked(event -> {
                        iconBehaviour(icon);
                    });
                    hBox.getChildren().add(icon);
                }
            }
            addComponentsToGameContent(vBox);
            initializeIconList();
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

    private void addComponents(Node... nodes){
        for(Node node: nodes){
            this.getChildren().add(node);
        }
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
