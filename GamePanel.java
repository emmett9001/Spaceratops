/******************************************************************************
"Spaceratops"
 * Written, directed, composed, programmed, animated, drawn, designed by Emmett Butler
 * Spring 2011
*******************************************************************************/
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.Image;
import java.awt.Dimension;

import java.net.URL;

import java.applet.AudioClip;
import java.applet.Applet;

public class GamePanel extends JPanel {

    int[] laserEnemyCollideArr, homerEnemyCollideArr, powerupGetArr, laserCollideArr, moveStartTimes;
    int[][] waveEnemyPositions;
    int laserMessageCounter = 0, deathMessageCounter = 0, startMessageCounter = 50, waveTimeCounter = 0, j = 0, endTimer = 0;
    int timeCount, laserCollideTotal, topwave = 50;
    double timeInSeconds;
    boolean enemiesAreGone, control = true, success = false, showOpts, death = false;

    //delcare main game objects
    Spaceship ship;
    Enemy[][] enemies;
    Powerup[] powerups;
    Background[] bgs;
    Background credits;
    Filter redfilm;
    ProgressBar[][] enemyLifeBars;
    LifeIndicator lifeBar;
    EnemyDirector enemyDirector;
    Level level;
    Color grey;
    ScoreWriter sw;
    JPanel op;

    Image offscreen;
    Graphics bufferGraphics;
    Dimension dim;
    
    //use 16-bit, 24000 samples per second to avoid memory overload
    //8-bit, 32000 and 8-bit 441000 work in this context but have audible choppiness
    static AudioClip song1;
    
    URL fontPath;
    static URL audioPath;
    static Font font;
    MyText laserMessage, deathMessage, deathMessage2, deathMessage3, startMessage, successMessage;

    public static void loadAudio(){
        //load audio files
        audioPath = GamePanel.class.getResource("audio/alllevels.aif");
        if(audioPath != null){
            song1 = Applet.newAudioClip(audioPath);
        }
    }

