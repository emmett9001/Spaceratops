/******************************************************************************
"Spaceratops"
 * Written, directed, composed, programmed, animated, drawn, designed by Emmett Butler
 * Spring 2011
*******************************************************************************/
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.net.*;
import java.applet.*;

public class Entity {

    protected int x, y, size, xDir, yDir, dy, dx, homeCount = 0, width, height;
    protected double xSpeed = 1, ySpeed = 1, speed, sep, angle = 0.0;
    protected boolean homing = true;
    protected AffineTransform affineTransform = new AffineTransform();
    protected Image image;
    protected static ImageIcon imageIcon;
    protected static URL path;

    Entity(int x, int y){
        this.x = x;
        this.y = y;
        this.xDir = 0;
        this.yDir = 0;
    }

    public void printStats(){
        System.out.println("XDir: " + xDir);
        System.out.println("YDir: " + yDir);
        System.out.println("YSpeed: " + ySpeed);
        System.out.println("XSpeed: " + xSpeed);
    }

    public void move(int multiplier){
        x += xSpeed * xDir * multiplier;
        y += ySpeed * yDir * multiplier;
    }

    public void move(){
        x += xSpeed * xDir;
        y += ySpeed * yDir;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isAboveScreen(){
        if(y < 23 && x < 900 && x > 50){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isOnscreen(){
        if( x > -100 && y > -100 && x < 851 && y < 749){
            return true;
        }
        return false;
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        affineTransform.setToTranslation(x - (size / 2), y - (size / 2));
        affineTransform.rotate(angle, size / 2, size / 2);
        g2d.drawImage(image, affineTransform, null);
    }

    //------------GETTERS--------------//
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getSize(){
        return size;
    }

    public double getAngle(){
        return angle;
    }

    public int getXDir(){
        return xDir;
    }

    public int getYDir(){
        return yDir;
    }

    public double getXSpeed(){
        return xSpeed;
    }

    public double getYSpeed(){
        return ySpeed;
    }

    //-------------SETTERS-------------//

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setXDir(int xDir){
        this.xDir = xDir;
    }

    public void setYDir(int yDir){
        this.yDir = yDir;
    }

    public void setXSpeed(double xSpeed){
        if(xSpeed < 20)
            this.xSpeed = xSpeed;
        else
            this.xSpeed = 20;
    }

    public void setYSpeed(double ySpeed){
        if(ySpeed < 20)
            this.ySpeed = ySpeed;
        else
            this.ySpeed = 20;
    }

    public void setAngle(double angle){
        this.angle = angle;
    }

    public void setImage(String icon){
        path = Creature.class.getResource(icon);
        if (path != null) {
            imageIcon = new ImageIcon(path);
            image = imageIcon.getImage();
        }
    }
}

class Creature extends Entity {
	
    protected boolean shot, alive;
    protected int totalHealth, damage, explodeCount = 0;
    protected AudioClip explode;
    protected static Image[] imageBank = new Image[9];

    public static void loadImages(){
        path = Creature.class.getResource("images/explode0_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[0] = imageIcon.getImage();
        }
        path = Creature.class.getResource("images/explode1_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[1] = imageIcon.getImage();
        }
        path = Creature.class.getResource("images/explode2_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[2] = imageIcon.getImage();
        }
        path = Creature.class.getResource("images/explode3_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[3] = imageIcon.getImage();
        }
        path = Creature.class.getResource("images/explode4_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[4] = imageIcon.getImage();
        }
        path = Creature.class.getResource("images/explode5_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[5] = imageIcon.getImage();
        }
        path = Creature.class.getResource("images/explode6_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[6] = imageIcon.getImage();
        }
        path = Creature.class.getResource("images/explode7_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[7] = imageIcon.getImage();
        }
        path = Creature.class.getResource("images/explode8_8.png");
        if(path != null){
            imageIcon = new ImageIcon(path);
            imageBank[8] = imageIcon.getImage();
        }
    }

    Creature(int x, int y){
        super(x, y);
        
    }

    //----------=SETTERS=------------//

    public void setShot(boolean shot){
        this.shot = shot;
    }

    public void makeAlive(){
        alive = true;
    }

    public boolean testDeath(){
        if(damage >= totalHealth){
            xSpeed = 0;
            ySpeed = 0;
            alive = false;
            deathAnimate();
            return true;
        }
        else{
            return false;
        }
    }

    public void deathAnimate(){
        image = imageBank[explodeCount/3];
        explodeCount += 1;
        if(explodeCount == 27){                     
            x = 2000;
            explodeCount = 0;
        }
    }

    public void setTotalHealth(int totalHealth){
        this.totalHealth = totalHealth;
    }

    public void setDamage(int damage){
        //if(this.damage < this.totalHealth){
            this.damage = damage;
        //}
    }

    public void setExplodeCount(int count){
        explodeCount = count;
    }

    //-----------=GETTERS=-----------//

    public boolean isShot(){
        return shot;
    }

    public boolean isAlive(){
        return alive;
    }

    public int getTotalHealth(){
        return totalHealth;
    }

    public int getDamage(){
        return damage;
    }

    public int getExplodeCount(){
        return explodeCount;
    }
}