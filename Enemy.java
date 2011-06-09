/******************************************************************************
"Spaceratops"
 * Written, directed, composed, programmed, animated, drawn, designed by Emmett Butler
 * Spring 2011
*******************************************************************************/
import javax.swing.ImageIcon;
import java.awt.Image;

import java.awt.Graphics;

public class Enemy extends Creature {
	
    protected Laser[] lasers;
    protected int laserCount, waveStepper, shootCount, step = -1, movementCounter = 0, moveStartTime, shootStartTime, targetX = 0, targetY = 0, imgNum;
    protected String type;
    protected int[] waves = new int[20];
    protected boolean boundaryDetect = true, isHoming;
    protected static int killHi = 0, enemiesKilled = 0;
    protected static Image[] imageBank = new Image[9];

    public static void loadImages(){
        path = Enemy.class.getResource("images/drone.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[0] = imageIcon.getImage();
        }
        path = Enemy.class.getResource("images/grunt1_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[1] = imageIcon.getImage();
        }
        path = Enemy.class.getResource("images/grunt2_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[2] = imageIcon.getImage();
        }
        path = Enemy.class.getResource("images/medenemy2.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[3] = imageIcon.getImage();
        }
        path = Enemy.class.getResource("images/dino.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[4] = imageIcon.getImage();
        }
        path = Enemy.class.getResource("images/spider1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[5] = imageIcon.getImage();
        }
        path = Enemy.class.getResource("images/rocket1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[6] = imageIcon.getImage();
        }
        path = Enemy.class.getResource("images/rainbow.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[7] = imageIcon.getImage();
        }
        path = Enemy.class.getResource("images/bigbird.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[8] = imageIcon.getImage();
        }
    }

    Enemy(int x, String name, int imgNum, int moveStartTime, int totalHealth, int width, int height, boolean homer){
        super(x, -550);
        this.width = width;
        this.height = height;
        this.moveStartTime = moveStartTime;
        this.shootStartTime = 100;
        waveStepper = 0;
        isHoming = homer;
        xDir = 0;
        yDir = 1;
        shootCount = 0;
        this.type = name;
        killHi = 0;
        enemiesKilled = 0;

        this.imgNum = imgNum;
        image = imageBank[imgNum];

        lasers = new Laser[80];
        for(int i = 0; i < lasers.length; i++){
            if(!isHoming)
                lasers[i] = new Laser(0, 1000, "enemy");
            else
                lasers[i] = new Laser(0, 1000, "enemyhoming");
        }
        this.totalHealth = totalHealth;
        
    }

    public void reset(int x, int y){
        incrementWaveStepper();
        damage = 0;
        movementCounter = 0;
        laserCount = 0;
        step = -1;
        angle = Math.PI;
        image = imageBank[imgNum];
        if(isHoming){
            for(int i = 0; i < lasers.length; i++){
                lasers[i].resetHoming();
            }
        }
        xSpeed = 1;
        ySpeed = 1;
        xDir = 0;
        yDir = 1;
        shootCount = 0;
        alive = false;
        this.x = x;
        this.y = y;
    }

    public void setBoundaryDetect(boolean bound){
        boundaryDetect = bound;
    }

    public void incrementMoveCount(){
        movementCounter++;
    }

    public void resetMoveCount(){
        movementCounter = 0;
    }

    public int getMoveCount(){
        return movementCounter;
    }

    public String getType(){
        return type;
    }

    public int getMoveStartTime(){
        return moveStartTime;
    }

    public void setMoveStartTime(int x){
        moveStartTime = x;
    }

    public int getShootStartTime(){
        return shootStartTime;
    }

    public int getShootCount(){
        return shootCount;
    }

    public int getWaveStepper(){
        return waveStepper;
    }

    public void incrementWaveStepper(){
        if(waveStepper < waves.length)
            waveStepper++;
    }

    public static void setKillsHi(int kills){
        killHi = kills;
        enemiesKilled = killHi/81;
    }

    public static void setKills(int kills){
        enemiesKilled = kills;
    }

