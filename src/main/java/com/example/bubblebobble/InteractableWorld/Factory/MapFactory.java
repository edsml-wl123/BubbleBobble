package com.example.bubblebobble.InteractableWorld.Factory;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.Boss;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.GameObjects.StaticGameObjects.StaticGameObject;
import com.example.bubblebobble.GameObjects.StaticGameObjects.*;
import com.example.bubblebobble.GameObjects.Weapons.Bubble;
import com.example.bubblebobble.GameObjects.Weapons.EnemyProjectile;
import com.example.bubblebobble.GameObjects.Weapons.HeroProjectile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


/**
 * MapFactory class handles things about drawing different maps.
 * It draws the background of the game according to the player's
 * background color choice, and it also draws different shapes or images
 * of game objects when the player choose different maps.
 */
public class MapFactory {
    InteractableWorld world;
    String mapName;
    Color backgroundChoice;

    //Images to be drawn on the world's canvas
    Image pill,sun,openDoor,door,fruit,shield;
    Image queen,bubbledQueen,heroProjectile,queenProjectile,witch,peach,heart,queenBoss;
    Image pinkHeart,barbie,dog,dogBoss,bone,poo,bubbledDog;

    private static int countDraw=0;


    /**
     * Initialize the images to be drawn on the world's canvas.
     * Initialize the {@code mapName} with the value of {@code world.getMap()}.
     * @param world The world parameter used to initialize {@code this.world}.
     */
    public MapFactory(InteractableWorld world){
        this.world=world;
        peach=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/MazeObjects/peach.jpg")));
        queen=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/MazeObjects/queen.png")));
        heart=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/MazeObjects/heart.jpg")));
        witch=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/MazeObjects/witch.png")));
        shield=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/shield.png")));
        fruit=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/fruit.png")));
        queenProjectile=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/MazeObjects/queenProjectile.png")));
        heroProjectile=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/MazeObjects/heroProjectile.png")));
        door=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pic/BarbieObjects/door.png")));
        openDoor=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pic/openDoor.png")));
        bubbledQueen=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/MazeObjects/diedQueen.png")));
        pinkHeart=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/BarbieObjects/pinkHeart.jpg")));
        barbie=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/BarbieObjects/barbie.png")));
        dog=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/BarbieObjects/dog.png")));
        bone=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/BarbieObjects/bone.png")));
        poo=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/BarbieObjects/poo.png")));
        bubbledDog=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/BarbieObjects/bubbledDog.png")));

        pill=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/medicine.png")));
        sun=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/sun.png")));
        queenBoss=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/MazeObjects/queenBoss.png")));
        dogBoss=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/BarbieObjects/dogBoss.png")));

        mapName= world.getMap();
    }


    /**
     * Reset the world by clearing all of its contents. Initialize the game
     * by reading different maps in the corresponding txt file. Exception
     * will be thrown when the file cannot be found.
     * Mark the world not to be reset after start the game.
     * @param map
     */
    public void startGame(String map) {
        world.clearContents();
        world.setNumOfDeadEnemy(0);
        mapName=map;

        InputStream input = this.getClass().getResourceAsStream("/world/"+mapName);
        if(input==null){throw new NullPointerException("Cannot find the file.");} //add new exception
        Scanner scanner = new Scanner(input);

        ArrayList<Destination> destinations=new ArrayList<>();

        for (int row = 0; row < Main.HEIGHT; row++) {
            String currentLine = scanner.next();
            for (int col = 0; col < Main.GAME_WIDTH; col++) {
                if (currentLine.charAt(col) == '*') {
                    world.addFloorUnit(new FloorUnit(world, col, row));
                }
                else if(currentLine.charAt(col)=='@'){
                    destinations.add(new Destination(world,col,row));
                    world.addFloorUnit(new FloorUnit(world, col, row));
                }
                else if(currentLine.charAt(col)=='#'){
                    world.addPortal(new Portal(world,col,row));
                }
                else if (currentLine.charAt(col) == 'H') {
                    Hero.heroInit(col,row);
                    setHeroSize();
                    world.addHero(Hero.getInstance());

                } else if (currentLine.charAt(col) == '|') {
                    world.addWallUnit(new WallUnit(world, col, row));
                } else if (currentLine.charAt(col) == '_') {
                    world.addCeilingUnit(new CeilingUnit(world, col, row));

                } else if (currentLine.charAt(col) == 'M') {
                    Enemy enemy=new Enemy(world, col, row);
                    setEnemySize(enemy);
                    world.addEnemy(enemy);
                }
            }
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }
        scanner.close();

        //Set the destination of portals
        if(!world.getPortals().isEmpty()){
            if(world.getPortals().size()==1){
                world.getPortals().get(0).setDes(destinations.get(0));
            }
            else {
                world.getPortals().get(0).setDes(destinations.get(1));
                world.getPortals().get(1).setDes(destinations.get(0));
            }
        }
        world.markNotToReset();
    }


