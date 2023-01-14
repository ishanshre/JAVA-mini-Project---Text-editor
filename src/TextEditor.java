import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;



public class TextEditor extends JFrame implements ActionListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    JLabel fontLabel;
    JSpinner fontSizeSpinner;
    JButton fontColorButton;
    JComboBox fontBox;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu moreMenu;
    JMenuBar menuBar;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem printItem;
    JMenuItem exitItem;
    JMenuItem aboutItem;
    JMenuItem cutItem;
    JMenuItem copyItem;
    JMenuItem pasteItem;
    JMenuItem selectAllItem;
    TextEditor() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// close the program when hit the cross button in right top corner
        this.setTitle("Ishan Text Editor");// title in windows top bar
        this.setSize(1024,600);// size of the windows when opening
        this.setLayout(new FlowLayout());// layout of the window
        this.setLocationRelativeTo(null); 

        textArea = new JTextArea();
        //textArea.setPreferredSize(new Dimension(450,450));//add size of text area in window
        // wrapping the text so it won't go beyound the border
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        //chaning the font and its size
        textArea.setFont(new Font("Arial",Font.PLAIN, 22));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(getWidth(),getHeight()));
        // define a scroll bar and show it when needed
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //this.add(textArea);

        JLabel fontLabel = new JLabel("Font: ");

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50,25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
            }
        });

        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        // --- Menu Bar Area Begins
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        moreMenu = new JMenu("More");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        printItem = new JMenuItem("print");
        exitItem = new JMenuItem("Exit");
        aboutItem = new JMenuItem("About");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        printItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(printItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        editMenu = new JMenu("Edit");
        cutItem = new JMenuItem("Cut");
        copyItem = new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        selectAllItem = new JMenuItem("Select All");

        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);
        selectAllItem.addActionListener(this);

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(selectAllItem);
        menuBar.add(editMenu);

        moreMenu = new JMenu("More");
        aboutItem.addActionListener(this);
        moreMenu.add(aboutItem);
        menuBar.add(moreMenu);
        // --- Menu Bar ends

        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontSizeSpinner);
        this.add(fontColorButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        // button click or ant action event, this method will be invocked

        // listener for change font color
        if (e.getSource()==fontColorButton) {
            
            JColorChooser colorChooser = new JColorChooser();
   
            Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
   
            textArea.setForeground(color);
        }

        // action listener for font change
        if (e.getSource()==fontBox){
            textArea.setFont(new Font((String)fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
        }

        // action listener for open item
        if (e.getSource()==openItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()){
                        while (fileIn.hasNextLine()){
                            String line = fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException err) {
                    err.printStackTrace();
                } finally {
                    fileIn.close();
                }
                
                
            }
        }

        // action listener for open item
        if (e.getSource()==saveItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut = null;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut= new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } finally {
                    fileOut.close();
                }
            }
        }
        if (e.getSource()==aboutItem){
            new About().setVisible(true);;
        }
        if (e.getSource()==printItem){
            try {
                textArea.print();
            } catch (PrinterException e1) {
                e1.printStackTrace();
            }
        }
        // action listener for open item
        if (e.getSource()==exitItem){
            System.exit(0);
        }
        if (e.getSource()==cutItem){
            textArea.cut();
        }
        if (e.getSource()==copyItem){
            textArea.copy();
        }
        if (e.getSource()==pasteItem){
            textArea.paste();
        }
        if (e.getSource()==selectAllItem){
            textArea.selectAll();
        }
    };
    
    
}