    //contruct game panel inside parent frame
    public GamePanel(final Spaceratops t){
        super.setFocusable(true);
        setFocusable(true);

        showOpts = true;

        final GamePanel g = this;

        //initialize wEP array
        waveEnemyPositions = new int[32][2];

        //load font data
        font = Level.getFont().deriveFont((float)20);

        //initialize level object
        level = new Level(0);

        //make sidebar color object
        grey = new Color((float).2, (float)0.2, (float)0.2, (float)1.0);

        //array to register powerup collisions
        powerupGetArr = new int[2];

        //intitialize main game objects with starting positions and images
        bgs = new Background[2];
        bgs[0] = new Background(-6375);
        bgs[1] = new Background(-12750);

        //make filter object for red film on player death
        redfilm = new Filter("images/redfilm.png");

        //initialize player ship
        ship = new Spaceship(480, 600, 60);
        ship.makeAlive();

        //initialize player life indicaor
        lifeBar = new LifeIndicator(25, 75);

        //initialize powerup array
        powerups = new Powerup[3];

        //declare 2d array of enemy objects
        enemies = new Enemy[18][8];//enemies.length must be an even number
        //initialize enemy life bars
        enemyLifeBars = new ProgressBar[enemies.length][enemies[0].length];
        //initialize new enemyDirector object
        enemyDirector = new EnemyDirector();

        //create array of the four legal values for enemy.moveStartTime
        moveStartTimes = new int[4];
        moveStartTimes[0] = 55;
        moveStartTimes[1] = 65;
        moveStartTimes[2] = 75;
        moveStartTimes[3] = 85;

        //instantiate array of enemy arrays
        for(int j = 0; j < enemies[0].length; j++){
            //                  type            x starting position moveStartTime
            enemies[0][j] = new Drone(          (70*(j+3))+130,     65);
            enemies[1][j] = new Drone(          (70*(j+3))+130,     55);
            enemies[2][j] = new Grunt(          (80*j)+300,         65);
            enemies[3][j] = new Grunt(          (80*j)+300,         55);
            enemies[4][j] = new MediumEnemy2(   (80*j)+300,         65);
            enemies[5][j] = new MediumEnemy2(   (80*j)+300,         55);
            enemies[6][j] = new Rainbow(        (80*j)+300,         65);
            enemies[7][j] = new Rainbow(        (80*j)+300,         55);
            enemies[8][j] = new Rocket(         (80*j)+300,         65);
            enemies[9][j] = new Rocket(         (80*j)+300,         55);
            enemies[10][j] = new Spider1(       (80*j)+300,         65);
            enemies[11][j] = new Spider1(       (80*j)+300,         55);
            enemies[12][j] = new Grunt2(        (80*j)+300,         65);
            enemies[13][j] = new Grunt2(        (80*j)+300,         55);
            enemies[14][j] = new Dino(          (80*j)+300,         65);
            enemies[15][j] = new Dino(          (80*j)+300,         55);
        }
        enemies[16][3] = new BigBird(           600,                55);
        enemies[16][6] = new BigBird(           800,                55);

        //set enemy angles and assign them one lifebar each
        for(int i = 0; i < enemies.length; i++){
            for(int j = 0; j < enemies[i].length; j++){
                if(enemies[i][j] != null){
                    enemies[i][j].setAngle(Math.PI);
                    enemyLifeBars[i][j] = new ProgressBar(enemies[i][j].getDamage(), enemies[i][j].getTotalHealth());
                }
            }
        }

        //initialize message MyText objects
        laserMessage = new MyText("Laserup", 1, 0, 0, 0, 890, 140, 15);
        deathMessage = new MyText("You failed.", 1, 0, (float).5, 0, 340, 300, 30);
        deathMessage2 = new MyText("your dinofriends", 1, 0, (float).5, 0, 260, 340, 30);
        deathMessage3 = new MyText("will die.", 1, 0, (float).5, 0, 380, 380, 30);
        startMessage = new MyText("Survive!", 1, 0, 0, 1, 370, 340, 30);
        successMessage = new MyText("Success!", 1, 0, 0, 0, 370, 340, 30);

        //populate powerup array
        powerups[0] = new Laserup(2000, 0, 60);
        powerups[1] = new Lifeup(2000, 0, 60);
        powerups[2] = new Missileup(2000, 0, 60);
        //set powerup fall direction
        for(int i = 0; i < powerups.length; i++){
            powerups[i].setXSpeed(0);
        }
        
        //Timer controls post-button motion
        final Timer mover = new Timer(50, new ActionListener(){
            public void actionPerformed(ActionEvent e){

                timeCount++;
                waveTimeCounter++;
                timeInSeconds = timeCount/25;

                //play music at the start of the game
                if(timeCount == 1){
                    if(song1 != null){
                        song1.loop();
                    }
                }

                for(int i = 0; i < enemies.length; i++){
                    for(int j = 0; j < enemies[i].length; j++){
                        if(enemies[i][j] != null){

                            enemies[i][j].shoot(ship.getX(), ship.getY(), enemies[i][j].isHoming());

                            //if the enemy is of a previous wave
                            if(enemies[i][j].getWave() < level.getWave()){
                                //and it's not above or onscreen
                                if(!enemies[i][j].isOnscreen() && !enemies[i][j].isAboveScreen()){
                                    enemies[i][j].reset((80*j)+300, -550);//reset it
                                    enemies[i][j].setMoveStartTime(moveStartTimes[i % 4]);//and its moveStartTime
                                    //System.out.println("reset " + i + " " + j);
                                    //System.out.println("wave " + enemies[i][j].getWave());
                                    //System.out.println("x: " + enemies[i][j].getX() + " y: " + enemies[i][j].getY());
                                }
                            }

                            //if the enemies in the current wave are either onscreen or above the screen, they are not "gone"
                            if(enemies[i][j].isWave(level.getWave())){
                                 //set enemy moveStart times to legal values from the array
                                enemies[i][j].setMoveStartTime(moveStartTimes[i % 4]);

                                //populate enemy movement arrays based on those in enemyDirector
                                if(i % 2 == 0){
                                    enemies[i][j].movePattern(enemyDirector.hoverThenLeaveLeft());
                                }
                                else
                                    enemies[i][j].movePattern(enemyDirector.hoverThenLeaveRight());

                                //start enemy's moveCount when their moveStartTime passes
                                if(enemies[i][j].getY() > 0 && enemies[i][j].isWave(level.getWave()) && waveTimeCounter > enemies[i][j].getMoveStartTime()){
                                    enemies[i][j].incrementMoveCount();
                                }

                                //start enemy's shootCount when shootStartTime passes
                                if(enemies[i][j].getY() > 0 && enemies[i][j].getX() > 0 && enemies[i][j].getX() < 750 &&
                                    timeCount > enemies[i][j].getShootStartTime()){
                                        if(enemies[i][j].getType() == "images/bigbird.png")
                                            enemies[i][j].randomShootCount(true);
                                        else
                                            enemies[i][j].randomShootCount(false);
                                }

                                if(enemies[i][j].isAlive()){
                                    enemyLifeBars[i][j].setFill(enemies[i][j].getDamage(), enemies[i][j].getTotalHealth());
                                }

                                enemies[i][j].makeDeadOnFrameExit();
                                enemies[i][j].makeAliveOnFrameEnter();

                                //show powerup when kill number is reached
                                for(int k = 0; k < powerups.length; k++){
                                    powerups[k].show(enemies[i][j].getDamage(), enemies[i][j].getTotalHealth(),
                                        enemies[i][j].isAlive(), enemies[i][j].getX(), enemies[i][j].getY());
                                }

                                //detect homing missile -> enemy collisions
                                homerEnemyCollideArr = ship.homerEnemyCollideDetect(enemies[i][j].getX(), enemies[i][j].getY(),
                                        enemies[i][j].getWidth(), enemies[i][j].getHeight());
                                //detect laser->enemy collisions...
                                laserEnemyCollideArr = ship.laserEnemyCollideDetect(enemies[i][j].getX(), enemies[i][j].getY(),
                                        enemies[i][j].getWidth(), enemies[i][j].getHeight());
                                //and update enemy health on that basis...
                                enemies[i][j].setDamage(enemies[i][j].getDamage() + laserEnemyCollideArr[0] + homerEnemyCollideArr[0]);
                                //as well as determining the "shot" status of enemies
                                if(laserEnemyCollideArr[1] == 1 || homerEnemyCollideArr[1] == 1){
                                    enemies[i][j].setShot(true);
                                }
                                else if(laserEnemyCollideArr[1] == 0 || homerEnemyCollideArr[1] == 0){
                                    enemies[i][j].setShot(false);
                                }

                                //update player health based on laser impacts
                                laserCollideArr = enemies[i][j].laserCollideDetect(ship.getX(),
                                    ship.getY());
                                laserCollideTotal += laserCollideArr[1];
                                ship.setDamage(laserCollideArr[0] + ship.getDamage());

                                
                                enemies[i][j].testDeath();
                                enemies[i][j].move(10);
                                
                                //bounce enemies off side of frame
                                enemies[i][j].borderDetect();
                                //System.out.println("Enemy[" + i + "][" + j + "] : isAboveScreen(): " + enemies[i][j].isAboveScreen());
                                //System.out.println("Enemy[" + i + "][" + j + "] : isOnscreen(): " + enemies[i][j].isOnscreen());
                                //System.out.println("Enemy[" + i + "][" + j + "] : isAlive(): " + enemies[i][j].isAlive());
                                //System.out.println("Enemy[" + i + "][" + j + "] : isShot(): " + enemies[i][j].isShot());
                                if(enemies[i][j].isOnscreen() || enemies[i][j].isAboveScreen()){
                                    //use waveEnemyPositions to assign enemy positions to homing bullet
                                    waveEnemyPositions[j][0] = enemies[i][j].getX();
                                    waveEnemyPositions[j][1] = enemies[i][j].getY();
                                    //if there are any wave enemies onscreen enemiesAreGone gets false
                                    enemiesAreGone = false;
                                }
                            }
                        }
                    }
                }

                //System.out.println("living enemies: " + livingEnemiesCounter);

                if(level.advanceWave(enemiesAreGone, topwave)){
                    waveTimeCounter = 0;
                }
                else if(Level.getWave() > topwave){
                    control = false;
                    endTimer++;
                    successMessage.setOpacity(1);
                    ship.setYSpeed(1);
                    ship.setXSpeed(0);
                    ship.setYDir(-1);
                    if(endTimer > 200)
                        ship.move(12);
                }

                for(int i = 0; i < waveEnemyPositions.length; i++){
                    if(waveEnemyPositions[i][0] == 0 && waveEnemyPositions[i][1] == 0){
                        for(int j = 0; j < waveEnemyPositions.length; j++){
                            if(waveEnemyPositions[j][0] != 0 && waveEnemyPositions[j][1] != 0){
                                waveEnemyPositions[i][0] = waveEnemyPositions[j][0];
                                waveEnemyPositions[i][1] = waveEnemyPositions[j][1];
                            }
                        }
                    }
                }

                //System.out.println("EnemiesAreGone: " + enemiesAreGone);
                //System.out.println("Kills: " + Enemy.getKills());

                //this will remain true for one cycle of the timer if there are no wave enemies onscreen, and the wave will 
                enemiesAreGone = true;

                //player timer calls
                ship.borderDetect();
                ship.animateLasers();
                ship.laserBorderDetect();
                ship.laserMove();
                ship.moveHomers(waveEnemyPositions);
                if(ship.testDeath() || success){
                    if(ship.testDeath())
                        death = true;
                    deathMessage.setOpacity(1);
                    deathMessage2.setOpacity(1);
                    deathMessage3.setOpacity(1);
                    if(showOpts){
                        g.add(t.showOptions(),BorderLayout.SOUTH);
                        g.validate();
                        showOpts = false;
                    }
                }
                ship.flightAnimate(laserCollideTotal);//flashes pink when shot
                ship.sendX(ship.getX());//sends ship's position to the Gun subclass
                ship.sendY(ship.getY());//  to show lasers being shot at the proper location

                //reset the total number of laser collisions for the benefit of ship.flightAnimate()
                laserCollideTotal = 0;

                for(int i = 0; i < waveEnemyPositions.length; i++){
                    for(int j = 0; j < waveEnemyPositions[i].length; j++){
                        waveEnemyPositions[i][j] = 0;
                    }
                    //System.out.println("wave enemies[" + i + "]: " + livingWaveEnemies[i]);
                }

                //move all powerups downscreen
                for(int i = 0; i < powerups.length; i++){
                    powerups[i].move(5);
                    powerups[i].animateFlight();
                    powerupGetArr = ship.powerUpCollideDetect(powerups[i].getX(), powerups[i].getY(), powerups[i].getType());
                    powerups[i].setX(powerupGetArr[0]);
                    if(powerupGetArr[1] == 1){
                        laserMessage.setOpacity(1);
                        laserMessageCounter = 50;
                    }
                }

                //update the player's health bar
                lifeBar.updateLife(ship.getDamage(), ship.getHealthIncrease());

                //stack and loop background images
                for(int i = 0; i < bgs.length; i++){
                    bgs[i].setY(bgs[i].getY() + 35);
                    if(bgs[i].getY() > 750){
                        bgs[i].setY(-6375);
                    }
                }

                //fade laser upgrade message after 25 ticks
                if(laserMessageCounter > 25){
                    laserMessageCounter -= 1;
                }
                else if(laserMessageCounter <= 25 && laserMessageCounter != 0){
                    laserMessageCounter -= 1;
                    if(laserMessageCounter % 3 == 0){
                        laserMessage.setOpacity(0);
                    }
                    else{
                        laserMessage.setOpacity(1);
                    }
                }

                if(startMessageCounter > 25){
                    startMessageCounter -= 1;
                }
                else if(startMessageCounter <= 25 && startMessageCounter != 0){
                    startMessageCounter -= 1;
                    if(startMessageCounter % 3 == 0){
                        startMessage.setOpacity(0);
                    }
                    else{
                        startMessage.setOpacity(1);
                    }
                }
                
                repaint();
                //ship.printStats();
            }
        });


        //keyListener tells Looper class when key is pressed by setting
        //the boolean 'looping?' variables
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(control){
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_DOWN:
                            ship.startMoving('d');
                            break;
                        case KeyEvent.VK_UP:
                            ship.startMoving('u');
                            break;
                        case KeyEvent.VK_LEFT:
                            ship.startMoving('l');
                            break;
                        case KeyEvent.VK_RIGHT:
                            ship.startMoving('r');
                            break;
                        case KeyEvent.VK_X:
                            ship.startShooting();
                            break;
                        case KeyEvent.VK_Z:
                            ship.launchMissiles();
                            break;
                    }
                }
                repaint();
            }

            //stop key events when key is released
            public void keyReleased(KeyEvent e) {
                if(control){
                    switch (e.getKeyCode()){
                        case KeyEvent.VK_RIGHT:
                            ship.stopMoving('r');
                        break;
                        case KeyEvent.VK_LEFT:
                            ship.stopMoving('l');
                        break;
                        case KeyEvent.VK_UP:
                            ship.stopMoving('u');
                        break;
                        case KeyEvent.VK_DOWN:
                            ship.stopMoving('d');
                        break;
                        case KeyEvent.VK_X:
                            ship.stopShooting();
                        break;
                    }

                    //border detection - duplicated for timer and key control separately
                    if(ship.getY() > 703)
                        ship.setY(702);
                    if(ship.getY() < 23)
                        ship.setY(24);
                    if(ship.getX() > 851)
                        ship.setX(850);
                    if(ship.getX() < 143)
                        ship.setX(144);
                }
            }
        });
        mover.start();
    }

    public void paintComponent(Graphics g){
        for(int i = 0; i < bgs.length; i++){
            bgs[i].draw(g);
        }
        for(int i = 0; i < powerups.length; i++){
            powerups[i].draw(g);
        }
        ship.draw(g);
        ship.drawLasers(g);
        for(int i = 0; i < enemies.length; i++){
            for(int j = 0; j < enemies[i].length; j++){
                if(enemies[i][j] != null){
                    enemies[i][j].draw(g);
                    enemies[i][j].drawLasers(g);
                    if(enemies[i][j].isShot() && enemies[i][j].isAlive()){
                        enemyLifeBars[i][j].draw(g, 300, 30);
                    }
                }
            }
        }
        if(death){
            redfilm.draw(g);
        }
        g.setColor(grey);
        g.fillRect(0, 0, 120, 750);
        g.fillRect(880, 0, 120, 750);
        lifeBar.draw(g);
        g.setFont(font);
        g.setColor(Color.yellow);
        g.drawString("Score", 2, 40);
        g.drawString("" + Enemy.getKillsHi(), 10, 65);
        g.drawString("Wave", 885, 40);
        g.drawString("" + level.getWave(), 920, 65);
        g.drawString("Homers", 880, 85);
        g.drawString("" + ship.getLaunchesLeft(), 920, 105);
        laserMessage.draw(g);
        deathMessage.draw(g);
        deathMessage2.draw(g);
        deathMessage3.draw(g);
        startMessage.draw(g);
        successMessage.draw(g);
        g.drawImage(offscreen,0,0,this);
    }
}