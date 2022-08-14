package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements ActionListener, KeyListener {
    private  boolean start_game=false;
    private int score = 0;
    int level = 1;
    private int totalBricks=20;
    private  Timer timer;
    private int speed_delay=2;
    boolean levelpass=false;
    /**
     * player position
     */
    private int playerX=310;

    /**
     * ball position
     */
    private  int ballposX =120;
    private int ballposY=350;
    /**
     * ball Direction
     */
    private double ballXdir=-1;
    private  double ballYdir=-2;

    private MapGenerator gameMap;

    public Gameplay(){
        gameMap= new MapGenerator(5,4);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(speed_delay,this);
        timer.start();
    }

    public void paint(Graphics g){
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        //map draw
        gameMap.draw((Graphics2D) g);

        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //score
        g.setColor(Color.red);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString("Score: "+score,590,30);

        //level
        g.setColor(Color.red);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString("Level: "+level,10,30);

        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX,ballposY,20,20);

        if (totalBricks<=0){
            start_game=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Level Clear, Score: "+score,190,300);
            levelpass=true;
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Space to Continue",230,350);
        }

        if (ballposY> 570){
            start_game=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, Score: "+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press enter to Restart",230,350);
        }

        g.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (start_game){
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
            {
                ballYdir=-ballYdir;
            }
            A: for (int i = 0; i < gameMap.brick_map.length; i++) {
                for (int j = 0; j < gameMap.brick_map[0].length; j++) {
                    if (gameMap.brick_map[i][j]>0){
                        int brickX=j* gameMap.brickWidth+80;
                        int brickY=i* gameMap.brickHeight+50;
                        int brickWidth=gameMap.brickWidth;
                        int brickHighth=gameMap.brickHeight;

                        //Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHighth);
                        Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect=new Rectangle(brickX,brickY,brickWidth,brickHighth);//= rect
                        if (ballRect.intersects(brickRect)){
                            gameMap.setBrickValue(0,i,j);
                            totalBricks--;
                            score++;
                            /**
                             * change the ball direction when you hit a brick
                             */
                            if (ballposX+ 10<= brickRect.x || ballposX +1 >= brickRect.x + brickRect.width){
                                ballXdir+=0.8;
                                ballXdir= -ballXdir;
                            }else{
                                if(ballYdir>=0)
                                    ballYdir+=0.4;
                                else
                                    ballYdir-=0.4;
                                ballYdir= -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX+=ballXdir;
            ballposY+=ballYdir;
            if (ballposX<0){
                ballXdir=-ballXdir;
            }
            if (ballposY<0){
                ballYdir=-ballYdir;
            }
            if (ballposX>670){
                ballXdir=-ballXdir;
            }
            if (ballposY>670){
                ballYdir=-ballYdir;
            }
        }

        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else
                moveRight();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else
                moveLeft();
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!start_game){
                start_game=true;
                ballposX=120;
                ballposY=350;
                ballXdir=-1;
                ballYdir=-2;
                playerX=310;
                score=0;
                totalBricks=21;
                gameMap=new MapGenerator(3,7);
                repaint();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE&&levelpass){
            if (!start_game){
                levelpass=false;
                start_game=true;
                ballposX=120;
                ballposY=350;
                ballXdir=-1;
                ballYdir=-2;
                playerX=310;
                level++;
                switch (level){
                    case 1:
                        totalBricks=20;
                        gameMap= new MapGenerator(5,4);
                    case 2:
                        totalBricks=21;
                        gameMap= new MapGenerator(3,7);
                    case 3:
                        totalBricks=24;
                        gameMap= new MapGenerator(3,8);
                }
                repaint();
            }
        }

    }
    public void moveRight(){
        start_game=true;
        playerX+=20;
    }
    public void moveLeft(){
        start_game=true;
        playerX-=20;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
