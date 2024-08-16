package com.example.bubblebobble;

import com.example.bubblebobble.PopUpWindows.HighScorePopupWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * Main class extends {@code Application} and shows the game on the stage.
 * It set the root of the stage's scene to different fxml files according
 * to the flow of the game.
 */
public class Main extends Application {
    public static final int UNIT_SIZE = 20;
    public static final int WIDTH = 50;
    public static final int HEIGHT = 34;
    public static final int GAME_WIDTH=40;
    private static Scene scene;
    private static String userName="Unknown";


    /**
     * Initialize {@code scene} and set its root to the start page,
     * and show {@code stage}.
     * @param stage The stage that is going to be shown.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("startScreen.fxml"));
        scene = new Scene(fxmlLoader.load(), WIDTH * UNIT_SIZE, HEIGHT * UNIT_SIZE);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Set the root of {@code scene} to the start page.
     * @throws IOException
     */
    public static void setStartRoot() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("startScreen.fxml"));
        scene.setRoot(fxmlLoader.load());
    }

    /**
     * Set the root of {@code scene} to the game.
     * @throws IOException
     */
    public static void setGameRoot() throws IOException {
        //GamePane gamePane = new GamePane(GAME_WIDTH * UNIT_SIZE, HEIGHT * UNIT_SIZE);
        FXMLLoader gamePane=new FXMLLoader(Main.class.getResource("game.fxml"));
        scene.setRoot(gamePane.load());
    }

    /**
     * Set the root of {@code scene} to the page showing rules.
     * @throws IOException
     */
    public static void setRuleRoot() throws IOException {
        FXMLLoader rule=new FXMLLoader(Main.class.getResource("rules.fxml"));
        scene.setRoot(rule.load());
    }

    /**
     * Set the root of {@code scene} to the setup page.
     * @throws IOException
     */
    public static void setSetUpRoot() throws IOException {
        FXMLLoader setUP = new FXMLLoader(Main.class.getResource("setUpScreen.fxml"));
        scene.setRoot(setUP.load());
    }

    /**
     * Display the pop-up window showing the high score list. Then set the
     * root of {@code scene} to the end page.
     * @throws IOException
     */
    public static void setEndRoot() throws IOException {
        HighScorePopupWindow.showWindow();

        FXMLLoader end = new FXMLLoader(Main.class.getResource("endScreen.fxml"));
        scene.setRoot(end.load());
    }

    /**
     * Get method of the player's name.
     * @return The value of {@code userName}
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Set method of {@code userName}.
     * @param name Set the value of the player's name.
     */
    public static void setUserName(String name){
        userName=name;
        System.out.println(userName);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
