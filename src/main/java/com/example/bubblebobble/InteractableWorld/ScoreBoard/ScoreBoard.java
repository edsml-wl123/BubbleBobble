package com.example.bubblebobble.InteractableWorld.ScoreBoard;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/**
 * ScoreBoard class handles everything about the update of scores in
 * the game. The score will increase everytime the hero has collected
 * a fruit enemy.
 */
public class ScoreBoard {

    InteractableWorld world;

    //record scores of different levels
    private int[] temp;
    private int[] scores;

    private FileWriter fw ;
    BufferedWriter bw ;


    public ScoreBoard(InteractableWorld world)  {
        this.world=world;
        temp=new int[]{0,0,0};
        scores=new int[]{0,0,0};
    }


    /**
     * Add the score of the current level by 1.
     */
    public void addScore(){
        temp[world.getLevelFactory().getLevel()]++;
    }

    /**
     * Add the score of the current level by {@code i}.
     * @param i The value added to the score of the current levl.
     */
    public void addScore(int i){
        temp[world.getLevelFactory().getLevel()]+=i;
    }

    /**
     * Handles the update of score if the current level is reset, i.e. the
     * hero has collided with an enemy or with the boss.
     * The score got before the current level is reset will be remained in
     * {@code scores[world.getLevelFactory().getLevel()]}. While
     * {@code temp[world.getLevelFactory().getLevel()]} will be reset to 0
     * to record the score in this level after the game is reset.
     */
    public void resetCurrentLevel(){
        if(scores[world.getLevelFactory().getLevel()]<temp[world.getLevelFactory().getLevel()]){
            scores[world.getLevelFactory().getLevel()]=temp[world.getLevelFactory().getLevel()];
        }
        temp[world.getLevelFactory().getLevel()]=0;
    }

    /**
     * Calculate total score got by the player.
     * Update the scores in different levels to the highest ones, then add
     * them together.
     * @return The highest total scores currently.
     */
    public int totalScore(){
        for(int i=0;i<3;i++){
            if(temp[i]>scores[i]){
                scores[i]=temp[i];
            }
        }
        return scores[0]+scores[1]+scores[2];
    }


    /**
     * Record the score after the game is over.
     * Write the score and the name of the player to the txt file, and reset
     * the value of scores in different levels to 0.
     */
    public void recordScore() {
        try {
            fw = new FileWriter("./src/main/resources/ScoreList/scoreList.txt",true);
            bw = new BufferedWriter(fw);

            int highestScore=totalScore();

            String name=Main.getUserName().replace(" ","");
            bw.write(String.valueOf(highestScore)+" "+ name);
            bw.write('\n');
            bw.close();

            for(int i=0;i<3;i++){
                temp[i]=0;
                scores[i]=0;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
