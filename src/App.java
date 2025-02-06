import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class App
{
    public static void main(String[] args) {
        JFrame GameFrame=new JFrame("Flappy Bird");
        Image image = new ImageIcon(App.class.getResource("./flappybird.png")).getImage();
        GameFrame.setIconImage(image);
        GameFrame.setSize(360,640);
        GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GameFrame.setLocationRelativeTo(null);
        GameFrame.setResizable(false);


        FlappyBird flappyBird=new FlappyBird();
        GameFrame.add(flappyBird);
        GameFrame.pack();
        flappyBird.requestFocus();
        GameFrame.setVisible(true);

    }
}