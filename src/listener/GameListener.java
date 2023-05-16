package listener;

import model.ChessboardPoint;
import view.AnimalChessComponent;
import view.CellComponent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);


    void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component);

}
