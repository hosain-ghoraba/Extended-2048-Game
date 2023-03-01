package hahaaa;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements KeyListener
{
    Game game;
    JLabel[][] labels;
    
    public GUI(final int maxWidth, final int maxHeight) {
        this.game = new Game(maxHeight, maxWidth);
        this.labels = new JLabel[maxHeight][maxWidth];
        this.addKeyListener(this);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.setTitle("2048 game");
        this.setSize(700, 525);
        this.construct();
        this.revalidate();
        this.repaint();
    }
    
    public void construct() {
        final JPanel NorthPanel = new JPanel();
        NorthPanel.setBackground(Color.blue);
        final JPanel SouthPanel = new JPanel();
        SouthPanel.setBackground(Color.blue);
        final JPanel EastPanel = new JPanel();
        EastPanel.setBackground(Color.blue);
        final JPanel WestPanel = new JPanel();
        WestPanel.setBackground(Color.blue);
        final JPanel CenterPanel = new JPanel(new GridLayout(this.game.maxHeight, this.game.maxWidth));
        for (int i = 0; i < this.game.maxHeight; ++i) {
            for (int j = 0; j < this.game.maxWidth; ++j) {
                final JLabel label = new JLabel("", 0);
                label.setFont(new Font("Dialog", 1, 400 / (this.game.maxHeight + this.game.maxWidth)));
                final Border border = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.cyan);
                label.setBorder(border);
                label.setOpaque(true);
                CenterPanel.add(this.labels[i][j] = label);
            }
        }
        this.add(NorthPanel, "North");
        this.add(SouthPanel, "South");
        this.add(EastPanel, "East");
        this.add(WestPanel, "West");
        this.add(CenterPanel, "Center");
        this.updateText();
    }
    
    public void updateText() {
        final Color COLOR_EMPTY = new Color(204, 192, 179);
        final Color[] colors = { new Color(238, 228, 218), new Color(237, 224, 200), new Color(242, 177, 121), new Color(245, 149, 99), new Color(246, 124, 95), new Color(246, 94, 59), new Color(237, 207, 114), new Color(237, 204, 97), new Color(237, 200, 80), new Color(237, 197, 63), new Color(237, 194, 46) };
        for (int i = 0; i < this.game.maxHeight; ++i) {
            for (int j = 0; j < this.game.maxWidth; ++j) {
                final JLabel label = this.labels[i][j];
                final int value = this.game.board[i][j];
                String text = "";
                Color foreground = COLOR_EMPTY;
                Color background = COLOR_EMPTY;
                if (value != 0) {
                    text = new StringBuilder(String.valueOf(value)).toString();
                    foreground = ((value <= 4) ? Color.black : Color.white);
                    final int index = (int)(Math.log(value) / Math.log(2.0)) - 1;
                    background = ((value <= 2048) ? colors[index] : Color.black);
                }
                label.setText(text);
                label.setForeground(foreground);
                label.setBackground(background);
            }
        }
        this.revalidate();
        this.repaint();
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37: {
                this.game.moveHorizontal(Direction.LEFT);
                break;
            }
            case 38: {
                this.game.moveVertical(Direction.UP);
                break;
            }
            case 39: {
                this.game.moveHorizontal(Direction.RIGHT);
                break;
            }
            case 40: {
                this.game.moveVertical(Direction.DOWN);
                break;
            }
        }
        if (!this.game.noChange) {
            this.game.putNumber();
            this.updateText();
            if (this.game.GameOver()) {
                System.out.println(this.game.emptyCells.size());
                final String[] options = { "Play Again", "Exit " };
                final int response = JOptionPane.showOptionDialog(null, "                           GameOver !", "", -1, -1, null, options, options[0]);
                if (response == 0) {
                    this.dispose();
                    new Welcome();
                }
                else if (response == 1) {
                    this.dispose();
                }
            }
        }
        if (this.game.hit_2048 && !this.game.hit_2048_Once) {
            final String[] options = { "Continue", "Exit " };
            final int response = JOptionPane.showOptionDialog(null, "Congratulation! you hit 2048 ! what would you like to do ? ", "", -1, -1, null, options, options[0]);
            if (response == 1) {
                this.dispose();
            }
            this.game.hit_2048_Once = true;
        }
    }
    
    public static void main(final String[] args) {
        new Welcome();
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
    }
}
