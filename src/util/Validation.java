package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class Validation {

    public static Object validate(LinkedHashMap<TextField, Pattern> map, JFXButton btn ){
        for (TextField textFieldKey:map.keySet()){
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()){
                if (! textFieldKey.getText().isEmpty()){
                    textFieldKey.setStyle("-fx-text-fill : red");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-text-fill:white");
        }
        btn.setDisable(false);
        return true;

        }

}


