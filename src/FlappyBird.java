import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int platos=360;
    int ipsos=640;
    Image birdimg,background,toppipe,bottompipe;
    //pouli
    int birdX=platos/8;
    int birdY=ipsos/2;
    int birdwidth=34;
    int birdheight=24;
    //εμπόδια
    int pipeX=platos;
    int pipeY=0;
    int pipeWidth=64;
    int pipeHeight=512;
    //λογική παιχνιδιού
    Bird bird;
    int velocityX=-4; //τα εμποδια ερχονται προς τα αριστερα (δηλ. το πουλι παει προς τα δεξια)
    int velocityY=0; // το πουλι παει πανω η κατω
    int gravity=1;
    ArrayList<Pipe> pipes;
    Random random=new Random();
    Timer gameloop;
    Timer placepipestimer;
    boolean gameover=false;
    double score=0;
    public FlappyBird(){
        setPreferredSize(new Dimension(platos,ipsos));
        setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        //εικόνες
        birdimg=new ImageIcon(App.class.getResource("./flappybird.png")).getImage();
        background=new ImageIcon(App.class.getResource("./flappybirdbg.png")).getImage();
        toppipe=new ImageIcon(App.class.getResource("./toppipe.png")).getImage();
        bottompipe=new ImageIcon(App.class.getResource("./bottompipe.png")).getImage();
        //pouli
        bird=new Bird(birdimg);
        //empodia
        pipes=new ArrayList<>();
        placepipestimer=new Timer(1500, e -> PlacePipes());
        placepipestimer.start();
        //paixnidi
        gameloop=new Timer(1000/60,this);
        gameloop.start();


    }
    public void PlacePipes(){
        int randompipeY= (int) (pipeY-pipeHeight/4- Math.random()*(pipeHeight/2));
        int anoigma=ipsos/4;
        Pipe topPipe=new Pipe(toppipe);
        topPipe.y=randompipeY;
        pipes.add(topPipe);

        Pipe bottomPipe=new Pipe(bottompipe);
        bottomPipe.y=topPipe.y+anoigma+pipeHeight;
        pipes.add(bottomPipe);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(background,0,0,platos,ipsos,null);
        g.drawImage(bird.eikona,bird.x,bird.y,bird.birdwidth,bird.birdheight,null);
        for (Pipe pipe:pipes){
            g.drawImage(pipe.eikona,pipe.x,pipe.y,pipe.width,pipe.height,null);
        }
        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if (gameover){
            g.drawString("Έχασες: " + String.valueOf((int) score),10,35);
        }
        else
            g.drawString(String.valueOf((int) score),10,35);
    }
    public void move(){
        //pouli
        velocityY+=gravity;
        bird.y+=velocityY;
        bird.y=Math.max(bird.y,0);
        //empodia
        for (Pipe pipe:pipes){
            pipe.x+=velocityX;
            if(!pipe.passed && bird.x>pipe.x+pipe.width){
                pipe.passed=true;
                score+=0.5; //gia kathe miso toy empodioy
            }
            if (collision(bird,pipe)){
                gameover=true;
            }
        }
        if (bird.y>ipsos){
            gameover=true;
        }
    }
    public boolean collision(Bird a,Pipe b){
        return a.x<b.x + b.width &&
                a.x + a.birdwidth>b.x &&
                a.y<b.y+b.height &&
                a.y+a.birdheight>b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameover){
            placepipestimer.stop();
            gameloop.stop();
        }
    }




    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY=-9;
            if (gameover){
                bird.y=ipsos/2;
                velocityY=0;
                pipes.clear();
                score=0;
                gameover=false;
                gameloop.start();
                placepipestimer.start();
            }
        }

    }
    //μας ειναι αχρηστα αυτα τα δυο
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