    public static int getTotalKills(){
        return enemiesKilled;
    }

    public static void resetKills(){
        killHi = 0;
    }

    public static int getKillsHi(){
        return killHi;
    }

    public static int getKills(){
        return enemiesKilled;
    }

    public boolean isHoming(){
        return isHoming;
    }

    public boolean isWave(int wave){
        //System.out.println("wave: " + waves[waveStepper]);
        if(wave == waves[waveStepper]){
            return true;
        }
        else{
            return false;
        }
    }

    public int getWave(){
        return waves[waveStepper];
    }

    public void borderDetect(){
        if(alive && boundaryDetect){
            if(y > 703){
                angle = angle + (2 * ((Math.PI/2) - angle));
                y = 702;
                yDir = -1;
            }
            if(y < 23){
                angle = angle + (2 * ((Math.PI/2) - angle));
                y = 24;
                yDir = 1;
            }
            if(x > 851){
                angle = angle - (2 * ((Math.PI/2) - angle));
                x = 850;
                xDir = -1;
            }
            if(x < 143){
                angle = angle - (2 * ((Math.PI/2) - angle));
                x = 144;
                xDir = 1;
            }
        }
    }

    public void incrementShootCount(){
        this.shootCount++;
    }

    public void randomShootCount(boolean big){
        int period;

        if(big)
            period = 5;
        else if(!isHoming){
            period = (-5 * Level.getWave()) + 300 ;
        }
        else{
            period = (-5 * Level.getWave()) + 400;
        }
            
        shootCount = ((int)(Math.random() * period));
    }

    public void shoot(int shipX, int shipY, boolean homing){
        if(this.shootCount == 1){
            lasers[laserCount].setXDir(0);
            lasers[laserCount].setYDir(1);
            lasers[laserCount].setX(x - 4);
            lasers[laserCount].setY(y - 5);
            lasers[laserCount].setShot(true);

            if(this.isHoming)
                lasers[laserCount].resetHoming();

            laserCount++;
            if(laserCount > (lasers.length - 1))
                laserCount = 0;

        }
        for(int i = 0; i < lasers.length; i++){
            if(lasers[i].isShot()){
                if(homing){
                    lasers[i].home(shipX, shipY, 9, false);
                }
                else{
                    lasers[i].move(24);
                }
            }
            lasers[i].makeDeadOnFrameExit();
        }
    }

    public int[] laserCollideDetect(int shipY, int shipX){

        int count = 0;
        int[] returnArr = new int[2];

        for(int i = 0; i < lasers.length; i++){
            if(lasers[i].isShot())
                count += lasers[i].collideDetect(shipY, shipX);
        }

        returnArr[0] = count;

        if(count != 0){
            returnArr[1] = 1;
        }
        
        return returnArr;
    }
    
    public void makeAliveOnFrameEnter(){
        if(x > 143 && y > 23 && x < 851 && y < 703){
            alive = true;
        }
    }

    public void makeDeadOnFrameExit(){
        if(x < 0 || x > 900 || y < -300 || y > 750){
            alive = false;
        }
    }

    public void drawLasers(Graphics g){
        for(int i = 0; i < lasers.length; i++){
            lasers[i].draw(g);
        }
    }

    public void movePattern(double[][] pattern){
        
        if(this.movementCounter == pattern[step + 1][5]){
            if(pattern[step + 1][0] == 2){
                this.boundaryDetect = false;
            }
            else{
                step++;
                xSpeed = pattern[step][0];
                ySpeed = pattern[step][1];
                xDir = (int)pattern[step][2];
                yDir = (int)pattern[step][3];
                angle = pattern[step][4];
            }
        }
    }
}

class Drone extends Enemy {

    Drone(int x, int moveStartTime){
        super(x, "images/drone.png", 0, moveStartTime, 5, 32, 32, false);
     
        waves[0] = 0;
        waves[1] = 1;
        waves[2] = 2;
        waves[3] = 3;
        waves[4] = 4;
        waves[5] = 5;
        waves[6] = 8;
        waves[7] = 9;
        waves[8] = 10;
        waves[9] = 14;
        waves[10] = 15;
        waves[11] = 18;
        waves[12] = 19;
        waves[13] = 37;
        waves[14] = 38;
    }
}

