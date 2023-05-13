package model;

import view.GridType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private final Set<ChessboardPoint> riverCell=new HashSet<>();
    private final Set<ChessboardPoint> denCell=new HashSet<>();
    private final Set<ChessboardPoint> trapCell=new HashSet<>();

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initSets();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (riverCell.contains(new ChessboardPoint(i, j))) {
                    grid[i][j] = new Cell(GridType.river);
                } else if (trapCell.contains(new ChessboardPoint(i, j))) {
                    grid[i][j] = new Cell(GridType.trap);
                    if(i<2)
                    {
                        grid[i][j].setOwner(PlayerColor.RED);
                    }
                    else{
                        grid[i][j].setOwner(PlayerColor.BLUE);
                    }
                }
                else if(denCell.contains(new ChessboardPoint(i,j)))
                {
                    grid[i][j] = new Cell(GridType.den);
                    if(i<2)
                    {
                        grid[i][j].setOwner(PlayerColor.RED);
                    }
                    else {
                        grid[i][j].setOwner(PlayerColor.BLUE);
                    }
                }
                else {
                    grid[i][j] = new Cell(GridType.grass);
                }
            }
        }
    }
    private void initPieces() {
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat",1));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));

        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.RED, "Rat",1));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[0][0].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
    }
    private void initSets()
    {
     riverCell.add(new ChessboardPoint(3,1));
     riverCell.add(new ChessboardPoint(3,2));
     riverCell.add(new ChessboardPoint(3,4));
     riverCell.add(new ChessboardPoint(3,5));
     riverCell.add(new ChessboardPoint(4,1));
     riverCell.add(new ChessboardPoint(4,2));
     riverCell.add(new ChessboardPoint(4,4));
     riverCell.add(new ChessboardPoint(4,5));
     riverCell.add(new ChessboardPoint(5,1));
     riverCell.add(new ChessboardPoint(5,2));
     riverCell.add(new ChessboardPoint(5,4));
     riverCell.add(new ChessboardPoint(5,5));


     denCell.add(new ChessboardPoint(8,3));
     denCell.add(new ChessboardPoint(0,3));

        trapCell.add(new ChessboardPoint(7,3));
        trapCell.add(new ChessboardPoint(0,2));
        trapCell.add(new ChessboardPoint(0,4));
        trapCell.add(new ChessboardPoint(1,3));
        trapCell.add(new ChessboardPoint(8,2));
        trapCell.add(new ChessboardPoint(8,4));
    }

    private ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        else
        {
            removeChessPiece(dest);
            setChessPiece(dest, removeChessPiece(src));
        }
        // TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        if(getGridAt(dest).getType()==GridType.river)
        {
            if(!Objects.equals(getChessPieceAt(src).getName(), "Rat"))
            {
                return false;
            }
            else {
                return calculateDistance(src, dest) == 1;
            }
        }
        if(calculateDistance(src, dest) > 1&&( getChessPieceAt(src).getName().equals("Lion")||getChessPieceAt(src).getName().equals("Tiger")))
        {
            if(src.getCol()!=dest.getCol()&&src.getRow()!=dest.getRow())
            {
                return false;
            }
            if(src.getCol()==dest.getCol())
            {
                if(src.getRow()>dest.getRow())
                {
                    for(int i=dest.getRow()+1;i<src.getRow();i++)
                    {
                        if(getGridAt(new ChessboardPoint(i,src.getCol())).getType()!=GridType.river)
                        {
                            return false;
                        }
                    }
                }
                if(src.getRow()<dest.getRow())
                {
                    for(int i=src.getRow()+1;i<dest.getRow();i++)
                    {
                        if(getGridAt(new ChessboardPoint(i,src.getCol())).getType()!=GridType.river)
                        {
                            return false;
                        }
                    }
                }
                if(src.getRow()<dest.getRow())
                {
                    for(int i=src.getRow()+1;i<dest.getRow();i++)
                    {
                        if(getGridAt(new ChessboardPoint(i,src.getCol())).getPiece()!=null)
                        {
                            return false;
                        }
                    }
                }
                if(src.getRow()>dest.getRow())
                {
                    for(int i=dest.getRow()+1;i<src.getRow();i++)
                    {
                        if(getGridAt(new ChessboardPoint(i,src.getCol())).getPiece()!=null)
                        {
                            return false;
                        }
                    }
                }
                return true;
            }
            if(src.getRow()==dest.getRow())
            {
                if(src.getCol()>dest.getCol())
                {
                    for(int i=dest.getCol()+1;i<src.getCol();i++)
                    {
                        if(getGridAt(new ChessboardPoint(src.getRow(),i)).getType()!=GridType.river)
                        {
                            return false;
                        }
                    }
                }
                if(src.getCol()<dest.getCol())
                {
                    for(int i=src.getCol()+1;i<dest.getCol();i++)
                    {
                        if(getGridAt(new ChessboardPoint(src.getRow(),i)).getType()!=GridType.river)
                        {
                            return false;
                        }
                    }
                }
                if(src.getCol()<dest.getCol())
                {
                    for(int i=src.getCol()+1;i<dest.getCol();i++)
                    {
                        if(getGridAt(new ChessboardPoint(src.getRow(),i)).getPiece()!=null)
                        {
                            return false;
                        }
                    }
                }
                if(src.getCol()>dest.getCol())
                {
                    for(int i=dest.getCol()+1;i<src.getCol();i++)
                    {
                        if(getGridAt(new ChessboardPoint(src.getRow(),i)).getPiece()!=null)
                        {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
    if(getGridAt(dest).getType()==GridType.den&&getGridAt(dest).getOwner()!=getChessPieceAt(src).getOwner())
    {
     return false;
    }

        return calculateDistance(src, dest) == 1;
    }
    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        // TODO:Fix this method
        ChessPiece srcPiece = getChessPieceAt(src);
        ChessPiece destPiece = getChessPieceAt(dest);
        if (srcPiece == null || destPiece == null) {
            return false;
        }
        if (srcPiece.getOwner() == destPiece.getOwner()) {
            return false;
        }
        if(getGridAt(dest).getType()==GridType.river||getGridAt(src).getType()==GridType.river)
        {
            return false;
        }
        if(calculateDistance(src, dest) > 1&&( getChessPieceAt(src).getName().equals("Lion")||getChessPieceAt(src).getName().equals("Tiger")))
        {
            if(src.getCol()!=dest.getCol()&&src.getRow()!=dest.getRow())
            {
                return false;
            }
            if(src.getCol()==dest.getCol())
            {
                if(src.getRow()>dest.getRow())
                {
                    for(int i=dest.getRow()+1;i<src.getRow();i++)
                    {
                        if(getGridAt(new ChessboardPoint(i,src.getCol())).getType()!=GridType.river)
                        {
                            return false;
                        }
                    }
                }
                if(src.getRow()<dest.getRow())
                {
                    for(int i=src.getRow()+1;i<dest.getRow();i++)
                    {
                        if(getGridAt(new ChessboardPoint(i,src.getCol())).getType()!=GridType.river)
                        {
                            return false;
                        }
                    }
                }
                if(src.getRow()<dest.getRow())
                {
                    for(int i=src.getRow()+1;i<dest.getRow();i++)
                    {
                        if(getGridAt(new ChessboardPoint(i,src.getCol())).getPiece()!=null)
                        {
                            return false;
                        }
                    }
                }
                if(src.getRow()>dest.getRow())
                {
                    for(int i=dest.getRow()+1;i<src.getRow();i++)
                    {
                        if(getGridAt(new ChessboardPoint(i,src.getCol())).getPiece()!=null)
                        {
                            return false;
                        }
                    }
                }
                return srcPiece.canCapture(destPiece);
            }
            if(src.getRow()==dest.getRow())
            {
                if(src.getCol()>dest.getCol())
                {
                    for(int i=dest.getCol()+1;i<src.getCol();i++)
                    {
                        if(getGridAt(new ChessboardPoint(src.getRow(),i)).getType()!=GridType.river)
                        {
                            return false;
                        }
                    }
                }
                if(src.getCol()<dest.getCol())
                {
                    for(int i=src.getCol()+1;i<dest.getCol();i++)
                    {
                        if(getGridAt(new ChessboardPoint(src.getRow(),i)).getType()!=GridType.river)
                        {
                            return false;
                        }
                    }
                }
                if(src.getCol()<dest.getCol())
                {
                    for(int i=src.getCol()+1;i<dest.getCol();i++)
                    {
                        if(getGridAt(new ChessboardPoint(src.getRow(),i)).getPiece()!=null)
                        {
                            return false;
                        }
                    }
                }
                if(src.getCol()>dest.getCol())
                {
                    for(int i=dest.getCol()+1;i<src.getCol();i++)
                    {
                        if(getGridAt(new ChessboardPoint(src.getRow(),i)).getPiece()!=null)
                        {
                            return false;
                        }
                    }
                }
                return srcPiece.canCapture(destPiece);
            }
        }
        return calculateDistance(src,dest)==1&&srcPiece.canCapture(destPiece);
    }
}
