package pl.com.game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JNartowicz
 */
public class Util {

   public static final Integer PROBABLY = 5;

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

}
