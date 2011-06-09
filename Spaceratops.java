/******************************************************************************
"Spaceratops"
 * Written, directed, composed, programmed, animated, drawn, designed by Emmett Butler
 * Spring 2011
*******************************************************************************/
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import java.net.URL;

import java.applet.AudioClip;
import java.applet.Applet;

class StoryPanel extends JPanel {

    Image bgImage, textImage;
    ImageIcon bgImageIcon, textImageIcon;
    URL bgPath, textPath;
    int textY = 580;
    boolean done = false;

    StoryPanel(final AudioClip song) {
        setLayout(new BorderLayout());
        bgPath = getClass().getResource("images/background_9large.png");
        if (bgPath != null) {
            bgImageIcon = new ImageIcon(bgPath);
            bgImage = bgImageIcon.getImage();
        }
        textPath = getClass().getResource("images/story.png");
        if (textPath != null) {
            textImageIcon = new ImageIcon(textPath);
            textImage = textImageIcon.getImage();
        }



        Timer timer = new Timer(90, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(textY > -688)
                    textY -= 2;
                repaint();
            }
        });

        timer.start();

    }

    protected void paintComponent(Graphics g) {
        g.drawImage(bgImage, 0, 0, this);
        g.drawImage(textImage, 0, textY, this);
    }
}

public class Spaceratops extends JFrame {
	
    private ClickMe start = new ClickMe(null,"Start");
    private ClickMe cont_story = new ClickMe(null,"Start the Game!");
    private ClickMe howto = new ClickMe(null,"How to play");
    private ClickMe cont_howto = new ClickMe(null,"Start the Game!");
    private ClickMe scoreButton = new ClickMe(null,"Scores");
    private ClickMe entername = new ClickMe(null,"Enter your name");
    private boolean contClicked = false;
    private URL audioPath;
    private AudioClip song;
    JPanel p = new JPanel();
    JPanel q1 = new JPanel();
    JPanel q2 = new JPanel();
    JPanel q3 = new JPanel();
    JPanel q4  = new JPanel();
    JTextField tf = new JTextField(10);
    final TitlePanel tp = new TitlePanel();
    StoryPanel sp;
    InstructionPanel ip;
    GamePanel gp;
    ScorePanel scp;

    public static void main(String[] args){
        Spaceratops p = new Spaceratops();
    }

    public Spaceratops(){
        Spaceship.loadImages();
        Cell.loadImages();
        Enemy.loadImages();
        Creature.loadImages();
        Powerup.loadImages();
        Laser.loadImages();
        GamePanel.loadAudio();

        //load images
        song = tp.loadAudio();
        gp.loadAudio();

        song.play();

        final Spaceratops t = this;

        setFocusable(true);
        //requestFocus();
        setSize(1000, 750);
        setLocation(220,70);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        

        // Add opening panel
        p.setOpaque(false);
        q1.setOpaque(false);
        q2.setOpaque(false);
        tp.add(p,BorderLayout.SOUTH);
        p.add(q1,BorderLayout.SOUTH);
        p.add(start, BorderLayout.SOUTH);
        p.add(q2,BorderLayout.SOUTH);
        add(tp);

        setVisible(true);

        // When button is clicked, set contClicked true
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sp = new StoryPanel(song);
                remove(tp);
                p.remove(start);
                q3.setOpaque(false);
                p.setOpaque(true);
                p.setBackground(Color.BLACK);
                sp.add(p,BorderLayout.SOUTH);
                p.add(q1,BorderLayout.SOUTH);
                p.add(cont_story, BorderLayout.SOUTH);
                p.add(q2,BorderLayout.SOUTH);
                p.add(howto, BorderLayout.SOUTH);
                p.add(q3,BorderLayout.SOUTH);
                add(sp);
                validate();
            }
        });

        // When button is clicked, set contClicked true
        cont_story.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //dispose();
                song.stop();
                remove(sp);
                gp = new GamePanel(t);
                add(gp);
                validate();
                gp.requestFocus();
                //pf.passFrame();
            }
        });

        howto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ip = new InstructionPanel();
                remove(sp);
                p.remove(q1);
                p.remove(cont_story);
                p.remove(q2);
                p.remove(howto);
                p.remove(q3);
                repaint();
                p.add(q1,BorderLayout.SOUTH);
                p.add(cont_story,BorderLayout.SOUTH);
                p.add(q2,BorderLayout.SOUTH);
                ip.add(p,BorderLayout.SOUTH);
                add(ip);
                validate();
            }
        });

        scoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.gc();
                scp = new ScorePanel(tf.getText(), Enemy.getKillsHi() + "");
                remove(gp);
                add(scp);
                validate();
            }
        });

        entername.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.gc();
                scp = new ScorePanel(tf.getText(), Enemy.getKillsHi() + "");
                remove(gp);
                add(scp);
                validate();
            }
        });
    }

    public JPanel showOptions(){
        p.remove(cont_story);
        p.remove(cont_howto);
        p.remove(howto);
        p.add(q1,BorderLayout.SOUTH);
        p.add(scoreButton,BorderLayout.SOUTH);
        p.add(q3,BorderLayout.SOUTH);
        p.add(tf,BorderLayout.SOUTH);
        p.add(q2,BorderLayout.SOUTH);
        p.add(entername,BorderLayout.SOUTH);
        p.add(q4,BorderLayout.SOUTH);
        p.setOpaque(false);
        q1.setOpaque(false);
        q3.setOpaque(false);
        q2.setOpaque(false);
        q4.setOpaque(false);
        return p;
    }
}

class TitlePanel extends JPanel {

        Image image, bgImage;
        ImageIcon imageIcon, bgImageIcon;
        URL path, bgPath;
	int iterator = 1, counter = -1;
        URL audioPath;
        AudioClip song;
        
        public AudioClip loadAudio(){
            //load audio files
            audioPath = getClass().getResource("audio/Title.aif");
            if(audioPath != null){
                song = Applet.newAudioClip(audioPath);
            }
            return song;
        }

	TitlePanel() {
            setLayout(new BorderLayout());

            bgPath = getClass().getResource("images/background_9large.png");
            if (bgPath != null) {
                bgImageIcon = new ImageIcon(bgPath);
                bgImage = bgImageIcon.getImage();
            }

            loadAudio();

             Timer timer = new Timer(100, new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    path = getClass().getResource("images/title_" + iterator + ".png");
                    if (path != null) {
                        imageIcon = new ImageIcon(path);
                        image = imageIcon.getImage();
                    }

                    counter++;

                    //if(counter == 1) song.play();

                    if(iterator < 6)
                        iterator++;
                    else
                        iterator = 1;
                    repaint();
                }
            });

            timer.start();
	}
	
	protected void paintComponent(Graphics g) {
            g.drawImage(bgImage, 0, 0, this);
            g.drawImage(image, 0, 0, this);
	}
}

class InstructionPanel extends JPanel {

        Image bgImage;
        ImageIcon bgImageIcon;
        URL bgPath;
        boolean done = false;

	InstructionPanel() {
            setLayout(new BorderLayout());
            bgPath = getClass().getResource("images/howtoplay.png");
            if (bgPath != null) {
                bgImageIcon = new ImageIcon(bgPath);
                bgImage = bgImageIcon.getImage();
            }

            Timer timer = new Timer(60, new ActionListener(){
                public void actionPerformed(ActionEvent e){
                   //maybe have timer actions here
                }
            });

            timer.start();

	}

	protected void paintComponent(Graphics g) {
		g.drawImage(bgImage, 0, 0, this);
        }
}