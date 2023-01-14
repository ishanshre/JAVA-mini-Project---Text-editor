import java.awt.*;
import javax.swing.*;

public class About extends JFrame{
    About() {
        setBounds(100,100,500,400);
        setTitle("About Ishan's Text Editor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        JLabel textLabel = new JLabel("<html><h1>Welcome to Ishan's Text Editor</h1></html>");
        textLabel.setBounds(100,50,400,300);
        add(textLabel);
    }
    public static void main(String[] args){
        new About().setVisible(true);
    }
}
