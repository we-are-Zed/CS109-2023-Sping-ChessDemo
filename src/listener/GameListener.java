package listener;

import model.ChessboardPoint;
import view.AnimalChessComponent;
import view.CellComponent;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);


    void onPlayerClickChessPiece(ChessboardPoint point, AnimalChessComponent component);

}
