package pl.com.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Region;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JNartowicz
 */
public class Util {

    public static final Double SCENE_HEIGHT = 500.0;
    public static final Double SCENE_WIDTH = 500.0;
    public static final Integer ZERO = 0;
    public static final Integer ICON_SIZE_PIXEL_SQUARE = 30;

    public static void initializeFxControls(Class<? extends Node> classWithNodes, Object instance, FXMLLoader loader) throws NoSuchFieldException, IllegalAccessException {

       Field[] fields = classWithNodes.getDeclaredFields();
       List<String> stringFields = new ArrayList<String>();
       for(Field field: fields){
           if(field.isAnnotationPresent(FXML.class)){
               stringFields.add(field.getName());
           }
       }

       for(String f: stringFields){
           Field f1 = classWithNodes.getDeclaredField(f);
           f1.setAccessible(true);
           try {
               f1.set(instance, getElementById(f, loader));
           } catch (IllegalAccessException e) {
               e.printStackTrace();
           }
       }

   }

    public static Object getElementById(String key, FXMLLoader loader){
       return loader.getNamespace().get(key);
   }

    public static void setElementSize(Region region, Double width, Double height){
       region.setMinSize(width, height);
       region.setPrefSize(width, height);
       region.setMaxSize(width, height);
   }

    public enum GameLevel{
        HI(0.3),
        MED(0.2),
        LOW(0.1);

        private Double bombsProportion;

       GameLevel(Double bombsProportion) {
           this.bombsProportion = bombsProportion;
       }

       public Double getBombsProportion() {
           return bombsProportion;
       }

   }

    public enum GameSize{
        BIG(14, 14),
        MED(10, 10),
        SMALL(7, 7);

        private Integer x;
        private Integer y;

       GameSize(Integer x, Integer y) {
           this.x = x;
           this.y = y;
       }

       public Integer getX() {
           return x;
       }

       public Integer getY() {
           return y;
       }
   }

}