class Grunt extends Enemy {

    Grunt(int x, int moveStartTime){
        super(x, "images/grunt1_8.png", 1, moveStartTime, 5, 64, 64, false);
        waves[0] = 4;
        waves[1] = 5;
        waves[2] = 6;
        waves[3] = 7;
        waves[4] = 10;
        waves[5] = 11;
        waves[6] = 18;
        waves[7] = 19;
        waves[8] = 20;
        waves[9] = 21;
        waves[10] = 22;
        waves[11] = 31;
        waves[12] = 42;
        waves[13] = 43;
    }
}

class Grunt2 extends Enemy {
		
    Grunt2(int x, int moveStartTime){
        super(x, "images/grunt2_8.png", 2, moveStartTime, 15, 64, 64, false);
        waves[0] = 6;
        waves[1] = 7;
        waves[2] = 11;
        waves[3] = 12;
        waves[4] = 13;
        waves[5] = 16;
        waves[6] = 17;
        waves[7] = 20;
        waves[8] = 23;
        waves[9] = 24;
        waves[10] = 25;
        waves[11] = 26;
        waves[12] = 39;
    }
}

class MediumEnemy2 extends Enemy {

    MediumEnemy2(int x, int moveStartTime){
        super(x, "images/medenemy2.png", 3, moveStartTime, 30, 76, 76, false);
        waves[0] = 21;
        waves[1] = 22;
        waves[2] = 27;
        waves[3] = 28;
        waves[4] = 29;
        waves[5] = 30;
        waves[6] = 40;
        waves[7] = 41;
    }
}

class Dino extends Enemy {

    Dino(int x, int moveStartTime){
        super(x, "images/dino.png", 4, moveStartTime, 15, 64, 64, false);
        waves[0] = 8;
        waves[1] = 9;
        waves[2] = 12;
        waves[3] = 13;
        waves[4] = 23;
        waves[5] = 24;
        waves[6] = 29;
        waves[7] = 30;
        waves[8] = 32;
        waves[9] = 33;
        waves[10] = 34;
        waves[11] = 44;
        waves[12] = 45;
    }
}

class Spider1 extends Enemy {

    Spider1(int x, int moveStartTime){
        super(x, "images/spider1.png", 5, moveStartTime, 30, 90, 90, true);
        waves[0] = 14;
        waves[1] = 15;
        waves[2] = 16;
        waves[3] = 17;
        waves[4] = 25;
        waves[5] = 26;
        waves[6] = 27;
        waves[7] = 28;
        waves[8] = 35;
        waves[9] = 36;
        waves[10] = 46;
        waves[11] = 47;
    }
}

class Rocket extends Enemy {

    Rocket(int x, int moveStartTime){
        super(x, "images/rocket1.png", 6, moveStartTime, 60, 90, 90, true);
        waves[0] = 31;
        waves[1] = 32;
        waves[2] = 33;
        waves[3] = 34;
        waves[4] = 35;
        waves[5] = 36;
    }
}

class Rainbow extends Enemy {

    Rainbow(int x, int moveStartTime){
        super(x, "images/rainbow.png", 7, moveStartTime, 80, 90, 90, true);
        waves[0] = 37;
        waves[1] = 38;
        waves[2] = 39;
        waves[3] = 40;
        waves[4] = 41;
        waves[5] = 48;
        waves[6] = 49;
    }
}

class BigBird extends Enemy {

    BigBird(int x, int moveStartTime){
        super(x, "images/bigbird.png", 8, moveStartTime, 100, 200, 140, true);
        waves[0] = 42;
        waves[1] = 43;
        waves[2] = 44;
        waves[3] = 45;
        waves[4] = 46;
        waves[5] = 47;
        waves[6] = 48;
        waves[7] = 49;
    }
}