package com.example.bubblebobble.Controllers;

import com.example.bubblebobble.PopUpWindows.HighScorePopupWindow;
import com.example.bubblebobble.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;


/**
 * Class StartController is the controller of startScreen.fxml"
 * file to achieve MVC pattern.
 * It controls two {@code Button} objects which allow the player to start or
 * see the rules, and a choice to see the high score list.
 */
public class StartController {

    @FXML
    private Text highScore;

    @FXML
    private Button startButton;

    @FXML
    private Button ruleButton;

    /**
     * If the player clicks {@code startButton}, the scene's root of the stage
     * in class {@code Main} will be set to allow the player to choose different
     * setup choices.
     * @param event Mouse event, happens when the player clicks the button.
     * @throws IOException
     */
    @FXML
    void clickStart(MouseEvent event) throws IOException {
        Main.setSetUpRoot();
    }

    /**
     * If the player clicks {@code ruleButton}, the scene's root of the stage
     * in class {@code Main} will be set to a root which allows the player to
     * read rules.
     * @param event Mouse event, happens when the player clicks the button.
     * @throws IOException
     */
    @FXML
    void clickRule(MouseEvent event) throws IOException {
        Main.setRuleRoot();
    }

    /**
     * If the player clicks {@code highScore}, a pop-up window showing the
     * high scores will be displayed.
     * @param event Mouse event, happens when the player clicks the button.
     * @throws IOException
     */
    @FXML
    void viewHighScore(MouseEvent event) {
        HighScorePopupWindow.showWindow();
    }
}