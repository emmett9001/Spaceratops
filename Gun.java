/******************************************************************************
"Spaceratops"
 * Written, directed, composed, programmed, animated, drawn, designed by Emmett Butler
 * Spring 2011
*******************************************************************************/
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Graphics;

import java.net.URL;

public class Gun {
 
    protected Laser[] lasers;
    protected Looper laserLoop;
    protected int laserCount;
    protected int laserAnimateCounter = 0;
    protected int x = 0, y = 0;

    public Gun(){
        laserLoop = new Looper();
        lasers = new Laser[16];
        for(int i = 0; i < lasers.length; i++){
            lasers[i] = new Laser(0, 0, "player");
            lasers[i].setX(10000);
        }
    }

    public String endShootAnimate(boolean looped){

        String returnVal = "";

        for(int i = 0; i < lasers.length; i++){
            if(!lasers[i].isShot() && looped == true){
                returnVal = "images/dino2.png";
            }
            else
                returnVal = "";
        }

        return "images/dino2.png";
    }

    public String animateShooter(boolean looped, int shootAnimate){
        if(looped == false){
            return "images/shiplaser" + lasers[0].getLevel() + "_" + shootAnimate + ".png";
        }
        else
            return "images/dino2.png";
    }

    public int setShootAnimateLimit(int shootAnimate){

        int returnVal = 0;

        switch(lasers[0].getLevel()){
            case 1:
                if(shootAnimate == 3)
                    returnVal = 0;
                else
                    returnVal = shootAnimate;
                break;
            case 2:
                if(shootAnimate == 6)
                    returnVal = 0;
                else
                    returnVal = shootAnimate;
                break;
            case 3:
                if(shootAnimate == 15)
                    returnVal = 0;
                else
                    returnVal = shootAnimate;
                break;
            case 4:
                if(shootAnimate == 15)
                    returnVal = 0;
                else
                    returnVal = shootAnimate;
                break;
            case 5:
                if(shootAnimate == 15)
                    returnVal = 0;
                else
                    returnVal = shootAnimate;
                break;
        }

        return returnVal;
    }

    public void laserBorderDetect(){
        for(int i = 0; i < lasers.length; i++){
            if(lasers[i].getY() < -150){
                lasers[i].setShot(false);
                lasers[i].setX(4000);
            }
        }
    }

    public void laserMove(){
        for(int i = 0; i < lasers.length; i++){
            if(lasers[i].isShot())
                lasers[i].move(40);
        }
    }

    public void laserLevelUp(){
        for(int j = 0; j < lasers.length; j++){
            if(lasers[j].getLevel() < 5){
                lasers[j].levelUp();
            }
        }
    }

    public void animateFlight(){
        for(int i = 0; i < lasers.length; i++){
            lasers[i].animateFlight(laserAnimateCounter);
        }
        laserAnimateCounter++;
        if(laserAnimateCounter == 7){
            laserAnimateCounter = 0;
        }
    }

    public int[] laserEnemyCollideDetect(int x, int y, int width, int height){

        int[] returnArr = new int[2];

        for(int j = 0; j < lasers.length; j++){
            if(width < 70){
                if((Math.abs(lasers[j].getY() - y)) < 30 && (Math.abs(lasers[j].getX() - x)) < 70){
                    lasers[j].setX(2000);
                    returnArr[0] = lasers[j].getDamage();
                    returnArr[1] = 1;
                }
            }
            else{
                if(lasers[j].getX() >= (x - width) && lasers[j].getX() <= (x)
                        && lasers[j].getY() <= ((y + height) - 140) && lasers[j].getY() >= y - 140){
                    lasers[j].setX(2000);
                    returnArr[0] = lasers[j].getDamage();
                    returnArr[1] = 1;
                }
            }

            //System.out.println("enemy: (" + x + "," + y + ") - laser: (" + lasers[j].getX() + "," + lasers[j].getY() + ")");
        }

        return returnArr;
    }

    public String setShipLaserImage(int shootAnimate){
        return "images/shiplaser" + lasers[Laser.getLaserCounter()].getLevel() +
            "_" + shootAnimate + ".png";
    }

    public void drawLasers(Graphics g){
        for(int i = 0; i < lasers.length; i++){
            lasers[i].draw(g);
        }
    }

    public void startShooting(){
        laserLoop.startLooping();
    }

    public void stopShooting(){
        laserLoop.stopLooping();
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    class Looper extends Thread{

        private boolean firstTime = true;
        private boolean isLooping = false;

        public Looper(){}

        public void startLooping(){
            isLooping = true;
            if (firstTime) {
                start();
                firstTime = false;
            }
        }

        //called by parent class when button is released
        public void stopLooping() {
            isLooping = false;
        }

        //controls actions triggered by keypresses
        public void run() {
            try {
                while(true){
                    if(isLooping){
                        lasers[laserCount].setShot(true);
                        lasers[laserCount].setX(x + 2);
                        lasers[laserCount].setY(y - 64);
                        lasers[laserCount].setXDir(0);
                        lasers[laserCount].setYDir(-1);

                        laserCount++;

                        if(laserCount > 15)
                            laserCount = 0;
                    }

                    sleep(75);
                }
            } catch (InterruptedException ie) {
                System.out.println(ie.getMessage());
            }
        }
    }
}

class Laser extends Entity{
	
    protected boolean shot = false, offScreenFlag = false, gotClose;
    protected String owner;
    protected int level = 1;
    protected int damageAmount;
    static int laserCounter = 0;
    protected static Image[] imageBank = new Image[19];

