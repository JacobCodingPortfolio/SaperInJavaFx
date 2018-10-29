package pl.com.game;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author JNartowicz
 */
public class GameBuilder extends BorderPane {

    private List<Icon> icons;
    private MenuBar menuBar;
    private AnchorPane gameContent;
    private Integer bombsCount;

    private static Random random = new Random();
    private static boolean gameEnd = false;

    public GameBuilder() throws IOException {
        this.menuBar = new MenuBar();
        this.gameContent = new AnchorPane();
        this.setTop(menuBar);
        this.setCenter(gameContent);
        this.setBehaviour();
    }

    private void setBehaviour() {
        this.menuBar.initAction((x, y, bombs) -> {
            gameEnd = false;
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
                        try {
                            iconBehaviour(icon);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(checkWinGame()){
                            gameEnd = true;
                            this.menuBar.logAction(() -> "Wygrałeś :) Super :)");
                        }
                    });
                    hBox.getChildren().add(icon);
                }
            }
            Util.setElementSize(this.gameContent, Double.valueOf((Integer.valueOf(y)) * Util.ICON_SIZE_PIXEL_SQUARE),  Double.valueOf((Integer.valueOf(x)) * Util.ICON_SIZE_PIXEL_SQUARE));
            addComponentsToGameContent(vBox);
            initializeIconList();
            this.bombsCount = initializeBombs(bombs);
        });
    }

    /**
     * Sprawdzenie czy już nastapił koniec gry
     * @return - zwraca true - wygrana, false - póki co nie
     */
    private boolean checkWinGame() {
        for(Icon icon: icons){
            if(!icon.isUnHide() && !icon.hasBomb()){
                return false;
            }
        }
        return true;
    }

    private void iconBehaviour(Icon icon) throws Exception {

        if(gameEnd){ //Check we can do sth after click button
            return;
        }

        //Przejście po wszystkich ikonach
        if(icon == null){
            return;
        }
        ICON_LOOP:
        for(Icon icon1: icons){
            if(icon.equals(icon1)){
                icon.disableButton();
                checkIcon(icon);
                break ICON_LOOP;
            }
        }
    }

    private void checkIcon(Icon icon) throws Exception {
        if(icon.hasBomb()){
            gameEnd = true;
            showAllBombs();
            setGameEnd();
        } else {
            Integer bombsAround = countBombsAround(icon);
            if((Util.ZERO).equals(bombsAround)){
                showAllZeroFieldsNearCurrent(icon);
            } else {
                setBombsStyleAfterChoose(bombsAround, icon);
            }
        }
    }

    private void showAllZeroFieldsNearCurrent(Icon icon) throws Exception {
        setBombsStyleAfterChoose(0, icon); //Ustawienie 0 w polu z bombą
        List<Icon> iconsAround = getIconAround(icon);
        FOR:
        for(Icon ico: iconsAround){
            Integer bombCount = countBombsAround(ico);
            if(Util.ZERO.equals(bombCount)){
                if(ico.isUnHide()){
                    continue FOR;
                } else {
                    showAllZeroFieldsNearCurrent(ico);
                }
            } else {
                setBombsStyleAfterChoose(bombCount, ico); //Ustawienie 0 w polu z bombą
            }
        }
    }

    private Integer countBombsAround(Icon icon) throws Exception {
        Integer bombsAround = 0;
        for(Icon iconAround: getIconAround(icon)){
            if(iconAround.hasBomb()){
                ++bombsAround;
            }
        }
        return bombsAround;
    }

    private List<Icon> getIconAround(Icon icon) throws Exception {
        List<Icon> iconAroundList = new ArrayList<>();
        Integer iconX = icon.getX();
        Integer iconY = icon.getY();
        Icon gettedIcon;
        for(ItemPositionAround positionAround: ItemPositionAround.values()){
            gettedIcon = searchIconByXY(iconX + positionAround.moveX, iconY + positionAround.moveY);
            if(gettedIcon != null){
                iconAroundList.add(gettedIcon);
            }
        }
        return iconAroundList;
    }

    private void showAllBombs() throws Exception {
        for (Icon icon: icons){
            if (icon.hasBomb()){
                setBombsStyleAfterChoose(9, icon);
            }
        }
    }

    private void setGameEnd(){
        gameEnd = true;
        this.menuBar.logAction(() -> {
            return "Gra została zakończona :(";
        });
    }

    /**
     * In here we can choose a color of text in Button
     *
     * @param bombsAround - from 1 - 8 bombs around, while set 9 method get it like a bomb
     * @param icon - reference of icon clicked where method could insert the style
     * @throws Exception
     */
    private void setBombsStyleAfterChoose(Integer bombsAround, Icon icon) throws Exception {
        icon.setUnHide(true); //Ustawienie ikony jako odkryta
        String backGround = "#a6a6a6";
        switch (bombsAround){
            case 0:
                setIconContentAndColor(icon, ' ', "#66ff33" , backGround);
                break;
            case 1:
                setIconContentAndColor(icon, '1', "#009933", backGround);
                break;
            case 2:
                setIconContentAndColor(icon, '2', "#33ccff", backGround);
                break;
            case 3:
                setIconContentAndColor(icon, '3', "#0000ff", backGround);
                break;
            case 4:
                setIconContentAndColor(icon, '4', "#ffff66", backGround);
                break;
            case 5:
                setIconContentAndColor(icon, '5', "#ffcc66", backGround);
                break;
            case 6:
                setIconContentAndColor(icon, '6', "#ff9966", backGround);
                break;
            case 7:
                setIconContentAndColor(icon, '7', "#ff6600", backGround);
                break;
            case 8:
                setIconContentAndColor(icon, '8', "#ff0000", backGround);
                break;
            case 9:
                setIconContentAndColor(icon, 'B', "black", "red");
                break;
            default:
                throw new Exception("Błąd w metodzie: setBombsStyleAfterChoose nie można przypisać stylu to przycisku.");
        }
    }

    /**
     *
     * @param icon - node to change style
     * @param znak - insert one char to the button content
     * @param color - char color
     * @param backGroundColor - icon bg color
     */
    private void setIconContentAndColor(Icon icon, Character znak, String color, String backGroundColor){
        String style = icon.getBombButton().getStyle(); //Get the old button style, default
        icon.getBombButton().setStyle(style + "; -fx-text-fill: " + color + "; -fx-background-color: " + backGroundColor);
        icon.getBombButton().setText(String.valueOf(znak));
    }

    /**
     *
     * @param x - coordinate of X
     * @param y - coordinate of Y
     * @return - return the icon on the XY position in coordinate system
     * @throws Exception - if the null icon is impossible, method throw the exception
     */
    private Icon searchIconByXY(Integer x, Integer y) throws Exception {
        Integer maxX = 1;
        Integer maxY = 1;
        for(Icon icon: icons){
            if(icon.getX() > maxX){
                maxX = icon.getX();
            }
            if(icon.getY() > maxY){
                maxY = icon.getY();
            }
            if(icon.isCurrentPosition(x,y)){
                return icon;
            }
        }
        Integer startX = 1;
        Integer startY = 1;

        if((x < startX || x > maxX) || (y < startY || y > maxY)){
            return null;
        } else {
            throw new Exception("Błąd w szukaniu X: " + x + " i Y: " + x);
        }
    }

    /**
     *
     * @param count - ilość bomb którą włożymy to siatki
     */
    private Integer initializeBombs(Integer count){

        Integer iconCount = icons.size();
        Integer actualBombs = 0;
        boolean[] bombsTab = new boolean[iconCount];
        //W początkowych wartościach zawsze false
        int k = 0;
        LOOP:
        while(1==1){
            for(int p = 0; p < bombsTab.length ; p++){
                Integer randomNumberMod = random.nextInt() % 10;
                if(randomNumberMod.equals(0)){
                    if(bombsTab[p] != true){
                        bombsTab[p] = true;
                        actualBombs += 1;
                    }
                } else if (actualBombs >= count){
                    break LOOP;
                }
            }
        }
        //Add bomb to the icon
        for(int i = 0; i < iconCount; i++){
            if(bombsTab[i]){
                icons.get(i).setBomb();
            }
        }

        //Bombs checker
        int j = 0;
        for(Icon icon: icons){
            if(icon.hasBomb()){
                j++;
            }
        }
        //System.out.println("Wstationo: " + j + " bomb a powinno być: " + count);
        return actualBombs;
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
        icons = new ArrayList<>();
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
                    icons.add(iconCell);
                }
            }
        } else {
            System.out.println("Błąd inicjacji panelu gry.");
        }
    }

    private enum ItemPositionAround{
        LEFT_TOP(-1,1),
        CENTER_TOP(0,1),
        RIGHT_TOP(1,1),
        CENTER_LEFT(-1,0),
        CENTER_RIGHT(1,0),
        LEFT_BOTTOM(-1,-1),
        CENTER_BOTTOM(0,-1),
        RIGHT_BOTTOM(1,-1);

        private int moveX;
        private int moveY;

        ItemPositionAround(int moveX, int moveY) {
            this.moveX = moveX;
            this.moveY = moveY;
        }

        public int getMoveX() {
            return moveX;
        }

        public int getMoveY() {
            return moveY;
        }
    }

}
