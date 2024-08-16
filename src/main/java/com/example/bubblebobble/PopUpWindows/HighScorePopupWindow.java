package com.example.bubblebobble.PopUpWindows;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Class HighScorePopupWindow handles the pop-up window with high scores
 * when the game is over.
 * Both the name of the player and the specific score will be displayed.
 */
public class HighScorePopupWindow extends Stage{
    private static Stage window;

    /**
     * Initialize a new {@code Stage}, define the root of the stage's scene to
     * be a {@code VBox}. Define a {@code Text} of which the text is the content
     * of the high score lists, then add it to the root.
     * The world can only update when the pop-up window is closed.
     */
    public static void showWindow() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        VBox root = new VBox(20);
        Image image = new Image(Objects.requireNonNull(HighScorePopupWindow.class.getResourceAsStream("/Pic/SceneBackgrounds/highScoreBackground.jpg")),500,600,false,false);
        ImageView bubble=new ImageView(new Image(Objects.requireNonNull(HighScorePopupWindow.class.getResourceAsStream("/Pic/SceneBackgrounds/bubble.gif")),500,600,false,false));
        root.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(500,600,false,false,false,false))));


        Text text=new Text();
        Text title = new Text("\n     RANKING LIST");
        title.setFont(Font.font("Ravie", 36));
        text.setFont(Font.font("Lucida Console", 25));


        text.setText(readScores());
        text.setLineSpacing(20);

        root.getChildren().add(title);
        root.getChildren().add(text);
        text.setLayoutX(100);

        root.getChildren().add(bubble);
        Scene scene = new Scene(root, 500, 600);

        window.setTitle("Ranking List");
        window.setScene(scene);
        window.show();

        if(!window.isShowing()) {
            InteractableWorld.getWorldInstance().setFrozen(false);
        }
    }


    /**
     * Read the scores and plays' names in the txt file.
     * Store the value of the score to the first element of {@code scoreInfo},
     * and the name to the second element of {@code scoreInfo}. Sort the array
     * {@code scoreInfo} according the value of scores from big to small.
     * Then store the value of the sorted array in the form of {@code String}.
     * @return The string form of the sorted array recording scores and plays' names
     */
    public static String readScores(){
        String[] scoreInfo;
        ArrayList<String[]> scoreInfos=new ArrayList<>();
        BufferedReader br;
        try {
            String str;
            br = new BufferedReader(new FileReader("./src/main/resources/ScoreList/scoreList.txt"));
            while((str=br.readLine())!=null){
                scoreInfo=str.split(" ");
                scoreInfos.add(scoreInfo);
            }
            for (int i = 0; i < scoreInfos.size(); i++) {
                int max=Integer.parseInt(scoreInfos.get(i)[0]);
                String name=scoreInfos.get(i)[1];
                int maxIndex=i;
                for(int j=i+1;j< scoreInfos.size();j++){
                    if(Integer.parseInt(scoreInfos.get(j)[0])>max){
                        max=Integer.parseInt(scoreInfos.get(j)[0]);
                        name=scoreInfos.get(j)[1];
                        maxIndex=j;
                    }
                }
                if(maxIndex!=i){
                    scoreInfos.get(maxIndex)[0]=scoreInfos.get(i)[0];
                    scoreInfos.get(maxIndex)[1]=scoreInfos.get(i)[1];

                    scoreInfos.get(i)[0]=String.valueOf(max);
                    scoreInfos.get(i)[1]=name;
                }
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }

        String rankingList="";
        for (int i = 0; i < scoreInfos.size(); i++) {
            rankingList += "       NO."+(i+1)+"  "+scoreInfos.get(i)[1]+" - "+scoreInfos.get(i)[0]+"\n";
        }
        return rankingList;
    }
}
