package com.example.bubblebobble.PopUpWindows;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;


/**
 * Class InGamePopupWindow handles the pop-up window with details of rules
 * when the player cicks "Read Rules..." in the {@code MenuBar} in the game.
 * The world can only update when the pop-up window is closed.
 */
public class InGamePopup {

    /**
     * Initialize a new {@code Stage}, define the root of the stage's scene to
     * be a {@code VBox}. Define a {@code Text} of which the text is the details
     * of the rules, then add it to the root.
     */
    public static void showWindow() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        VBox root = new VBox(20);

        Image image = new Image(Objects.requireNonNull(HighScorePopupWindow.class.getResourceAsStream("/Pic/SceneBackgrounds/rules.jpg")),700,600,false,false);
        root.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(700,600,false,false,false,false))));

        Text text=new Text();
        text.setFont(Font.font("Vernada", FontWeight.BOLD,23));

        text.setText("\n\n       Use keyboard to control your hero:\n" +
                "       Left & Right -- move towards left or right\n" +
                "       Up -- jump\n" +
                "       Space -- when holding, your moving speed increases\n" +
                "       Q -- get shield\n" +
                "       W -- charge bubble, which can catch all the enemies\n               at one time; the charging of bubble requires\n           long cool down time\n" +
                "       E -- shoot projectile, which can catch an enemy or \n               beat an enemy's projectile at one time");
        text.setLineSpacing(18);

        root.getChildren().add(text);

        Scene scene = new Scene(root, 700, 600);
        window.setTitle("Rules");
        window.setScene(scene);
        window.show();

        window.setOnCloseRequest(windowEvent -> {
            InteractableWorld.getWorldInstance().setFrozen(false);});
    }
}
