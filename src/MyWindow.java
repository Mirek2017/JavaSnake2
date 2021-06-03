import javax.swing.*;

public class MyWindow extends JFrame
{
    public MyWindow()
    {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(480,480);
        setLocation(400,300);
        add(new GameField());
        setVisible(true);
    }
    public static void main(String[] args)
    {
        MyWindow mw = new MyWindow();
    }
}
