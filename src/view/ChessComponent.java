package view;


import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ChessComponent extends JComponent {
    protected PlayerColor owner;

    protected boolean selected;

    public ChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size/2, size/2);
        setLocation(0,0);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 如果棋子是蓝方的，就画一个蓝色的环
        if (owner == PlayerColor.BLUE) {
            g.setColor(Color.BLUE);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
        // 如果棋子是红方的，就画一个红色的环
        else if (owner == PlayerColor.RED) {
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.BLACK);
            g.drawOval(0, 0, getWidth(), getHeight());
            g.drawOval(1, 1, getWidth() - 1, getHeight() - 1);
            g.drawOval(2, 2, getWidth() - 2, getHeight() - 2);
        }
    }
}
