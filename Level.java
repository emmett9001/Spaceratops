/******************************************************************************
"Spaceratops"
 * Written, directed, composed, programmed, animated, drawn, designed by Emmett Butler
 * Spring 2011
*******************************************************************************/
import java.io.InputStream;

import java.util.ArrayList;

import javax.swing.JComponent;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Cursor;

public class Level {

    static int wave;
    protected int score;
    //protected int waveEnemiesNumber;

    Level(int wave){
        this.wave = wave;
        score = 0;
        //this.waveEnemiesNumber = 16;
    }

    public static Font getFont(){
        Font font = null;
        Font ttfBase;
        Font ttfReal;
        String fName = "data/m04b.TTF";
        try {
            //InputStream myStream = new BufferedInputStream(new FileInputStream("m04b.ttf"));
	    //ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
	    //ttfReal = ttfBase.deriveFont(Font.PLAIN, 24);
            InputStream is = GamePanel.class.getResourceAsStream(fName);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(fName + " not loaded.  Using serif font.");
            font = new Font("serif", Font.PLAIN, 24);
        }
        return font;
    }

    public static int numOfZeroes(int[][] array){
        int num = 0;

        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){
                if(array[i][j] == 0)
                    num += 1;
            }
        }

        return num;
    }

    public static int numOfOnes(int[][] array){
        int num = 0;

        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){
                if(array[i][j] == 1)
                    num += 1;
            }
        }

        return num;
    }

    public int[] generateOrder(){
        int[] order = new int[6];

        for(int i = 0; i < order.length; i++){
            order[i] = ((int)(Math.random() * 6));
        }

        return order;
    }

    public boolean advanceWave(boolean enemiesAreGone, int topwave){
        if((/*waveKills >= waveEnemiesNumber ||*/ enemiesAreGone) && this.wave <= topwave){
            this.wave++;
            //this.waveEnemiesNumber += 16;
            return true;
        }
        return false;
    }

    //public int getWaveEnemies(){
        //return waveEnemiesNumber;
    //}

    public static int getWave(){
        return wave;
    }

    public int getScore(){
        return score;
    }

    public int setScore(int x){
        score += x;
        return score;
    }
}

class MyText extends Entity{
    //----------VARIABLES--------------------
    private String text = "Sample text!";
    private float red = 0;
    private float green = 0;
    private float blue = 0;
    private float opacity = 255;
    private float size = 40;
    private Font font;

    //----------CONSTRUCTOR------------------
    public MyText(String text, float red, float green, float blue, float opacity, int x, int y, float size) {
        super(x, y);
        this.text = text;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
        this.size = size;
        font = Level.getFont();
    }

    //----------METHODS----------------------
    public void draw(Graphics g) {
        // Draw text
        g.setColor(new Color(red, green, blue, opacity));
        g.setFont(font.deriveFont(size));
        g.drawString(text, x, y);
    }

    // Getters
    public String getText() {
        return text;
    }

    public float getOpacity() {
        return opacity;
    }

    // Setters
    public void setText(String text) {
        this.text = text;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }
}

class ClickMe extends JComponent implements MouseListener {

    private Dimension size;

    private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

    public String label;
    private Font font = Level.getFont();

    private boolean mouseEntered = false, mousePressed = false;

    public ClickMe(ActionListener e, String label){
        super();
        enableInputMethods(true);
        addMouseListener(this);

        size = new Dimension(label.length()*20, 50);

        setSize(size.width,size.height);
        setFocusable(true);

        this.label = label;
    }

    public void paintComponent(Graphics g){
        //super.paintComponent(g);

        g.setFont(font.deriveFont((float)20));
        g.setColor(Color.YELLOW);

        if(mouseEntered)
            g.setColor(Color.BLUE);

        g.drawString(label, 0, 40);
    }

    public void mouseClicked(MouseEvent e){
    }

    public void mouseEntered(MouseEvent e){
        mouseEntered = true;
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        repaint();
    }

    public void mouseExited(MouseEvent e){
        mouseEntered = false;
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        repaint();
    }

    public void mousePressed(MouseEvent e){
        notifyListeners(e);
        mousePressed = true;
        repaint();
    }

    public void mouseReleased(MouseEvent e){
        mousePressed = false;
        System.out.println("Something happens!");
        repaint();
    }

    public void addActionListener(ActionListener listener){
        listeners.add(listener);
    }

    private void notifyListeners(MouseEvent e){
        ActionEvent evt  = new ActionEvent(this,ActionEvent.ACTION_PERFORMED,new String(),e.getWhen(),e.getModifiers());

        synchronized(listeners){
            for(int i = 0; i < listeners.size(); i++){
                ActionListener tmp = listeners.get(i);
                tmp.actionPerformed(evt);
            }
        }
    }

    public Dimension getPreferredSize(){
        return new Dimension(getWidth(), getHeight());
    }

    public Dimension getMinimumSize(){
        return getPreferredSize();
    }
    public Dimension getMaximumSize(){
        return getPreferredSize();
    }
}