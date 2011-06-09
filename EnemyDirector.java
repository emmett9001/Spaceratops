/******************************************************************************
"Spaceratops"
 * Written, directed, composed, programmed, animated, drawn, designed by Emmett Butler
 * Spring 2011
*******************************************************************************/
public class EnemyDirector {

    double[][] definition;

    /*definition array:
    [x][0] = xSpeed
    [x][1] = ySpeed
    [x][2] = xDir
    [x][3] = yDir
    [x][4] = angle
    [x][5] = time elapsed
    if [x][0] == 2, boundary detection
    will be turned off after the specified time
    */

    EnemyDirector(){
        definition = new double[90][6];
    }

    public double[][] hoverThenLeaveLeft(){
        definition[0][0] = 0;
        definition[0][1] = 1;
        definition[0][2] = 0;
        definition[0][3] = 1;
        definition[0][4] = (Math.PI);
        definition[0][5] = 1;
        subMovement(hover(10, 3), 1);
        subMovement(hover(10+60, 3), 5);
        subMovement(hover(10+120, 3), 9);
        subMovement(hover(10+180, 3), 13);
        subMovement(hover(10+240, 3), 17);
        subMovement(hover(10+300, 3), 21);
        subMovement(hover(10+360, 3), 25);
        subMovement(hover(10+420, 3), 29);
        subMovement(hover(10+480, 3), 33);
        subMovement(hover(10+540, 3), 37);
        subMovement(hover(10+600, 3), 41);
        subMovement(hover(10+660, 3), 45);
        subMovement(hover(10+720, 3), 49);
        subMovement(hover(10+780, 3), 53);
        subMovement(hover(10+840, 3), 57);
        subMovement(hover(10+900, 3), 61);
        subMovement(hover(10+960, 3), 65);
        subMovement(hover(10+1020, 3), 69);
        subMovement(hover(10+1080, 3), 73);
        subMovement(wideTurn(-1, 1, 1150, 3), 77);
        definition[81][0] = 2;
        definition[81][5] = 1158;

        return definition;
    }

    public double[][] hoverThenLeaveRight(){
        definition[0][0] = 0;
        definition[0][1] = 1;
        definition[0][2] = 0;
        definition[0][3] = 1;
        definition[0][4] = (Math.PI);
        definition[0][5] = 1;
        subMovement(hover(10, 3), 1);
        subMovement(hover(10+60, 3), 5);
        subMovement(hover(10+120, 3), 9);
        subMovement(hover(10+180, 3), 13);
        subMovement(hover(10+240, 3), 17);
        subMovement(hover(10+300, 3), 21);
        subMovement(hover(10+360, 3), 25);
        subMovement(hover(10+420, 3), 29);
        subMovement(hover(10+480, 3), 33);
        subMovement(hover(10+540, 3), 37);
        subMovement(hover(10+600, 3), 41);
        subMovement(hover(10+660, 3), 45);
        subMovement(hover(10+720, 3), 49);
        subMovement(hover(10+780, 3), 53);
        subMovement(hover(10+840, 3), 57);
        subMovement(hover(10+900, 3), 61);
        subMovement(hover(10+960, 3), 65);
        subMovement(hover(10+1020, 3), 69);
        subMovement(hover(10+1080, 3), 73);
        subMovement(wideTurn(1, 1, 1150, 3), 77);
        definition[81][0] = 2;
        definition[81][5] = 1158;

        return definition;
    }

    private double[][] hover(double timeInterval, int lengthTicks){
        double[][] hover = new double[4][6];
        int speed = 15;

        hover[0][0] = 1;
        hover[0][1] = 0;
        hover[0][2] = -1;
        hover[0][3] = 0;
        hover[0][4] = Math.PI;
        hover[0][5] = timeInterval;
        hover[1][0] = 1;
        hover[1][1] = 0;
        hover[1][2] = 1;
        hover[1][3] = 0;
        hover[1][4] = Math.PI;
        hover[1][5] = timeInterval + speed;
        hover[2][0] = 1;
        hover[2][1] = 0;
        hover[2][2] = -1;
        hover[2][3] = 0;
        hover[2][4] = Math.PI;
        hover[2][5] = timeInterval + (speed*2);
        hover[3][0] = 1;
        hover[3][1] = 0;
        hover[3][2] = 1;
        hover[3][3] = 0;
        hover[3][4] = Math.PI;
        hover[3][5] = timeInterval + (speed*3);

        return hover;
    }

