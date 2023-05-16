package view.Animal;


import model.PlayerColor;
import view.AnimalChessComponent;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class DogChessComponent extends AnimalChessComponent {
    private ImageIcon image;

    public DogChessComponent(PlayerColor owner, int size) {
        super(owner,size);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getOwner().getColor());
        if(getOwner().getColor() == Color.RED){
            image = new ImageIcon(getClass().getResource("/Rdog.png"));
        } else {
            image = new ImageIcon(getClass().getResource("/Bdog.png"));
        }

        if (image != null) {
            g.drawImage(image.getImage(), 0, 0, getWidth()-1, getHeight()-1, this);
        }

    }
}
