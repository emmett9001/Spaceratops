/******************************************************************************
"Spaceratops"
 * Written, directed, composed, programmed, animated, drawn, designed by Emmett Butler
 * Spring 2011
*******************************************************************************/

import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.util.Scanner;

import java.io.PrintWriter;
import java.io.File;

import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;

public class ScoreReader {

    String[][] list = new String[11][2];

    public ScoreReader(String Score, String username){
        try{
            File file;
            URI uri = null;
            URL textPath;

            int score = Integer.parseInt(Score);
            int i = 0;

            textPath = getClass().getResource("data/scores.txt");
            try{
                uri = textPath.toURI();
            }
            catch(URISyntaxException e){}
            if(uri != null){
                file = new File(uri);
            
                Scanner input = new Scanner(file);

                while(input.hasNext() && i < 10){

                    list[i][0] = input.next() + " ";

                    //handle inputs with spaces
                    if(!input.hasNextInt())
                        list[i][0] += input.next() + " ";

                    list[i][1] = input.next();

                    if(Integer.parseInt(list[i][1]) < score){
                        list[i + 1][0] = list[i][0];
                        list[i + 1][1] = list[i][1];

                        list[i][0] = username + " ";
                        list[i][1] = Score;

                        i++;

                        score = 0;
                    }

                    i++;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String[][] getScores(){
        return list;
    }
}

class ScoreWriter {
	
    public ScoreWriter(String Username, String Score) {
        try{
            ScoreReader reader = new ScoreReader(Score, Username);
            String[][] list = reader.getScores();
            File scores;
            URL textPath;
            URI uri = null;
            PrintWriter output;

            textPath = getClass().getResource("data/scores.txt");
            try{
                uri = textPath.toURI();
            }
            catch(URISyntaxException e){}
            if(uri != null){
                scores = new File(uri);
                output = new PrintWriter(scores);

                for(int i = 0; i < 10; i++){
                    output.print(list[i][0]);
                    output.println(list[i][1]);
                }
                output.close();
            }
        }
        catch(Exception e){
        }
    }
}

class ScorePanel extends JPanel {
	
    File textFile;
    URL textPath;
    URI uri;
    Scanner input;
    Font font = Level.getFont();
    JPanel p = new JPanel();
    JPanel q1 = new JPanel();
    JPanel q2 = new JPanel();
    JPanel q3 = new JPanel();
    int i = 0;
    String filePath = "data/scores.txt";
    ImageIcon imageIcon;
    Image image;
    URL bgURL;

    public ScorePanel(String username, String score) {
        ScoreWriter sw = new ScoreWriter(username, score);

        bgURL = getClass().getResource("images/background_9large.png");
        if(bgURL != null){
            imageIcon = new ImageIcon(bgURL);
            image = imageIcon.getImage();
        }

        textPath = getClass().getResource(filePath);
        try{
            uri = textPath.toURI();
        }
        catch(URISyntaxException e){}
        if(uri != null){
            textFile = new File(uri);
        }
            try{
                input = new Scanner(textFile);
            }
            catch(Exception e){
            }
    }

    public void paintComponent(Graphics g){
        g.drawImage(image, 0, 0, null);
        g.setColor(Color.yellow);
        g.setFont(font.deriveFont((float)45));
        g.drawString("High Scores", 307, 60);
        while(input.hasNext()){
            g.setFont(font.deriveFont((float)35));
            g.drawString(input.nextLine() + "\n", 20, 120 + i);
            i += 60;
        }
    }
}