package cn.dante.t01huanchong;

import javax.swing.*;
import java.awt.*;

public class NoBufferMovingCircle extends JApplet implements Runnable {
    Image screenImage = null;
    Thread thread ;
    int x = 5;
    int move = 1;

    public void init(){
        screenImage  = createImage(230,160);
    }

    public void start(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }

    public void run() {
        try{
            while (true){
                x += move;
                if((x>105) || (x<5))
                    move *= -1;
                repaint();
                Thread.sleep(10);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void drawCircle(Graphics gc){
        Graphics2D g = (Graphics2D) gc;
        g.setColor(Color.GREEN);
        g.fillRect(0,0,200,100);
        g.setColor(Color.RED);
        g.fillOval(x,5,90,90);
    }

    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,200,100);
        drawCircle(g);
    }

}