    /**
     * Set the size of enemies according to different maps.
     * The size of enemy in classic map is the default size.
     * @param enemy The enemy that is to be set size.
     */
    public void setEnemySize(Enemy enemy){
        if(mapName.equalsIgnoreCase("Maze.txt")){
            enemy.setSize(25,45);
        }
        else if(mapName.equalsIgnoreCase("Barbie.txt")){
            enemy.setSize(35,35);
        }
    }

    /**
     * Set the size of hero according to different maps.
     * The size of hero in classic map is the default size.
     */
    public void setHeroSize(){
        if(mapName.equalsIgnoreCase("Maze.txt")){
            Hero.getInstance().setSize(25,30);
        }
        else if(mapName.equalsIgnoreCase("Barbie.txt")){
            Hero.getInstance().setSize(25,30);
        }
    }

    /**
     * Set the color of background in the game.
     * @param color The parameter used to set {@code backgroundChoice}.
     */
    public void setBackgroundChoice(Color color){
        backgroundChoice=color;
    }


    /**
     * Draw background on the canvas on the game scene's root.
     * If the player has chosen a background color in {@code colorPick}
     * as the setup choice, draw the corresponding color chosen. Otherwise,
     * draw default color according to different maps.
     * @param gc The graphic context of the canvas that the background is
     *           going to be drawn on to.
     */
    public void drawBackground(GraphicsContext gc){
        if(backgroundChoice!=null){
            gc.setFill(backgroundChoice);
            gc.fillRect(0,0,Main.GAME_WIDTH*Main.UNIT_SIZE, Main.HEIGHT * Main.UNIT_SIZE);
            return;
        }
        if(mapName.equalsIgnoreCase("Classic.txt")){
            gc.setFill(Color.rgb(255,235,176,0.8));
            gc.fillRect(0,0,Main.GAME_WIDTH*Main.UNIT_SIZE, Main.HEIGHT * Main.UNIT_SIZE);
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            gc.setFill(Color.WHITESMOKE);
            gc.fillRect(0,0,Main.GAME_WIDTH*Main.UNIT_SIZE, Main.HEIGHT * Main.UNIT_SIZE);
        }
        else {
            gc.setFill(Color.BLACK);
            gc.fillRect(0,0,Main.GAME_WIDTH*Main.UNIT_SIZE, Main.HEIGHT * Main.UNIT_SIZE);
        }
    }


