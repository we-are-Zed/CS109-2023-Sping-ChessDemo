package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class AnimalChessComponent extends JComponent{
    private PlayerColor owner;

    private boolean selected;
    public AnimalChessComponent(PlayerColor owner, int size) {
        this.owner = owner;
        this.selected = false;
        setSize(size, size);
        setLocation(0,0);
        setVisible(true);
    }
    public PlayerColor getOwner() {
        return owner;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected()) { // 如果选中就highlight
            g.setColor(Color.GRAY);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

}
