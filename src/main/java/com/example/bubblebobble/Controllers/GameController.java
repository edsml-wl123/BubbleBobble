package com.example.bubblebobble.Controllers;

import com.example.bubblebobble.*;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.PopUpWindows.InGamePopup;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;


/**
 * GameController class is the controller of "game.fxml" file to achieve
 * MVC pattern.
 * <p>
 * It initializes the {@code canvas} in {@code world} with the one defined in the
 * "game.fxml" file. And it initializes a {@code Timeline} object which
 * constantly calls methods to update the world, draw game objects on {@code canvas},
 * and update the information on {@code infoPane}.
 * </p>
 * <p>
 * It controls the hero's behaviour when keyboard input is made on the canvas,
 * and a {@code MenuBar} object which allow the player to control the current
 * game or read rules.
 * </p>
 */
public class GameController {

    @FXML
    private Pane gamePane;

    @FXML
    private Pane infoPane;

    @FXML
    private Canvas canvas;

    @FXML
    private Text infoText;

    @FXML
    private Label chargeLabel;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem restart;

    @FXML
    private MenuItem goStartPage;

    @FXML
    private MenuItem readRules;

    private InteractableWorld world;
    private Hero hero;


    /**
     * Initializes {@code world} with the instance of {@code InteractableWorld},
     * set {@code canvas} in {@code world} with the one defined in the
     * "game.fxml" file. Set the background of {@code infoPane} according
     * to different map choice. And it initializes a {@code Timeline} object
     * which constantly calls methods to update the world, draw game objects
     * on {@code canvas}, and update the information on {@code infoPane}.
     */
    @FXML
    void initialize() {
        world= InteractableWorld.getWorldInstance();
        world.setCanvas(canvas);

        world.getMapFactory().startGame(world.getMap());
        hero=Hero.getInstance();

        String filePath=world.getMapFactory().infoPaneBackground();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(filePath)),200,680,false,false);
        infoPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(200,680,false,false,false,false))));


        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        actionEvent -> world.update()
                ),
                new KeyFrame(
                        Duration.ZERO,
                        actionEvent -> infoPaneUpdate()
                ),
                new KeyFrame(
                        Duration.ZERO,
                        actionEvent -> world.paintPureWorld(gc)

                ),
                new KeyFrame(
                        Duration.ZERO,
                        actionEvent -> world.paintObject(gc)

                ),
                new KeyFrame(
                        Duration.seconds(0.025)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    /**
     * Handles {@code hero}'s behavior when a key is pressed on {@code canvas}.
     * Call the corresponding method in the {@code currentState} of {@code hero}.
     * @param keyEvent Keyboard event, happens when a key is pressed when the canvas
     *                 is focus.
     */
    @FXML
    void keyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.RIGHT) {
            hero.getState().pressRight();
        }

        if (keyEvent.getCode() == KeyCode.LEFT) {
            hero.getState().pressLeft();
        }

        if (keyEvent.getCode() == KeyCode.UP) {
            hero.getState().pressUp();
        }

        if (keyEvent.getCode() == KeyCode.SPACE) {
            hero.getState().pressSpace();
        }

        if (keyEvent.getCode() == KeyCode.E) {
            hero.getState().pressE();
        }

        if(keyEvent.getCode()==KeyCode.W){
            hero.getState().pressW();
        }

        if (keyEvent.getCode() == KeyCode.Q) {
            hero.getState().pressQ();
        }

    }

    /**
     * Handles {@code hero}'s behavior when a key is released on {@code canvas}.
     * Call the corresponding method in the {@code currentState} of {@code hero}.
     * @param keyEvent Keyboard event, happens when a key is released when the canvas
     *                 is focus.
     */
    @FXML
    void keyRelease(KeyEvent keyEvent) {
        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());
        if (keyEvent.getCode() == KeyCode.RIGHT) {
            hero.getState().releaseRight();
        }

        if (keyEvent.getCode() == KeyCode.LEFT) {
            hero.getState().releaseLeft();
        }

        if (keyEvent.getCode() == KeyCode.SPACE) {
            hero.getState().releaseSpace();
        }

        if (keyEvent.getCode() == KeyCode.E) {
            hero.getState().releaseE();
        }

        if (keyEvent.getCode() == KeyCode.Q) {
            hero.getState().releaseQ();
        }

        if (keyEvent.getCode() == KeyCode.W) {
            hero.getState().releaseW();
        }
    }

    /**
     * When the {@code canvas} on the scene is clicked, set {@code canvas}
     * to be focus.
     * @param event Mouse event, happens when the player click the canvas.
     */
    @FXML
    void clickGame(MouseEvent event) {
        hero=Hero.getInstance();
        canvas.requestFocus();
    }


    /**
     * Update the text of {@code Text} nodes on {@code infoPane}.
     * This includes information about the current level, the left lives of hero,
     * the scores that the player has got and whether the hero is able to charge
     * a bubble.
     */
    public void infoPaneUpdate(){
        int life=world.getLifeHandler().getLife();
        if(life<0){life=0;}

        int level=world.getLevelFactory().getLevel();
        if(level<3){level++;}

        String info="Left Lives: "+life+"\nLevel: "+level+"\nScore: "+world.getScoreBoard().totalScore();
        String chargeInfo = null;

        if(hero.ableToCharge){
            chargeInfo="Able to charge!";
        }
        else {
            chargeInfo= "Charge CD: "+hero.getLeftChargeTime()+"s";
        }

        world.getMapFactory().InfoPaneFont(infoText);
        infoText.setLineSpacing(20);
        infoText.setText(info);

        chargeLabel.setText(chargeInfo);
        chargeLabel.setTextFill(Color.rgb(255, 204, 102, 1));
    }


    /**
     * When the player clicks {@code MenuItem} object {@code restart},
     * corresponding alert confirming whether the player is sure to restart
     * will be prompted.
     * @param event Action event, happens when the player clicks on the menu item.
     */
    @FXML
    void chooseRestart(ActionEvent event) {
        raiseConfirmAlert("","Are you sure to restart?\nYour score(now "+world.getScoreBoard().totalScore()+") will not be recorded.",new ButtonType("Yes", ButtonBar.ButtonData.YES),new ButtonType("Cancel", ButtonBar.ButtonData.NO),0);
    }

    /**
     * When the player clicks {@code MenuItem} object {@code goStartPage},
     * corresponding alert confirming whether the player is sure to go to the
     * start page will be prompted.
     * @param event Action event, happens when the player clicks on the menu item.
     */
    @FXML
    void chooseStartPage(ActionEvent event) {
        raiseConfirmAlert("","Are you sure to go to start page?",new ButtonType("Yes", ButtonBar.ButtonData.YES),new ButtonType("Cancel", ButtonBar.ButtonData.NO),1);

    }

    /**
     * When the player clicks {@code MenuItem} object {@code exit},
     * corresponding alert confirming whether the player is sure to exit
     * will be prompted.
     * @param event Action event, happens when the player clicks on the menu item.
     */
    @FXML
    void chooseExit(ActionEvent event) {
        raiseConfirmAlert("","Are you sure to exit?\nYour score(now "+world.getScoreBoard().totalScore()+") will not be recorded.",new ButtonType("Yes", ButtonBar.ButtonData.YES),new ButtonType("Cancel", ButtonBar.ButtonData.NO),2);

    }

    /**
     * When the player clicks {@code MenuItem} object {@code readRules},
     * the current world will be frozen and the pop-up window showing rules
     * will be displayed.
     * @param event Action event, happens when the player clicks on the menu item.
     */
    @FXML
    void chooseReadRules(ActionEvent event) {
        world.setFrozen(true);
        InGamePopup.showWindow();

    }


    /**
     * Show confirmation alert when the player tries to control the game.
     * If the player confirms to restart, the current game will restart from
     * level 1; if the player confirms to go back to the start page, the scene's
     * root of class {@code Main} will be set to the beginning of the game; if
     * the player confirms to exit, the current window will close.
     * Otherwise, if the player chooses cancel, the current world will stop
     * frozen and the game will continue.
     * @param title String used to set the title of the alert.
     * @param msg String that will be shown on the alert.
     * @param yesbt ButtonType of the button to confirm.
     * @param nobt ButtonType of the button to cancel.
     * @param choice The parameter that indicated which menu items in menu
     *               {@code control} is chosen by the player.
     */
    private void raiseConfirmAlert(String title, String msg, ButtonType yesbt, ButtonType nobt, int choice){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Yes",yesbt,nobt);
        world.setFrozen(true);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.setOnCloseRequest(e -> {
            ButtonType result = alert.getResult();
            if (result != null && result == yesbt) {
                if(choice==0){
                    world.restartCurrentMap();
                }
                else if(choice==1){
                    try {
                        Main.setStartRoot();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if(choice==2){
                    ((Stage)gamePane.getScene().getWindow()).close();
                }
            }
            else if(result==nobt){
                world.setFrozen(false);
            }
        });
        alert.show();
    }
}