    /**
     * Draw static objects according to different maps
     * @param object The static object that is going to be drawn.
     * @param gc The graphic context of the canvas that the static is
     *           going to be drawn on to.
     */
    public void drawOnStaticObject(StaticGameObject object, GraphicsContext gc){
        if(mapName.equalsIgnoreCase("Classic.txt")){
            gc.setFill(Color.rgb(130,200,205,1));
            gc.fillRect(object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
           if(countDraw==0) {
                gc.drawImage(peach, object.getX(), object.getY(), object.getWidth(), object.getHeight());
                countDraw=1;
            }
            else if(countDraw==1) {
                gc.drawImage(heart, object.getX(), object.getY(), object.getWidth(), object.getHeight());
                countDraw=0;
            }
        }
        else {
            gc.drawImage(pinkHeart,object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
    }


    /**
     * According to different maps, return the path of the images that is
     * going to be the background of {@code infoPane} in the game scene's root.
     * @return The file path of images to be the {@code infoPane}'s background.
     */
    public String infoPaneBackground(){
        mapName= world.getMap();
        if(mapName.equalsIgnoreCase("Classic.txt")){
            return "/Pic/InfoPaneBackgrounds/classicInfoPane.jpg";
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            return "/Pic/InfoPaneBackgrounds/mazeInfoPane.jpg";
        }
        else{
            return "/Pic/InfoPaneBackgrounds/barbieInfoPane.jpg";
        }
    }


    /**
     * Set the style of the text on {@code infoPane} according to
     * different maps.
     * @param text The {@code Text} object that is going to be set style.
     */
    public void InfoPaneFont(Text text){
        if(mapName.equalsIgnoreCase("Classic.txt")){
            text.setFont(Font.font(null, FontWeight.BOLD, 22));
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            text.setFont(Font.font("Britannic Bold",24));
            text.setFill(Color.WHITE);
        }
        else {
            text.setFont(Font.font("Elephant",23));
        }
    }


    /**
     * According to different maps, draw active enemy on the canvas on the
     * game scene's root.
     * @param object The enemy that is going to be drawn.
     * @param gc The graphic context of the canvas that the enemy is going to
     *           be drawn on to.
     */
    public void drawActiveEnemy(Enemy object,GraphicsContext gc){
        if(mapName.equalsIgnoreCase("Classic.txt")){
            gc.setFill(Color.PINK);
            gc.fillRect(object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            gc.drawImage(queen, object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
        else {
            gc.drawImage(dog, object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
    }


    /**
     * According to different maps, draw bubbled enemy on the canvas on the
     * game scene's root.
     * @param object The enemy that is going to be drawn.
     * @param gc The graphic context of the canvas that the enemy is going to
     *           be drawn on to.
     */
    public void drawBubbledEnemy(Enemy object,GraphicsContext gc){
        if(mapName.equalsIgnoreCase("Classic.txt")){
            drawActiveEnemy(object,gc);
            gc.setFill(Color.rgb(88,182 , 230, (object.getBubbleTimer() * ((double) 1 / 300))));
            gc.fillRect(object.getX()-5, object.getY()-5, object.getWidth()+10, object.getHeight()+10);
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            gc.drawImage(bubbledQueen,object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
        else {
            gc.drawImage(bubbledDog,object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
    }


    /**
     * According to different maps, draw fruit enemy on the canvas on the
     * game scene's root.
     * @param object The enemy that is going to be drawn.
     * @param gc The graphic context of the canvas that the enemy is going to
     *           be drawn on to.
     */
    public void fruitEnemyColor(Enemy object,GraphicsContext gc){
        gc.drawImage(fruit,object.getX(), object.getY(), object.getWidth(), object.getHeight());
    }


    /**
     * According to different maps, draw active hero on the canvas on the
     * game scene's root.
     * @param hero The hero that is going to be drawn.
     * @param gc The graphic context of the canvas that the hero is going to
     *           be drawn on to.
     */
    public void drawActiveHero(Hero hero,GraphicsContext gc){
        if(mapName.equalsIgnoreCase("Classic.txt")){
            gc.setFill(Color.rgb(102,140,95,1));
            gc.fillRect(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            gc.drawImage(witch,hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());
        }
        else {
            gc.drawImage(barbie,hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());
        }
    }


    /**
     * According to different maps, draw stunned hero on the canvas on the
     * game scene's root.
     * @param hero The hero that is going to be drawn.
     * @param gc The graphic context of the canvas that the hero is going to
     *           be drawn on to.
     */
    public void drawStunnedHero(Hero hero,GraphicsContext gc){
        if(mapName.equalsIgnoreCase("Classic.txt")){
            gc.setFill(Color.BLACK);
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            drawActiveHero(hero,gc);
            gc.setFill(Color.rgb(0,0,0,0.55));
        }
        else {
            drawActiveHero(hero,gc);
            gc.setFill(Color.rgb(220,200,200,0.55));
        }
        gc.fillRect(hero.getX(),hero.getY(),hero.getWidth(),hero.getHeight());
    }


    /**
     * According to different maps, draw shielded hero on the canvas on the
     * game scene's root.
     * @param hero The hero that is going to be drawn.
     * @param gc The graphic context of the canvas that the hero is going to
     *           be drawn on to.
     */
    public void drawShieldHero(Hero hero,GraphicsContext gc){
        if(mapName.equalsIgnoreCase("Classic.txt")){
            gc.setFill(Color.rgb(0, (int) ((hero.shieldTimer * ((double) 255 / hero.SHIELD_TIME))), (int) (hero.shieldTimer * ((double) 255 / hero.SHIELD_TIME)), 0.6));
            gc.fillOval(hero.getX() - 10, hero.getY() - 10, hero.getWidth() + 20, hero.getHeight()+ 20);
        }
        else{
            gc.drawImage(shield,hero.getX()-5,hero.getY() - 5, hero.getWidth() + 10, hero.getHeight()+ 10);
        }
    }

    /**
     * According to different maps, draw charging hero on the canvas on the
     * game scene's root.
     * @param hero The hero that is going to be drawn.
     * @param gc The graphic context of the canvas that the hero is going to
     *           be drawn on to.
     */
    public void drawChargeHero(Hero hero,GraphicsContext gc){
        //gc.setFill(Color.rgb(255, 204, 102, 1));
        if(mapName.equalsIgnoreCase("Classic.txt")){
            gc.setFill(Color.rgb(255, 204, 102, 1));
            gc.fillRect(hero.getX(),hero.getY(),hero.getWidth(),hero.getHeight());
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            drawActiveHero(hero,gc);
            //gc.drawImage(witch,hero.getX(),hero.getY(),hero.getWidth(),hero.getHeight());
            gc.setFill(Color.rgb(255, 0, 30, 0.75));
            gc.fillRect(hero.getX(),hero.getY(),hero.getWidth(),hero.getHeight());
        }
        else {
            drawActiveHero(hero,gc);
            gc.setFill(Color.rgb(250, 0, 100, 0.65));
            gc.fillRect(hero.getX(),hero.getY(),hero.getWidth(),hero.getHeight());
        }
    }


    /**
     * According to different maps, draw bubble on the canvas on the
     * game scene's root.
     * @param object The bubble that is going to be drawn.
     * @param gc The graphic context of the canvas that the bubble is going to
     *           be drawn on to.
     */
    public void drawBubble(Bubble object, GraphicsContext gc){
        if (object.getWidth() <= 2500) {
            if(mapName.equalsIgnoreCase("Classic.txt")){
                gc.setFill(Color.rgb(255, 204, 102, 1 - (object.getWidth() * ((double) 1 / 2500))));
            }
            else if(mapName.equalsIgnoreCase("Maze.txt")){
                gc.setFill(Color.rgb(255, 0, 65, 1 - (object.getWidth() * ((double) 1 / 2500))));
            }
            else {
                gc.setFill(Color.rgb(240, 0, 100, 1 - (object.getWidth() * ((double) 1 / 2500))));
            }
        } else {
            if(mapName.equalsIgnoreCase("Classic.txt")){
                gc.setFill(Color.rgb(0, 0, 0, 0));
            }
            else if(mapName.equalsIgnoreCase("Maze.txt")){
                gc.setFill(Color.rgb(0, 0, 0, 0));
            }
            else {
                gc.setFill(Color.rgb(0, 0, 0, 0));
            }
        }
        gc.fillOval(object.getX(), object.getY(),object.getWidth(),object.getHeight());
    }




    /**
     * According to different maps, draw hero projectiles on the canvas on the
     * game scene's root.
     * @param object The hero projectile that is going to be drawn.
     * @param gc The graphic context of the canvas that the hero projectile
     *           is going to be drawn on to.
     */
    public void drawHeroProjectile(HeroProjectile object, GraphicsContext gc){
        if(mapName.equalsIgnoreCase("Classic.txt")){
            if (object.getActive()) { //change all new color() to color.rgba
                gc.setFill(Color.rgb(102, 204, 255,1));
                //System.out.println("draw hero projectile");
            } else {
                gc.setFill(Color.rgb(51, 204, 255, 0.157));
            }
            //gc.fillOval(x, y, width, height);
            gc.fillOval(object.getX(),object.getY(),object.getWidth(),object.getHeight());
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            gc.drawImage(heroProjectile, object.getX(), object.getY(), object.getWidth(), object.getHeight());
            if (!object.getActive()) {
                gc.setFill(Color.rgb(255, 255, 255,0.65));
                gc.fillOval(object.getX(), object.getY(), object.getWidth(), object.getHeight());
            }
        }
        else {
            gc.drawImage(bone,object.getX(), object.getY(), object.getWidth(), object.getHeight());
            if (!object.getActive()) {
                gc.setFill(Color.rgb(255, 255, 255,0.65));
                gc.fillOval(object.getX(), object.getY(), object.getWidth(), object.getHeight());
            }
        }
    }


    /**
     * According to different maps, draw enemy projectiles on the canvas on the
     * game scene's root.
     * @param object The enemy projectile that is going to be drawn.
     * @param gc The graphic context of the canvas that the enemy projectile
     *           is going to be drawn on to.
     */
    public void drawEnemyProjectile(EnemyProjectile object, GraphicsContext gc){
        if(mapName.equalsIgnoreCase("Classic.txt")){
            if (object.getActive()) {
                gc.setFill(Color.rgb(230, 100, 140));
            }
            else {
                gc.setFill(Color.rgb(230, 100, 140, 0.16));
            }
            gc.fillOval(object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            gc.drawImage(queenProjectile,object.getX(), object.getY(), object.getWidth(), object.getHeight());
            if (!object.getActive()) {
                gc.setFill(Color.rgb(255, 255, 255,0.65));
                gc.fillOval(object.getX(), object.getY(), object.getWidth(), object.getHeight());
            }
        }
        else {
            gc.drawImage(poo,object.getX(), object.getY(), object.getWidth(), object.getHeight());
            if (!object.getActive()) {
                gc.setFill(Color.rgb(255, 255, 255,0.65));
                gc.fillOval(object.getX(), object.getY(), object.getWidth(), object.getHeight());
            }
        }
    }



    /**
     * According to different maps, draw boss on the canvas on the
     * game scene's root.
     * @param object The boss that is going to be drawn.
     * @param gc The graphic context of the canvas that the boss
     *           is going to be drawn on to.
     */
    public void drawBoss(Boss object, GraphicsContext gc){
        if(mapName.equalsIgnoreCase("Classic.txt")){
            gc.setFill(Color.rgb(250, 20, 100,1));
            gc.fillRect(object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
        else if(mapName.equalsIgnoreCase("Maze.txt")){
            object.setSize(48,58);
            gc.drawImage(queenBoss,object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }
        else{
            object.setSize(45,45);
            gc.drawImage(dogBoss,object.getX(), object.getY(), object.getWidth(), object.getHeight());
        }

    }
}
