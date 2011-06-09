/******************************************************************************
"Spaceratops"
 * Written, directed, composed, programmed, animated, drawn, designed by Emmett Butler
 * Spring 2011
*******************************************************************************/
import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;

public class Spaceship extends Creature {
	
    protected String type;
    protected boolean alive = false;
    protected int shootAnimate = 0, flyAnimate = 1, hit = 0, hitAnimate = 0;
    protected boolean lasersLooped = false;
    protected boolean shot = false;
    protected int impactCount = 0, launchesLeft = 10;
    protected boolean healthIncrease = false;
    protected Looper horizontalLooper, verticalLooper;
    protected Gun gun;
    protected Laser[] homers;
    protected static Image[] imageBank = new Image[18];

    public static void loadImages(){
        path = Spaceship.class.getResource("images/dino2_wag_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[0] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_wag_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[1] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_wag_2.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[2] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_wag_3.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[3] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_wag_4.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[4] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_wag_5.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[5] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_0_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[6] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_0_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[7] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_0_2.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[8] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_0_3.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[9] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_0_4.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[10] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_0_5.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[11] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_1_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[12] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_1_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[13] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_1_2.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[14] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_1_3.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[15] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_1_4.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[16] = imageIcon.getImage();
        }
        path = Spaceship.class.getResource("images/dino2_hit_1_5.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[17] = imageIcon.getImage();
        }
    }

    public Spaceship(int x, int y, int size){
        super(x, y);
        totalHealth = 10;
        horizontalLooper = new Looper('h');
        verticalLooper = new Looper('v');
        gun = new Gun();
        homers = new Laser[16];
        for(int i = 0; i < homers.length; i++){
            homers[i] = new Laser(10000, 10000, "playerhoming");
        }
    }

    public void printStats(){
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
        System.out.println("xSpeed: " + xSpeed);
        System.out.println("ySpeed: " + ySpeed);
        System.out.println("xDir: " + xDir);
        System.out.println("yDir: " + yDir);
        System.out.println("Hit: " + hit);
        System.out.println("Damage: " + damage);
        System.out.println("Total Health: " + totalHealth);
        System.out.println("Alive: " + alive);
        System.out.println();
    }

    public void launchMissiles(){
        if(launchesLeft > 0){
            launchesLeft--;
            for(int i = 0; i < homers.length; i++){
                if(!homers[i].isShot()){
                    homers[i].setX(x + 32);
                    homers[i].setY(y - 5);
                    homers[i].setShot(true);
                    homers[i].randomImage();
                }
            }
        }
    }

    public void moveHomers(int [][] targetPositions){
        for(int i = 0; i < homers.length; i++){
            if(homers[i].isShot()){
                homers[i].home(targetPositions[i][0], targetPositions[i][1], 25, true);
                homers[i].randomImage();
            }
            homers[i].makeDeadOnFrameExit();
        }
    }

    public int[] homerEnemyCollideDetect(int x, int y, int width, int height){

        int[] returnArr = new int[2];

        for(int j = 0; j < homers.length; j++){
            //if(homers[j].getX() >= (x - width) && homers[j].getX() <= (x)
                    //&& homers[j].getY() <= ((y + height) - 140) && homers[j].getY() >= y - 140){
            if(Math.abs(homers[j].getY() - y) < 10 && Math.abs(homers[j].getX() - x) < 10){
                homers[j].setX(2000);
                homers[j].setShot(false);
                returnArr[0] = 50;
                returnArr[1] = 1;
            }
        }

        return returnArr;
    }

    public void flightAnimate(int hit){
        this.hit = hit;

        if(damage < totalHealth && hit != 1){
            image = imageBank[flyAnimate/3];
            flyAnimate++;
            if(flyAnimate == 18){
                flyAnimate = 3;
            }
        }
        if(this.hit != 0){
            hitAnimate = 1;
        }
        if(hitAnimate != 0){
            image = imageBank[(((hitAnimate%2)+1)*6)+((flyAnimate/3))];
            hitAnimate++;
        }
        if(hitAnimate == 6){
            hitAnimate = 0;
        }
    }

    public void setShootAnimateLimit(){
        lasersLooped = true;
        shootAnimate = gun.setShootAnimateLimit(shootAnimate);
    }

    public void laserBorderDetect(){
        gun.laserBorderDetect();
    }

    public void laserMove(){
        gun.laserMove();
    }

    public void lifeUp(){
        if(damage > 0)
            damage--;
    }

    public void missileUp(){
        if(launchesLeft < 20)
            launchesLeft++;
    }

    public int[] powerUpCollideDetect(int x, int y, String type){
        int[] returnArr = new int[2];

        if((Math.abs(this.y - y) < 80 && (Math.abs(this.x - x) < 80))){
            returnArr[0] = 1500;
            if(type == "laserup"){
                laserLevelUp();
                returnArr[1] = 1;
            }
            else if(type == "lifeup"){
                lifeUp();
                healthIncrease = true;
                returnArr[1] = 2;
            }
            else if(type == "missiles"){
                missileUp();
                returnArr[1] = 3;
            }
        }
        else{
            returnArr[0] = x;
        }
        return returnArr;
    }

    public void borderDetect(){
        if(alive){
            if(y > 703)
                y = 702;
            if(y < 23)
                y = 24;
            if(x > 851)
                x = 850;
            if(x < 143)
                x = 144;
        }
    }

    public int getLaunchesLeft(){
        return launchesLeft;
    }

    public void laserLevelUp(){
        gun.laserLevelUp();
    }
	
    public int[] laserEnemyCollideDetect(int x, int y, int width, int height){
        return gun.laserEnemyCollideDetect(x, y, width, height);
    }

