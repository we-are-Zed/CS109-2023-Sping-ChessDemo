package model;

import view.ChessboardComponent;
import controller.GameController;


import javax.swing.*;
import java.io.File;
import java.io.FileWriter;

public class Saver {
    public Chessboard board;
    public PlayerColor currentPlayer;

    public ChessboardComponent component;
    public void Save(String fileName) {
        String location = "save\\" + fileName + ".txt";
        File file = new File(location);

        try {
            if(file.exists()){     // 若文档存在，询问是否覆盖
                int n = JOptionPane.showConfirmDialog(component, "存档已存在，是否覆盖?", "", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    file.delete();
                }
            }

            // 创建文档
            FileWriter fileWriter = new FileWriter(location,true);

            fileWriter.write(board.steps.size() + "");
            fileWriter.write("\n");



            fileWriter.write(currentPlayer == PlayerColor.BLUE ? "b" : "r");
            fileWriter.write("\n");

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 7; j++) {
                    ChessPiece chess = board.grid[i][j].getPiece();
                    fileWriter.write(save(chess) + " ");
                }
                fileWriter.write("\n");
            }

            fileWriter.close();
            System.out.println("Save Done");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static String save(ChessPiece chessPiece)
    {
        if(chessPiece== null)
            return "0";
        else if(chessPiece.getName().equals("Elephant")) return "e";
        else if(chessPiece.getName().equals("Lion")) return "l";
        else if(chessPiece.getName().equals("Tiger")) return "t";
        else if(chessPiece.getName().equals("Leopard")) return "L";
        else if(chessPiece.getName().equals("Wolf")) return "w";
        else if(chessPiece.getName().equals("Dog")) return "d";
        else if(chessPiece.getName().equals("Cat")) return "c";
        else if(chessPiece.getName().equals("Rat")) return "r";
        else return "";
    }
    private Saver() {}
}
