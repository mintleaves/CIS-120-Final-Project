/**
 * CIS 120 Game HW (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */
// imports necessary libraries for Java swing

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.TreeMap;
import javax.swing.*;

/** Game Main class that specifies the frame and widgets of the GUI */
@SuppressWarnings("serial")
public class Game extends JFrame implements Runnable {

    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game

        final JFrame frame = new JFrame("TETRIS");
        frame.setLocation(300, 300);
        frame.setSize(500, 500);
        final JLabel scoreBoard = new JLabel("0");
        UIManager.put("OptionPane.messageFont", new Font("impact", Font.PLAIN, 12));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 12));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Map<String, Integer> leaderBoard = new TreeMap<String, Integer>();

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Current Score: 0 Level : 0");
        status_panel.add(status);

        // Input Username
        String name = "";
        try {
            while (name.equals("")) {
                name = JOptionPane.showInputDialog(frame, "Please enter a username.");
            }
        } catch (Exception e) {
            System.exit(0);
        }

        // Main playing area
        final GameCourt board = new GameCourt(status, name, leaderBoard, frame);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // File I/O for LeaderBoard
        try {
            BufferedReader br1 =
                    new BufferedReader(
                            new FileReader(
                "files\\LeaderBoard.txt"));
            String s1 = br1.readLine();
            String username = "";
            int points = 0;
            while (s1 != null) {
                points = Integer.parseInt(s1.substring(s1.indexOf(" ") + 1, s1.length()));
                username = s1.substring(0, s1.indexOf(" "));
                leaderBoard.put(username, points);
                s1 = br1.readLine();
            }
            br1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // The Play MenuBar
        JMenuBar menuBar = new JMenuBar();
        Font f = new Font("impact", Font.PLAIN, 20);
        Font f2 = new Font("impact", Font.PLAIN, 15);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f2);
        UIManager.put("MenuBar.background", Color.YELLOW);

        status.setFont(new Font("impact", Font.PLAIN, 15));
        status.setBackground(Color.GREEN);
        status.setOpaque(true);

        JMenu fileMenu = new JMenu("Play!");
        menuBar.setLayout(new GridBagLayout());

        JMenuItem newAction = new JMenuItem("Play a New Game");
        newAction.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        board.start();
                    }
                });

        JMenuItem directionsAction = new JMenuItem("Don't know how to play?");
        final String directions =
                "Tetris instructions\n"
                        + "\n"
                        + "Press 'New Game' to start a new game!\n"
                        + "Press 'Pause' to pause the game.\n"
                        + "Left Arrow Key: Pushes the block to the left\n"
                        + "Right Arrow Key: Pushes the block right\n"
                        + "Up Arrow Key: Rotates the block to the left\n"
                        + "Down Arrow Key: Rotates current block to the right\n"
                        + "\n"
                        + "There are two types of boosts.\n"
                        + "1. Adds 5 points & changes the shape of the block to vertical\n"
                        + "2. Adds 10 points & changes the shape of the block to square\n"
                        + "Press pause to pause the game and resume to resume it!\n"
                        + "Press save score to display your name on the leaderboard!";

        directionsAction.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        JOptionPane.showMessageDialog(null, directions);
                    }
                });

        JMenuItem pauseAction = new JMenuItem("Pause the game!");
        pauseAction.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        board.pause();
                    }
                });

        JMenuItem playAction = new JMenuItem("Resume the game!");
        playAction.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        board.pause();
                    }
                });

        fileMenu.add(newAction);
        fileMenu.add(directionsAction);
        fileMenu.add(pauseAction);
        fileMenu.add(playAction);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);

        control_panel.add(menuBar);

        // Other Buttons

        final JButton lBButton = new JButton("Leader Board");
        lBButton.setFont(new Font("impact", Font.PLAIN, 18));
        lBButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        JOptionPane.showMessageDialog(
                                frame,
                                board.highScorers(),
                                "Leader Board:",
                                JOptionPane.PLAIN_MESSAGE);
                        board.pause();
                    }
                });
        control_panel.add(lBButton);

        final JButton boostsButton = new JButton(" Add Boosts");
        boostsButton.setFont(new Font("impact", Font.PLAIN, 18));
        boostsButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        board.addNextBoost();
                        board.pause();
                    }
                });
        control_panel.add(boostsButton);

        final JButton showBoostsButton = new JButton(" Show Boosts");
        showBoostsButton.setFont(new Font("impact", Font.PLAIN, 18));
        showBoostsButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        JOptionPane.showMessageDialog(
                                frame,
                                board.showBoosts(),
                                "All Boosts:",
                                JOptionPane.PLAIN_MESSAGE);
                        board.pause();
                    }
                });
        control_panel.add(showBoostsButton);

        final JButton SaveScore = new JButton("SaveScore");
        SaveScore.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        board.saveScore();
                        try {
                            FileWriter fw =
                                    new FileWriter("files\\LeaderBoard.txt");
                            Writer out = new BufferedWriter(fw);
                            out.write(board.highScorers());
                            out.flush();
                            out.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        board.pause();
                    }
                });
        status_panel.add(SaveScore);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
