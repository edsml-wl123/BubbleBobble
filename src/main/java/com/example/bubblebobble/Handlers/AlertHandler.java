package com.example.bubblebobble.Handlers;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import java.io.IOException;


/**
 * AlertHandler class handles everything when an alert is to be prompted.
 * <p>
 * An alert will be prompt when the hero dies, the level of the game increases,
 * and when the game is over(includes the hero loses all of its lives or the
 * hero has beaten all the enemies.
 * </p>
 */
public class AlertHandler {
    InteractableWorld world;
    Hero hero;

    /**
     * Initialize the value of {@code world} and {@code hero}.
     */
    public AlertHandler(){
        world=InteractableWorld.getWorldInstance();
        hero=Hero.getInstance();

    }

    /**
     * Call the alert according to different situations.
     * When the enemies and boss in the current level are all beaten,
     * alert informing wining will be prompted when the level is 3; otherwise,
     * alert informing the increase of level will be prompted.
     */
    public void callAlert() {
        String alertMsg;

        if (world.getEnemies().size() == 0&& world.getBoss()==null) {
            if(world.getLevelFactory().getLevel()==3){
                alertMsg="YOU WIN!!\nGet "+world.getScoreBoard().totalScore()+" marks!\nDo you want to play again?";
                gameOverAlert(alertMsg);
            }
            else {
                alertMsg = "You have beated all the enemies in this level.\nGo to next level! :D";
                levelAlert(alertMsg);
            }
        }
    }


    /**
     * Call the alert when the hero dies.
     * If the left lives of hero is more than 0, alert with specific message
     * will be prompted. If the left lives of hero is 0, then alert warning the
     * player of the last chance will be prompted. Otherwise, an alert informing
     * the player of game over will be prompted.
     * @param information The message that will be shown in the alert if the
     *                    hero has 1 or more left lives.
     */
    public void callAlert(String information) {
        world.setFrozen(true);

        String alertMsg;
        if(world.getLifeHandler().getLife() >0){
            if(world.getLifeHandler().getLife() ==1){
                alertMsg="Be careful. This is your last chance!";
            }
            else {
                alertMsg = "You are killed by "+information+"!\nBe careful, " + (world.getLifeHandler().getLife() - 1) + " lives left!";
            }
            loseLifeAlert(alertMsg);
        }
        else if(world.getLifeHandler().getLife() ==0){
            alertMsg="You are dead :(\nYour highest score is "+(world.getScoreBoard().totalScore())+"\nDo you want to play again?";
            gameOverAlert(alertMsg);
        }
    }


    /**
     * The alert to be shown when the hero loses 1 life.
     * @param information The message that might be in the content of the alert.
     */
    public void loseLifeAlert(String information){
        world=InteractableWorld.getWorldInstance();
        world.setFrozen(true);
        ButtonType bt= new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "OK",
                bt);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(information);

        alert.setOnCloseRequest(e -> {
            ButtonType result = alert.getResult();
            if (result != null && result == bt) {
                world.setFrozen(false);
            }
        });
        alert.show();
    }

    /**
     * The alert to be shown when the game is over.
     * @param information The message that will be in the content of the alert.
     */
    public void gameOverAlert(String information){
        world=InteractableWorld.getWorldInstance();
        world.setFrozen(true);
        ButtonType yesBT= new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noBT= new ButtonType("No", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Yes",yesBT,noBT);
        //Button btn=new Button("OK");
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(information);

        alert.setOnCloseRequest(e -> {
            ButtonType result = alert.getResult();

            world.getScoreBoard().recordScore();
            if (result != null && result == yesBT) {
                world.getLevelFactory().resetLevel();
                world.getLifeHandler().resetLife(3);
                world.setFrozen(false);
                world.markToReset();
            }
            else if(result==noBT){
                try {
                    Main.setEndRoot();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        alert.show();
    }


    /**
     * The alert to be shown when the level of the game increases.
     * @param information The message to be displayed in the content of the alert.
     */
    public void levelAlert(String information){
        world=InteractableWorld.getWorldInstance();
        world.setFrozen(true);
        ButtonType BT= new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "OK",BT);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(information);

        alert.setOnCloseRequest(e -> {
            ButtonType result = alert.getResult();
            if (result != null && result == BT) {
                world.setFrozen(false);
            }
        });
        alert.show();
    }
}
