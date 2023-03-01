package hahaaa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Welcome extends JFrame implements ActionListener
{
    JComboBox widths;
    JComboBox heights;
    JButton start;
    int choosedWidth;
    int choosedheight;
    
    public Welcome() {
        this.choosedWidth = 0;
        this.choosedheight = 0;
        this.setDefaultCloseOperation(3);
        this.setTitle(" Welcome to 2048 Game !");
        this.setSize(500, 400);
        this.setResizable(false);
        this.setLayout(null);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.green);
        this.construct();
        this.revalidate();
        this.repaint();
    }
    
    public void construct() {
        final String[] width = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" };
        final String[] height = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" };
        final JLabel widthLabel = new JLabel("Select board width", 0);
        widthLabel.setFont(new Font("Dialog", 1, 13));
        widthLabel.setBackground(Color.cyan);
        widthLabel.setOpaque(true);
        widthLabel.setBounds(100, 20, 130, 80);
        widthLabel.setVerticalTextPosition(1);
        widthLabel.setHorizontalTextPosition(0);
        final JLabel heightLabel = new JLabel("Select board height", 0);
        heightLabel.setFont(new Font("Dialog", 1, 13));
        heightLabel.setBackground(Color.cyan);
        heightLabel.setOpaque(true);
        heightLabel.setBounds(250, 20, 130, 80);
        heightLabel.setVerticalTextPosition(1);
        heightLabel.setHorizontalTextPosition(0);
        
        this.widths = new JComboBox<>(width);
        this.widths.setSelectedIndex(2);
        this.widths.setFont(new Font("Times New Roman", 1, 20));
        this.widths.setBounds(100, 100, 130, 50);
        
        this.heights = new JComboBox<>(height);
        this.heights.setSelectedIndex(2);
        this.heights.setFont(new Font("Times New Roman", 1, 20));
        this.heights.setBounds(250, 100, 130, 50);
        
        (this.start = new JButton("Play !")).addActionListener(this);
        this.start.setBounds(140, 250, 200, 70);
        this.start.setFont(new Font("Times New Roman", 1, 30));
        this.start.setForeground(Color.white);
        this.start.setBackground(Color.blue);
        this.add(widthLabel);
        this.add(heightLabel);
        this.add(this.widths);
        this.add(this.heights);
        this.add(this.start);
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        this.choosedWidth = Integer.parseInt((String)this.widths.getSelectedItem());
        this.choosedheight = Integer.parseInt((String)this.heights.getSelectedItem());
        if (e.getSource() instanceof JButton) {
            this.dispose();
            new GUI(this.choosedWidth, this.choosedheight);
        }
    }
    
    public static void main(final String[] args) {
        new Welcome();
    }
}