    public void drawLasers(Graphics g){
        gun.drawLasers(g);
        for(int i = 0; i < homers.length; i++){
            homers[i].draw(g);
        }
    }

    public String setShipLaserImage(){
        return gun.setShipLaserImage(shootAnimate);
    }

    public void animateLasers(){
        gun.animateFlight();
    }

    public void startShooting(){
        gun.startShooting();
    }

    public void stopShooting(){
        gun.stopShooting();
        lasersLooped = true;
    }
	
    public void startMoving(char direction){
        if(direction == 'u'){
            verticalLooper.startLooping(-1);
        }
        else if(direction == 'd'){
            verticalLooper.startLooping(1);
        }
        else if(direction == 'r'){
            horizontalLooper.startLooping(1);
        }
        else if(direction == 'l'){
            horizontalLooper.startLooping(-1);
        }
    }

    public void stopMoving(char direction){
        if(direction == 'd' || direction == 'u'){
            verticalLooper.stopLooping();
        }
        else if(direction == 'r' || direction == 'l'){
            horizontalLooper.stopLooping();
        }
    }
	
    //-----------=GETTERS=--------------//

    public int getshootAnimate(){
        return shootAnimate;
    }

    public boolean getLasersLooped(){
        return lasersLooped;
    }

    public int getImpactCount(){
        return impactCount;
    }

    public boolean getHealthIncrease(){
        return healthIncrease;
    }

    //------------=SETTERS=--------------//

    public void setshootAnimate(int count){
        shootAnimate = count;
    }

    public void setLasersLooped(boolean looped){
        lasersLooped = looped;
    }

    public void setImpactCount(int count){
        impactCount = count;
    }
    
    public void setHealthIncrease(boolean increase){
        healthIncrease = increase;
    }

    public void sendX(int x){
        gun.setX(x);
    }

    public void sendY(int y){
        gun.setY(y);
    }

    //The Looper class controls the key events and avoids the system-default
    //delay on the repetition of held keys - that is, events looped by this class
    //happen with the same delay between each repetition
    class Looper extends Thread{

        private boolean firstTime = true;
        private boolean isLooping = false;
        private char axis;

        public Looper(char axis){
            this.axis = axis;
        }

        public void startLooping(int direction) {
            switch(axis){
                case 'h':
                    Spaceship.this.setXSpeed(1);
                    Spaceship.this.setXDir(direction);
                    break;
                case 'v':
                    Spaceship.this.setYSpeed(1);
                    Spaceship.this.setYDir(direction);
                    break;
            }
            if (firstTime) {
                start();
                firstTime = false;
            }
        }

        //called by parent class when button is released
        public void stopLooping() {
            //isLooping = false;
            switch(axis){
                case 'h':
                    Spaceship.this.setXDir(0);
                    break;
                case 'v':
                    Spaceship.this.setYDir(0);
                    break;
            }
        }

        //controls actions triggered by keypresses
        public void run() {
            try {
                while(true){
                    Spaceship.this.borderDetect();
                    Spaceship.this.move(16);
                    sleep(25);
                }
            } catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
            }
        }
    }
}

class Powerup extends Entity {

    String type;
    int powerupAnimateCount = 0;
    private static Image[] imageBank = new Image[5];

    public static void loadImages(){
        path = Powerup.class.getResource("images/laserup_8_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[0] = imageIcon.getImage();
        }
        path = Powerup.class.getResource("images/laserup_8_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[1] = imageIcon.getImage();
        }
        path = Powerup.class.getResource("images/lifeup_8_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[2] = imageIcon.getImage();
        }
        path = Powerup.class.getResource("images/lifeup_8_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[3] = imageIcon.getImage();
        }
        path = Powerup.class.getResource("images/missileup_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[4] = imageIcon.getImage();
        }
    }

    public Powerup(int x, int y, int size){
        super(x, y);
    }

    public void show(int damage, int totalHealth, boolean alive, int x, int y){
        if(damage >= totalHealth){
            if(alive){
                Enemy.setKillsHi(Enemy.getKillsHi() + 1);
                if(Enemy.getKills() != 0){
                    if(this.type == "laserup"){
                        if(Enemy.getKills() % 189 == 0){
                            this.x = x;
                            this.y = y;
                            yDir = 1;
                        }
                    }
                    else if(this.type == "lifeup"){
                        if(Enemy.getKills() % 30 == 0){
                            this.x = x;
                            this.y = y;
                            yDir = 1;
                        }
                    }
                    else if(this.type == "missiles"){
                        if(Enemy.getKills() % 37 == 0){
                            this.x = x;
                            this.y = y;
                            yDir = 1;
                        }
                    }
                }
            }
        }
    }

    public void animateFlight(){
        if(type == "laserup")
            image = imageBank[(powerupAnimateCount/4)];
        else if(type == "lifeup")
            image = imageBank[2+(powerupAnimateCount/4)];
        else if(type == "missiles")
            image = imageBank[4];

        powerupAnimateCount++;

        if(powerupAnimateCount == 7){
            powerupAnimateCount = 0;
        }
    }

    public String getType(){
        return type;
    }
}

class Laserup extends Powerup {
	
    public Laserup(int x, int y, int size){
        super(x, y, size);
        type = "laserup";
    }
}

class Lifeup extends Powerup {
	
    public Lifeup(int x, int y, int size){
        super(x, y, size);
        type = "lifeup";
    }
}

class Missileup extends Powerup {

    public Missileup(int x, int y, int size){
        super(x, y, size);
        type = "missiles";
    }
}