import controller.GameController;
import model.Chessboard;
import view.Begin;
import view.ChessGameFrame;

import javax.swing.*;
import java.net.URL;
import view.Music;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Begin begin = new Begin();
            GameController gameController = new GameController(begin.getChessGameFrame().getChessboardComponent(), new Chessboard());
            begin.setVisible(true);
        });
        URL musicURL = Main.class.getResource("/music.wav");

        Music musicThread = new Music(musicURL, true);

        Thread music = new Thread(musicThread);
        music.start();

        musicThread.setVolume(0.5f);
    }
}
