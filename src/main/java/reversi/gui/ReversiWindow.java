package reversi.gui;

import lombok.NonNull;
import reversi.additional.ConstGUI;
import reversi.games.Game;
import reversi.games.GameFactory;
import reversi.players.LocalPlayer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/**
 * Main window for GUI
 */
public class ReversiWindow extends JFrame {
    /**
     * Size game board
     */
    private final int size;

    /**
     * Current game
     */
    private Game game;

    // GUI elements
    private JLabel[][] labels;
    private JTextField fieldNickName;
    private JTextField fieldActivePlayer;

    /**
     * Create window and initialize new game
     * @return window with game
     */
    public static ReversiWindow createReversiWindow() {
        return new ReversiWindow(ConstGUI.FIELD_COUNT, ConstGUI.WINDOW_MAIN_TITLE);
    }

    /**
     * Private constructor for fabric method
     * @param size size of game board
     * @param title window title
     */
    private ReversiWindow(int size, String title) {
        super(title);

        this.size = ConstGUI.FIELD_COUNT;
        labels = new JLabel[this.size][this.size];

        // Frame
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Menu
        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(createGameMenu());
        setJMenuBar(jMenuBar);

        // Statistic
        getContentPane().add(createStatisticBlock(), BorderLayout.NORTH);

        // Game board
        getContentPane().add(createGameBoard(), BorderLayout.CENTER);

        pack();
    }

    /**
     * Show created window
     */
    public void showWindow() {
        setVisible(true);
    }

    /**
     * Create block Statistic
     *
     * @return panel with field for showing/editing data
     */
    private JPanel createStatisticBlock() {
        JLabel labelNickName = new JLabel("Nickname:");
        fieldNickName = new JTextField();

        JLabel labelActivePlayer = new JLabel("Active:");
        fieldActivePlayer = new JTextField();
        fieldActivePlayer.setEditable(false);

        JPanel header = new JPanel(new BorderLayout());
        JPanel grid = new JPanel(new BorderLayout());
        grid.add(labelNickName, BorderLayout.WEST);
        grid.add(fieldNickName, BorderLayout.CENTER);
        header.add(grid);

        grid = new JPanel(new BorderLayout());
        grid.add(labelActivePlayer, BorderLayout.WEST);
        grid.add(fieldActivePlayer, BorderLayout.CENTER);
        header.add(grid);
        header.setLayout(new GridLayout(2, 1, 5, 5));

        return header;
    }

    /**
     * Create GUI game board with fields
     *
     * @return panel with fields
     */
    private JPanel createGameBoard() {
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(size, size, ConstGUI.FIELD_PADDING, ConstGUI.FIELD_PADDING));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JLabel label = new JLabel();
                labels[i][j] = label;

                label.setIcon(new ImageIcon(getClass().getResource(ConstGUI.PATH_IMG_BACK)));
                label.addMouseListener(new MouseClickListener());
                grid.add(label);
            }
        }

        return grid;
    }

    /**
     * Create menu with assigned listeners
     *
     * @return menu panel
     */
    private JMenu createGameMenu() {
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGamePvP = new JMenuItem("New local PvP");
        newGamePvP.addActionListener(new MenuNewGamePvPListener());
        gameMenu.add(newGamePvP);

        JMenuItem newGamePvC = new JMenuItem("New local PvС");
        newGamePvC.addActionListener(new MenuNewGamePvCListener());
        gameMenu.add(newGamePvC);

        JMenuItem exitGame = new JMenuItem("Exit");
        exitGame.addActionListener(new MenuExitGameListener());
        gameMenu.add(exitGame);

        return gameMenu;
    }

    /**
     * Paint game board by color data from game board at the game
     */
    private void paintGameBoard() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                String path = ConstGUI.getPathByColor(game.getGameBoardStatistic().getColorByIndex(i, j));
                labels[i][j].setIcon(new ImageIcon(getClass().getResource(path)));
                labels[i][j].setText(path);
            }
    }

    private void showMsg(String title) {
        JOptionPane.showMessageDialog(this, title);
    }

    /**
     * Listener for Menu "Exit"
     */
    class MenuExitGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * Listener for new game PvP and initialization the game
     */
    class MenuNewGamePvPListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (fieldNickName.getText().equals("")) showMsg("NickName not inputted!");
            else {
                fieldNickName.setEditable(false);

                GameFactory gameFactory = new GameFactory(
                        LocalPlayer.createWhiteHuman(fieldNickName.getText()),
                        LocalPlayer.createBlackHuman(""));
                game = gameFactory.createPvP();
                game.newGame();
                game.start();

                paintGameBoard();
                fieldActivePlayer.setText(
                        game.getActivePlayer().getColor() + " :: " + game.getActivePlayer().getNickName());
            }
        }
    }

    /**
     * Listener for new game PvC and initialization the game
     */
    class MenuNewGamePvCListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (fieldNickName.getText().equals("")) showMsg("NickName not inputted!");
            else {
                fieldNickName.setEditable(false);

                GameFactory gameFactory = new GameFactory(
                        LocalPlayer.createWhiteHuman(fieldNickName.getText()),
                        LocalPlayer.createBlackAI());
                game = gameFactory.createPvC();
                if (game == null) {
                    showMsg("Incorrect players! This game a game of human versus computer!");
                    return;
                }

                game.newGame();
                game.start();
                paintGameBoard();
                fieldActivePlayer.setText(
                        game.getActivePlayer().getColor() + " :: " + game.getActivePlayer().getNickName());
            }
        }
    }

    /**
     * Click listener of player. If next AI: recursive to make next step - paintStep()
     */
    class MouseClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            SwingUtilities.invokeLater(() -> {
                if (game == null || game.notProgress()) showMsg("Game isn't started!");

                JLabel label = (JLabel) event.getSource();
                if (!label.getText().equals(ConstGUI.PATH_IMG_BACK)) return;

                Coordinate coordinateHuman = getCoordinatesField(label);
                if (coordinateHuman != null) paintStep(coordinateHuman);
            });
        }
    }

    /**
     * Recursive next step based on analysis next step and type player (AI or not)
     * @param coordinate coordinates for next step
     */
    private void paintStep(@NonNull Coordinate coordinate) {
        if (game.makeStep(coordinate.getRow(), coordinate.getCol())) {
            paintGameBoard();
            fieldActivePlayer.setText(
                    game.getActivePlayer().getColor() + " :: " + game.getActivePlayer().getNickName());

            if (game.notProgress()) {
                showMsg("Game over! Whites: "
                        + game.getGameBoardStatistic().getCountWhites() + ", Blacks: "
                        + game.getGameBoardStatistic().getCountBlacks());

                fieldNickName.setEditable(false);
            }

            if (game.getActivePlayer().isAI()) {
                Coordinate coordinateAI = game.getCoordinatesOfBestStep();
                if (coordinateAI.getCount() > 0) {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            TimeUnit.SECONDS.sleep(ConstGUI.SECOND_IMITATE_THINKING_AI);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        paintStep(coordinateAI);
                    });
                }
            }
        } else showMsg("It's impossible to take this step.");
    }

    /**
     * Get coordinates by JLabel from GUI-array game board
     * @param label label from GUI array
     * @return coordinates
     */
    private Coordinate getCoordinatesField(@NonNull JLabel label) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (labels[i][j] == label) return new Coordinate(i, j);
            }
        }
        return null;
    }
}