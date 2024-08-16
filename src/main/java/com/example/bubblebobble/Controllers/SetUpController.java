package com.example.bubblebobble.Controllers;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


/**
 * SetUpController class is the controller for "setUpScreen.fxml" to
 * achieve MVC pattern.
 * It controls an {@code TextField} object which allows the player to input the
 * name, a {@code ColorPicker} object  which allows the player to choose a
 * background color for the game. It also controls three {@code CheckBox} objects
 * which enable the player to choose different maps for the game.
 */
public class SetUpController {

    @FXML
    private CheckBox defaultBackground;

    @FXML
    private ColorPicker colorPick;

    @FXML
    private CheckBox classic;

    @FXML
    private CheckBox maze;

    @FXML
    private CheckBox barbie;

    @FXML
    private TextField nameText;

    @FXML
    private Button confirmBT;


    /**
     * When the player tick {@code classic}, other checkboxes being selected
     * will be set not to be selected.
     * @param event Event, happens when the player select the checkbox.
     */
    @FXML
    void chooseClassic(Event event) {
        maze.setSelected(false);
        barbie.setSelected(false);
    }

    /**
     * When the player tick {@code maze}, other checkboxes being selected
     * will be set not to be selected.
     * @param event Event, happens when the player select the checkbox.
     */
    @FXML
    void chooseMaze(Event event) {
        classic.setSelected(false);
        barbie.setSelected(false);
    }

    /**
     * When the player tick {@code barbie}, other checkboxes being selected
     * will be set not to be selected.
     * @param event Event, happens when the player select the checkbox.
     */
    @FXML
    void chooseBarbie(Event event) {
        classic.setSelected(false);
        classic.setSelected(false);
    }

    /**
     * When the player chooses a color in {@code colorPick}, set
     * {@code defaultBackground} to be not selected.
     * @param event Event, happens when the player select a color in {@code colorPick}.
     */
    @FXML
    void chooseColor(Event event) {
        defaultBackground.setSelected(false);
    }


    /**
     * Confirm the setup choices.
     * If {@code confirmBT} is clicked, world of the map and background
     * color chosen will be built. And the name of the player will be stored.
     * The root of the scene of the stage in class {@code Main} will be set
     * to the game root.
     * @param event Mouse event, happens when the player click {@code confirmBT}.
     * @throws IOException
     */
    @FXML
    void confirm(MouseEvent event) throws IOException {
        if(classic.isSelected()){
            InteractableWorld.getWorldInstance().restart();
            InteractableWorld.getWorldInstance().setMap("Classic.txt");
        }
        else if(maze.isSelected()){
            InteractableWorld.getWorldInstance().restart();
            InteractableWorld.getWorldInstance().setMap("Maze.txt");
        }
        else {
            InteractableWorld.getWorldInstance().restart();
            InteractableWorld.getWorldInstance().setMap("Barbie.txt");
        }

        if(!nameText.getText().equalsIgnoreCase("")) {
            Main.setUserName(nameText.getText());
        }
        if(!defaultBackground.isSelected()) {
            InteractableWorld.getWorldInstance().getMapFactory().setBackgroundChoice(colorPick.getValue());
        }
        Main.setGameRoot();
    }

}
