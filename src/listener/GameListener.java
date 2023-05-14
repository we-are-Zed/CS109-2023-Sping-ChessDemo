package listener;

import model.ChessboardPoint;
import view.Animal.LionChessComponent;
import view.CellComponent;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);


    void onPlayerClickChessPiece(ChessboardPoint point, LionChessComponent.ElephantChessComponent component);

}
