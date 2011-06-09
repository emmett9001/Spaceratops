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

import java.net.URL;

public class ProgressBar extends JPanel {
	
    BarFill fill;
    BarBackground bg;
    int increment;
    int goal;

    ProgressBar(int progress, int goal){
        fill = new BarFill();
        bg = new BarBackground();
        this.goal = goal;
    }

    public void setFill(int progress, int goal){
        if(progress < goal)
            fill.setFill((goal - (progress * 1.0)) / (goal * 1.0));
        else
            fill.setFill(1.0);
    }

    public void draw(Graphics g){
        g.setColor(Color.orange);
        g.drawRect(10, 24, 500, 8);
        fill.draw(g);
    }

    public void draw(Graphics g, int x, int y){
        g.setColor(Color.orange);
        g.drawRect(x, y, 500, 8);
        bg.draw(g, x, y);
        fill.draw(g, x, y);
    }
}

class BarFill extends JPanel {
	
    private double fillPct;

    BarFill(){
    }

    public void draw(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(10, 24, (int)(fillPct * 500), 8);
    }

    public void draw(Graphics g, int x, int y){
        g.setColor(Color.yellow);
        g.fillRect(x, y + 1, (int)(fillPct * 500), 8);
    }

    public void setFill(double fillPct){
        this.fillPct  = fillPct;
    }
}

class BarBackground extends JPanel {
	
    BarBackground(){
    }

    public void draw(Graphics g, int x, int y){
        g.setColor(Color.red);
        g.fillRect(x, y+1, 499, 7);
    }
}

class LifeIndicator extends JPanel {

	private int distance = 0, x, y;
	private Cell[] lives;

	LifeIndicator(int x, int y){
            lives = new Cell[11];
            for(int i = 1; i < lives.length; i++){
                lives[i] = new Cell(x, y + distance);
                distance += 64;
            }
            this.x = x;
            this.y = y;
	}

        public void updateLife(int number, boolean increasing){
            if(!increasing){
                lives[10].setImage(5);
                if(number == 0){
                    lives[1].setImage(6);
                }
                else if(number < 11 && number > 0){
                    if(number == 1){
                        lives[1].setImage(7);
                        lives[2].setImage(9);
                    }
                    else{
                        lives[number].setImage(1);
                        lives[1].setImage(3);
                        lives[number - 1].setImage(0);
                        if(number != 10){
                            lives[number + 1].setImage(9);
                        }
                        else{
                            lives[10].setImage(2);
                        }
                    }
                }
            }
            else {
                lives[10].setImage(5);
                if(number == 0){
                    lives[1].setImage(6);
                }
                else if(number < 11 && number > 0){
                    if(number == 1){
                        lives[1].setImage(6);
                        lives[2].setImage(4);
                    }
                    else{
                        if(number != 10){
                            lives[number + 1].setImage(9);
                        }
                        lives[1].setImage(3);
                        lives[number].setImage(1);
                        if(number < 9){
                            lives[number + 2].setImage(4);
                        }
                    }
                }
            }
            for(int i = 1; i < number; i++){
                if(number < 11){
                    if(i != 1)
                        lives[i].setImage(0);
                    else
                        lives[i].setImage(3);
                }
            }
            for(int i = 10; i > number; i--){
                if(number > 0){
                    if(i < 9)
                        lives[i + 1].setImage(4);
                    else
                        lives[i].setImage(5);
                }
            }
        }

	public void draw(Graphics g){
            for(int i = 1; i < lives.length; i++){
		lives[i].draw(g);
            }
	}
}

class Cell {

	private int x;
	private int y;
	private static ImageIcon imageIcon;
        private static URL path;
        private Image image;
        private static Image[] imageBank = new Image[10];

        public static void loadImages(){
            path = Cell.class.getResource("images/meter.png");
            if(path != null){
                imageIcon = new ImageIcon(path);
                imageBank[0] = imageIcon.getImage();
            }
            path = Cell.class.getResource("images/meterbottomfull.png");
            if(path != null){
                imageIcon = new ImageIcon(path);
                imageBank[1] = imageIcon.getImage();
            }
            path = Cell.class.getResource("images/meteremptybottom.png");
            if(path != null){
                imageIcon = new ImageIcon(path);
                imageBank[2] = imageIcon.getImage();
            }
            path = Cell.class.getResource("images/meteremptytop.png");
            if(path != null){
                imageIcon = new ImageIcon(path);
                imageBank[3] = imageIcon.getImage();
            }
            path = Cell.class.getResource("images/meterfull.png");
            if(path != null){
                imageIcon = new ImageIcon(path);
                imageBank[4] = imageIcon.getImage();
            }
            path = Cell.class.getResource("images/meterfullbottom.png");
            if(path != null){
                imageIcon = new ImageIcon(path);
                imageBank[5] = imageIcon.getImage();
            }
            path = Cell.class.getResource("images/meterfulltop.png");
            if(path != null){
                imageIcon = new ImageIcon(path);
                imageBank[6] = imageIcon.getImage();
            }
            path = Cell.class.getResource("images/meterhalfemptytop.png");
            if(path != null){
                imageIcon = new ImageIcon(path);
                imageBank[7] = imageIcon.getImage();
            }
            path = Cell.class.getResource("images/meterhalffullbottom.png");
            if(path != null){
                imageIcon = new ImageIcon(path);
                imageBank[8] = imageIcon.getImage();
            }
            path = Cell.class.getResource("images/metertopempty.png");
            if(path != null){
                imageIcon = new ImageIcon(path);
                imageBank[9] = imageIcon.getImage();
            }
        }

	Cell(int x, int y){
            image = imageBank[4];
            this.x = x;
            this.y = y;
	}

        public void setImage(int x){
            image = imageBank[x];
        }

	public void draw(Graphics g){
            g.drawImage(image, x, y, null);
	}

}