    private double[][] wideTurn(int hDir, int vDir, double timeInterval, int lengthTicks){
        double[][] turn = new double[8][6];
        int speed = 2;

        for(int i = 0; i <= lengthTicks; i++){
            if(vDir == 1){
                if(hDir == -1){
                    if(i == 0){
                        turn[0][0] = Math.sin((7*Math.PI)/6);
                        turn[0][1] = Math.cos((7*Math.PI)/6);;
                        turn[0][2] = 1;
                        turn[0][3] = -1;
                        turn[0][4] = (7*Math.PI)/6;
                        turn[0][5] = timeInterval;
                    }
                    else if(i == 1){
                        turn[1][0] = Math.sin((5*Math.PI)/4);
                        turn[1][1] = Math.cos((5*Math.PI)/4);;
                        turn[1][2] = 1;
                        turn[1][3] = -1;
                        turn[1][4] = (5*Math.PI)/4;
                        turn[1][5] = timeInterval + speed;
                    }
                    else if(i == 2){
                        turn[2][0] = Math.sin((4*Math.PI)/3);
                        turn[2][1] = Math.cos((4*Math.PI)/3);;
                        turn[2][2] = 1;
                        turn[2][3] = -1;
                        turn[2][4] = (4*Math.PI)/3;
                        turn[2][5] = timeInterval + (speed*2);
                    }
                    else if(i == 3){
                        turn[3][0] = 1;
                        turn[3][1] = 0;
                        turn[3][2] = -1;
                        turn[3][3] = 0;
                        turn[3][4] = (3*Math.PI)/2;
                        turn[3][5] = timeInterval + (speed*3);
                    }
                    else if(i == 4){
                        turn[4][0] = Math.sin((5*Math.PI)/3);
                        turn[4][1] = Math.cos((5*Math.PI)/3);;
                        turn[4][2] = 1;
                        turn[4][3] = -1;
                        turn[4][4] = (5*Math.PI)/3;
                        turn[4][5] = timeInterval + (speed*4);
                    }
                    else if(i == 5){
                        turn[5][0] = Math.sin((7*Math.PI)/4);
                        turn[5][1] = Math.cos((7*Math.PI)/4);;
                        turn[5][2] = 1;
                        turn[5][3] = -1;
                        turn[5][4] = (7*Math.PI)/4;
                        turn[5][5] = timeInterval + (speed*5);
                    }
                    else if(i == 6){
                        turn[6][0] = Math.sin((11*Math.PI)/6);
                        turn[6][1] = Math.cos((11*Math.PI)/6);;
                        turn[6][2] = 1;
                        turn[6][3] = -1;
                        turn[6][4] = (11*Math.PI)/6;
                        turn[6][5] = timeInterval + (speed*6);
                    }
                    else if(i == 7){
                        turn[7][0] = 0;
                        turn[7][1] = 1;
                        turn[7][2] = 0;
                        turn[7][3] = -1;
                        turn[7][4] = 0;
                        turn[7][5] = timeInterval + (speed*7);
                    }
                }
                else if(hDir == 1){
                    if(i == 0){
                        turn[0][0] = Math.sin((5*Math.PI)/6);
                        turn[0][1] = Math.cos((5*Math.PI)/6);;
                        turn[0][2] = 1;
                        turn[0][3] = -1;
                        turn[0][4] = (5*Math.PI)/6;
                        turn[0][5] = timeInterval;
                    }
                    else if(i == 1){
                        turn[1][0] = Math.sin((3*Math.PI)/4);
                        turn[1][1] = Math.cos((3*Math.PI)/4);;
                        turn[1][2] = 1;
                        turn[1][3] = -1;
                        turn[1][4] = (3*Math.PI)/4;
                        turn[1][5] = timeInterval + speed;
                    }
                    else if(i == 2){
                        turn[2][0] = Math.sin((2*Math.PI)/3);
                        turn[2][1] = Math.cos((2*Math.PI)/3);;
                        turn[2][2] = 1;
                        turn[2][3] = -1;
                        turn[2][4] = (2*Math.PI)/3;
                        turn[2][5] = timeInterval + (speed*2);
                    }
                    else if(i == 3){
                        turn[3][0] = 1;
                        turn[3][1] = 0;
                        turn[3][2] = 1;
                        turn[3][3] = 0;
                        turn[3][4] = (Math.PI)/2;
                        turn[3][5] = timeInterval + (speed*3);
                    }
                    else if(i == 4){
                        turn[4][0] = Math.sin((Math.PI)/3);
                        turn[4][1] = Math.cos((Math.PI)/3);;
                        turn[4][2] = 1;
                        turn[4][3] = -1;
                        turn[4][4] = (Math.PI)/3;
                        turn[4][5] = timeInterval + (speed*4);
                    }
                    else if(i == 5){
                        turn[5][0] = Math.sin((Math.PI)/4);
                        turn[5][1] = Math.cos((Math.PI)/4);;
                        turn[5][2] = 1;
                        turn[5][3] = -1;
                        turn[5][4] = (Math.PI)/4;
                        turn[5][5] = timeInterval + (speed*5);
                    }
                    else if(i == 6){
                        turn[6][0] = Math.sin((Math.PI)/6);
                        turn[6][1] = Math.cos((Math.PI)/6);;
                        turn[6][2] = 1;
                        turn[6][3] = -1;
                        turn[6][4] = (Math.PI)/6;
                        turn[6][5] = timeInterval + (speed*6);
                    }
                    else if(i == 7){
                        turn[7][0] = 0;
                        turn[7][1] = 1;
                        turn[7][2] = 0;
                        turn[7][3] = -1;
                        turn[7][4] = 0;
                        turn[7][5] = timeInterval + (speed*7);
                    }
                }
            }
            else{
                if(hDir == -1){
                    if(i == 0){
                        turn[0][0] = Math.sin((11*Math.PI)/6);
                        turn[0][1] = Math.cos((11*Math.PI)/6);;
                        turn[0][2] = 1;
                        turn[0][3] = -1;
                        turn[0][4] = (11*Math.PI)/6;
                        turn[0][5] = timeInterval;
                    }
                    else if(i == 1){
                        turn[1][0] = Math.sin((7*Math.PI)/4);
                        turn[1][1] = Math.cos((7*Math.PI)/4);;
                        turn[1][2] = 1;
                        turn[1][3] = -1;
                        turn[1][4] = (7*Math.PI)/4;
                        turn[1][5] = timeInterval + (speed);
                    }
                    else if(i == 2){
                        turn[2][0] = Math.sin((5*Math.PI)/3);
                        turn[2][1] = Math.cos((5*Math.PI)/3);;
                        turn[2][2] = 1;
                        turn[2][3] = -1;
                        turn[2][4] = (5*Math.PI)/3;
                        turn[2][5] = timeInterval + (speed*2);
                    }
                    else if(i == 3){
                        turn[3][0] = 1;
                        turn[3][1] = 0;
                        turn[3][2] = -1;
                        turn[3][3] = 0;
                        turn[3][4] = (3*Math.PI)/2;
                        turn[3][5] = timeInterval + (speed*3);
                    }
                    else if(i == 4){
                        turn[4][0] = Math.sin((4*Math.PI)/3);
                        turn[4][1] = Math.cos((4*Math.PI)/3);;
                        turn[4][2] = 1;
                        turn[4][3] = -1;
                        turn[4][4] = (4*Math.PI)/3;
                        turn[4][5] = timeInterval + (speed*4);
                    }
                    else if(i == 5){
                        turn[5][0] = Math.sin((5*Math.PI)/4);
                        turn[5][1] = Math.cos((5*Math.PI)/4);;
                        turn[5][2] = 1;
                        turn[5][3] = -1;
                        turn[5][4] = (5*Math.PI)/4;
                        turn[5][5] = timeInterval + (speed*5);
                    }
                    else if(i == 6){
                        turn[6][0] = Math.sin((7*Math.PI)/6);
                        turn[6][1] = Math.cos((7*Math.PI)/6);;
                        turn[6][2] = 1;
                        turn[6][3] = -1;
                        turn[6][4] = (7*Math.PI)/6;
                        turn[6][5] = timeInterval + (speed*6);
                    }
                    else if(i == 7){
                        turn[7][0] = 0;
                        turn[7][1] = 1;
                        turn[7][2] = 0;
                        turn[7][3] = 1;
                        turn[7][4] = Math.PI;
                        turn[7][5] = timeInterval + (speed*7);
                    }
                }
                else if(hDir == 1){
                    if(i == 0){
                        turn[0][0] = Math.sin((Math.PI)/6);
                        turn[0][1] = Math.cos((Math.PI)/6);;
                        turn[0][2] = 1;
                        turn[0][3] = -1;
                        turn[0][4] = (Math.PI)/6;
                        turn[0][5] = timeInterval;
                    }
                    else if(i == 1){
                        turn[1][0] = Math.sin((Math.PI)/4);
                        turn[1][1] = Math.cos((Math.PI)/4);;
                        turn[1][2] = 1;
                        turn[1][3] = -1;
                        turn[1][4] = (Math.PI)/4;
                        turn[1][5] = timeInterval + speed;
                    }
                    else if(i == 2){
                        turn[2][0] = Math.sin((Math.PI)/3);
                        turn[2][1] = Math.cos((Math.PI)/3);;
                        turn[2][2] = 1;
                        turn[2][3] = -1;
                        turn[2][4] = (Math.PI)/3;
                        turn[2][5] = timeInterval + (speed*2);
                    }
                    else if(i == 3){
                        turn[3][0] = 1;
                        turn[3][1] = 0;
                        turn[3][2] = 1;
                        turn[3][3] = 0;
                        turn[3][4] = (Math.PI)/2;
                        turn[3][5] = timeInterval + (speed*3);
                    }
                    else if(i == 4){
                        turn[4][0] = Math.sin((2*Math.PI)/3);
                        turn[4][1] = Math.cos((2*Math.PI)/3);;
                        turn[4][2] = 1;
                        turn[4][3] = -1;
                        turn[4][4] = (2*Math.PI)/3;
                        turn[4][5] = timeInterval + (speed*4);
                    }
                    else if(i == 5){
                        turn[5][0] = Math.sin((3*Math.PI)/4);
                        turn[5][1] = Math.cos((3*Math.PI)/4);;
                        turn[5][2] = 1;
                        turn[5][3] = -1;
                        turn[5][4] = (3*Math.PI)/4;
                        turn[5][5] = timeInterval + (speed*5);
                    }
                    else if(i == 6){
                        turn[6][0] = Math.sin((5*Math.PI)/6);
                        turn[6][1] = Math.cos((5*Math.PI)/6);;
                        turn[6][2] = 1;
                        turn[6][3] = -1;
                        turn[6][4] = (5*Math.PI)/6;
                        turn[6][5] = timeInterval + (speed*6);
                    }
                    else if(i == 7){
                        turn[7][0] = 0;
                        turn[7][1] = 1;
                        turn[7][2] = 0;
                        turn[7][3] = 1;
                        turn[7][4] = Math.PI;
                        turn[7][5] = timeInterval + (speed*7);
                    }
                }
            }
        }

        return turn;
    }

    public void subMovement(double[][] subMove, int step){
        for(int i = 0; i < subMove.length; i++){
            for(int j = 0; j < 6; j ++){
                definition[step + i][j] = subMove[i][j];
            }
        }
    }
}