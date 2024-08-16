package com.example.bubblebobble.Controllers;

import com.example.bubblebobble.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;


/**
 * Class RulesController is the controller of "rules.fxml" file to achieve
 * MVC pattern.
 * It controls one {@code Text} object and one {@code Label} object which allow
 * the player to view more details of the rule, or go back to the start page.
 */
public class RulesController {

    @FXML
    private Label goBack;

    @FXML
    private Text seeMore;

    @FXML
    private Text text3;

    @FXML
    private Text text4;

    @FXML
    private Text text1;

    @FXML
    private Text intro;

    @FXML
    private Text text2;

    @FXML
    private Text text5;

    @FXML
    private Text ruleText;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    /**
     * When the player clicks {@code goBack}, the scene's root of the stage
     * in {@code Main} will be reset to the start page.
     * @param event Mouse event, happens when the player clicks the button.
     * @throws IOException
     */
    @FXML
    void clickGoBack(MouseEvent event) throws IOException {
        Main.setStartRoot();
    }


    /**
     * When the player clicks {@code seeMore}, the contents of texts already
     * on the scene will be cleared while the images in imageviews will be null.
     * Then the text of {@code ruleText} will be set to show.
     * @param event Mouse event, happens when the player clicks the button.
     */
    @FXML
    void clickSeeMore(MouseEvent event) {
        intro.setText("");
        text1.setText("");
        text2.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");

        image1.setImage(null);
        image2.setImage(null);
        image3.setImage(null);
        image4.setImage(null);

        ruleText.setText("Use keyboard to control your hero:\nLeft & Right -- move towards left or right\nUp -- jump\n" +
                "Space -- when holding, your moving speed increases\n" +
                "Q -- get shield\n" +
                "W -- charge bubble, which can catch all the enemies at one time; the \n      charging of bubble requires long cool down time\n" +
                "E -- shoot projectile, which can catch an enemy or beat an enemy's \n      projectile at one time");
    }

}
