package view.Animal;


import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class WolfChessComponent extends JComponent {
    private PlayerColor owner;

    private boolean selected;
    private ImageIcon gifImage;

    public WolfChessComponent(PlayerColor owner, int size) {
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
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(owner.getColor());
        if(owner.getColor() == Color.RED){
            gifImage = new ImageIcon(getClass().getResource("/Rwolf.png"));
        } else {
            gifImage = new ImageIcon(getClass().getResource("/Bwolf.png"));
        }

        if (gifImage != null) {
            g.drawImage(gifImage.getImage(), 0, 0, getWidth()-1, getHeight()-1, this);
        }

        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.GRAY);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
