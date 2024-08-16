package com.example.bubblebobble.Controllers;

import com.example.bubblebobble.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class EndScreenController is the controller of "endScreen.fxml"
 * file to achieve MVC pattern.
 * It controls two {@code Button} objects which allow the player to exit or
 * replay the game.
 */
public class EndController {
    @FXML
    private Button exitButton;

    @FXML
    private Button replayButton;


    /**
     * If the player click {@code replayButton}, then the scene's root of stage
     * in class {@code Main} will be reset which allows the player to play
     * again from the beginning.
     * @param event Mouse event, happens when the player clicks the button.
     * @throws IOException
     */
    @FXML
    void onClickReplay(MouseEvent event) throws IOException {
        Main.setStartRoot();

    }

    /**
     * If the player click {@code exitButton}, then the stage that the button
     * is on will be closed.
     * @param event Mouse event, happens when the player clicks the button.
     */
    @FXML
    void onClickExit(MouseEvent event) {
        ((Stage)exitButton.getScene().getWindow()).close();
    }

}
