package view.Animal;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class TrapChessComponent extends JComponent{

    public TrapChessComponent(PlayerColor color, int size) {
        setSize(size, size);
        setLocation(0,0);
        setVisible(true);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ImageIcon image = new ImageIcon(getClass().getResource("/trap.png"));
        g.drawImage(image.getImage(), 0, 0, getWidth()-1, getHeight()-1, this);
    }
}
