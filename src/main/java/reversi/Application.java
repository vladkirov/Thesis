package reversi;

import reversi.gui.ReversiWindow;

public class Application {
    public static void main(String[] args) {
        ReversiWindow reversiWindow = ReversiWindow.createReversiWindow();
        reversiWindow.showWindow();
    }
}
