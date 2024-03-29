import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class MyWindow extends JFrame
{
    public MyWindow()
    {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(480,480);
        setLocation(400,300);
        add(new Game());
        setVisible(true);
    }
    public static void main(String[] args)
    {
        MyWindow myform = new MyWindow();
    }
}
class Game extends JPanel implements ActionListener
{
    private String str = "Score: ";
    private Image point;
    private Image apple;
    private int appleX;
    private int appleY;
    private final int SIZE = 480;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 200;
    private int SCORE = 0;
    private int time = 180;
    private int points;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    public Game()
    {
        setBackground(Color.BLACK);
        LoadImages();
        Begin();
        addKeyListener(new KeyListener());
        setFocusable(true);
    }
    public void Begin()
    {
        points = 3;
        for(int i = 0; i < points; i++)
        {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(time,this);
        timer.start();
        CreateApple();
    }
    public void CreateApple()
    {
        appleX = new Random().nextInt(27) * DOT_SIZE;
        appleY = new Random().nextInt(27) * DOT_SIZE;
    }
    public void LoadImages()
    {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        point = iid.getImage();
    }
    public void Go()
    {
        for(int i = points; i > 0; i--)
        {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if(left)
        {
            x[0] = x[0] - DOT_SIZE;
        }
        if(right)
        {
            x[0] = x[0] + DOT_SIZE;
        }
        if(up)
        {
            y[0] = y[0] - DOT_SIZE;
        }
        if(down)
        {
            y[0] = y[0] + DOT_SIZE;
        }
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(inGame)
        {
            g.drawImage(apple,appleX,appleY,this);
            for(int i = 0; i < points; i++)
            {
                g.drawImage(point,x[i],y[i],this);
            }
            Font f = new Font("Arial",Font.BOLD + Font.ITALIC, 20);
            g.setFont(f);
            g.setColor(Color.green);
            g.drawString(str + SCORE,360,430);
        }
        else
        {
            String str2 = "Game Over";
            String str3 = "Press 'esc' to restart the game";
            Font f = new Font("Arial",Font.BOLD + Font.ITALIC, 36);
            Font fа = new Font("Arial",Font.BOLD + Font.ITALIC, 24);
            g.setFont(f);
            g.setColor(Color.white);
            g.drawString(str2,140,220);
            g.setFont(fа);
            g.drawString(str3,55,260);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(inGame)
        {
            CheckApple();
            CheckBorder();
            Go();
        }
        repaint();
    }
    public void CheckApple()
    {
        if(x[0] == appleX && y[0] == appleY)
        {
            points++;
            SCORE = SCORE + 10;
            if(time <= 200 && time >= 60)
            {
                time = time - 10;
                timer.setDelay(time);
            }
            CreateApple();
        }
    }
    public void CheckBorder()
    {
        for(int i = points; i > 0; i--)
        {
            if(i > 4 && x[0] == x[i] && y[0] == y[i])
            {
                inGame = false;
            }
        }
        if(x[0] > SIZE)
        {
            inGame = false;
        }
        if(x[0] < 0)
        {
            inGame = false;
        }
        if(y[0] > SIZE)
        {
            inGame = false;
        }
        if(y[0] < 0)
        {
            inGame = false;
        }
    }
    class KeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right)
            {
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left)
            {
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down)
            {
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up)
            {
                right = false;
                down = true;
                left = false;
            }
            if(key == KeyEvent.VK_PAUSE)
            {
                timer.stop();
            }
            if(key == KeyEvent.VK_ENTER)
            {
                timer.restart();
            }
            if(key == KeyEvent.VK_ESCAPE)
            {
                inGame = true;
                MyWindow myform = new MyWindow();
            }
        }
    }
}