    public static void loadImages(){
        path = Laser.class.getResource("images/laser1_8_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[0] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/laser1_8_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[1] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/laser2_8_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[2] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/laser2_8_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[3] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/laser3_8_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[4] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/laser3_8_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[5] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/laser4_8_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[6] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/laser4_8_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[7] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/laser5_8_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[8] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/laser5_8_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[9] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/enemylaser8_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[10] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/enemyhomer.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[11] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/missileup_0.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[12] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/missileup_1.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[13] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/missileup_2.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[14] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/missileup_3.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[15] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/missileup_4.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[16] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/missileup_5.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[17] = imageIcon.getImage();
        }
        path = Laser.class.getResource("images/missileup_6.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[18] = imageIcon.getImage();
        }
    }

    public Laser(int x, int y, String owner){
        super(x, y);
        this.level = level;
        this.gotClose = false;
        setDamage();
        this.owner = owner;

        if(owner == "player") {
            image = imageBank[0];
            width = 64;
        }
        else if(owner == "enemy") {
            image = imageBank[10];
        }
        else if(owner == "enemyhoming") {
            image = imageBank[11];
        }
        else if(owner == "playerhoming") {
            image = imageBank[12];
        }
    }

    public void home(int targetX, int targetY, int rate, boolean aggressive){
        int scale = rate;

        if(homing){
            dy = targetY - y;
            dx = targetX - x;
            sep = Math.sqrt(dx * dx + dy * dy);
            speed = scale/sep;
        }

        if(sep < 150 && sep > 140 && !aggressive){
            homing = false;
        }

        angle += (Math.PI/16);

        x += dx * speed;
        y += dy * speed;
    }

    public void resetHoming(){
        homing = true;
        if(!this.isOnscreen())
            shot = false;
    }

    public void randomImage(){
        image = imageBank[((int)(Math.random() * 7) + 12)];
    }

    public int collideDetect(int x, int y){
        if(Math.abs(this.x - (x + 34)) < 30 && Math.abs(this.y - y) < 20){
            this.x = 2000;
            return 1;
        }
        return 0;
    }

    public void makeDeadOnFrameExit(){
        if(y > 1000 || y < -100 || x > 980 || x < -30){
            shot = false;
            x = 4000;
        }
    }

    //---------SETTERS------------//
    public void setShot(boolean shot){
        this.shot = shot;
    }

    public void levelUp(){
        level++;
        setDamage();
    }

    public void gotClose(){
        gotClose = true;
    }

    public void setHoming(boolean homing){
        this.homing = homing;
    }

    public void levelDown(){
        level--;
    }

    public void setDamage(){
        if(level == 1)
            damageAmount = 1;
        else if(level == 2)
            damageAmount = 2;
        else if(level == 3)
            damageAmount = 3;
        else if(level == 4)
            damageAmount = 7;
        else if(level == 5)
            damageAmount = 12;
    }

    public void animateFlight(int count){
        image = imageBank[(((2*level)-2))+(count%2)];
    }

    static void setLaserCounter(int count){
        laserCounter = count;
    }

    //--------=GETTERS=----------//
    public boolean isShot(){
        return shot;
    }

    public int getLevel(){
        return level;
    }

    public int getDamage(){
        return damageAmount;
    }

    static int getLaserCounter(){
        return laserCounter;
    }
}

class Background {

    protected int size = 9;

    protected int y;
    protected int[] paneY = new int[size];
    protected ImageIcon[] imageIcons = new ImageIcon[size];
    protected Image[] images = new Image[size];;
    protected URL[] paths = new URL[size];
    protected ImageIcon filmIcon;
    protected Image filmImage;
    protected URL filmURL;

    Background(int y){
        for(int i = 0; i < images.length; i++){
            paths[i] = Laser.class.getResource("images/background_" + ((int)(Math.random() * size)) + ".png");
            if (paths[i] != null) {
                imageIcons[i] = new ImageIcon(paths[i]);
                images[i] = imageIcons[i].getImage();
            }
            paneY[0] = y;
            if(i != 0){
                paneY[i] = paneY[i-1] + 750;
            }
        }
    }

    Background(int y, String image){
        for(int i = 0; i < images.length; i++){
            paths[i] = Laser.class.getResource(image);
            if (paths[i] != null) {
                imageIcons[i] = new ImageIcon(paths[i]);
                images[i] = imageIcons[i].getImage();
            }
            paneY[0] = y;
            if(i != 0){
                paneY[i] = paneY[i-1] + 750;
            }
        }
    }

    //--------------=GETTERS=-----------//

    public int getY(){
        return paneY[0];
    }

    //-------------=SETTERS=------------//
    public void setY(int y){
        for(int i = 0; i < images.length; i++){
            paneY[0] = y;
            if(i != 0){
                paneY[i] = paneY[i-1] + 750;
            }
        }

    }

    public void draw(Graphics g){
        for(int i = 0; i < images.length; i++){
            g.drawImage(images[i], 120, paneY[i], null);
        }
    }
}

class Filter {

    protected Image image;
    protected ImageIcon imageIcon;
    protected URL path;

    public Filter(String path){
        this.path = Laser.class.getResource(path);
        if(this.path != null){
            imageIcon = new ImageIcon(this.path);
            image = imageIcon.getImage();
        }
    }

    public void draw(Graphics g){
        g.drawImage(image, 0, 0, null);
    }
}