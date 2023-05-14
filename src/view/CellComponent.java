package view;

import model.CellType;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    public Color background;
    public CellType cellType;

//    public boolean mouseOn;
//    public boolean canMove;



    public CellComponent(Color background, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.background = background;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);

        if(cellType == CellType.dayGrass){
            background = new Color(120,204,0);
        }else if(cellType == CellType.nightGrass){
            background = new Color(5,88,37);
        }else if(cellType == CellType.dayRiver){
            background = new Color(114,218,210);
        }else if(cellType == CellType.nightRiver){
            background = new Color(8,127,152);
        }else if(cellType == CellType.dayTrap) {
            background = new Color(245, 105, 105);
        }else if(cellType == CellType.nightTrap){
            background = new Color(131, 17, 17);
        }else if(cellType == CellType.dayDen){
            background = new Color(192, 192, 192);
        }else if(cellType == CellType.nightDen){
            background = new Color(100, 100, 100);
        }

        g.setColor(background);
        g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);

//        if(mouseOn){}
//        if(canMove){}


    }
}
