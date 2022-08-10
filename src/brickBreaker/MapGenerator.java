package brickBreaker;

import java.awt.*;

public class MapGenerator {
    public int brick_map[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row,int col){
        brick_map=new int[row][col];
        for (int i = 0; i <brick_map.length ; i++) {
            for (int j = 0; j <brick_map[0].length ; j++) {
                brick_map[i][j]=1;
            }
        }
        brickWidth=540/col;
        brickHeight=150/row;
    }

    public void draw(Graphics2D g){
        for (int i = 0; i <brick_map.length ; i++) {
            for (int j = 0; j <brick_map[0].length ; j++) {
                if (brick_map[i][j]>0){
                    g.setColor(Color.white);
                    g.fillRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);
                    //הפרדה בין ריבועים
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j*brickWidth+80,i*brickHeight+50,brickWidth,brickHeight);

                }
            }
        }
    }

    public void setBrickValue(int value , int row, int col){
        brick_map[row][col]=value;
    }
}
