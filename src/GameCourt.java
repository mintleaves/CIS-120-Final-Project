/**
 * CIS 120 Game HW (c) University of Pennsylvania
 *
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

/**
 * gameCourt
 *
 * <p>This class holds the primary game logic for how different objects interact with one another.
 * Take time to understand how the timer interacts with the different methods and how it repaints
 * the GUI on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel implements ActionListener {

    private Tetriminos.Tetrimino[] gameCourt;

    private int width = 15;
    private int height = 15;
    private Timer timer;

    private int currentX = 0; // current X coordinate
    private int currentY = 0; // current Y coordinate
    private int level = 0; // current level
    private int points = 0; // current points
    private int timesDropped = 0; // number of times space bar is pressed

    private boolean pause = false;
    private boolean reachedTheEnd = false; // whether the block reached the end
    private boolean startedToFall = false; // whether the block has started to fall
    private boolean lineIsFull = true;

    private Map<String, Integer> highScorers =
            new TreeMap<String, Integer>(); // tree map for high scores
    private Stack<String> boosts = new Stack<String>(); // stack to store boosts
    private String username; // string to store username

    private JLabel status; // status bar

    private Tetriminos current; // the current shape

    class Adapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            if (!startedToFall || current.getTetriminos() == Tetriminos.Tetrimino.NONE) {
                return;
            }
            if (pause) {
                return;
            }
            int keycode = e.getKeyCode();

            if (keycode == 'P') {
                pause();
                return;
            }
            if (keycode == 'p') {
                pause();
                return;
            }

            switch (keycode) {
                case KeyEvent.VK_RIGHT:
                    tryToMove(current, currentX + 1, currentY);
                    break;
                case KeyEvent.VK_LEFT:
                    tryToMove(current, currentX - 1, currentY);
                    break;
                case KeyEvent.VK_UP:
                    tryToMove(current.rotateLeft(), currentX, currentY);
                    break;

                case KeyEvent.VK_DOWN:
                    tryToMove(current.rotateRight(), currentX, currentY);
                    break;

                case KeyEvent.VK_SPACE:
                    int newY = currentY;
                    while (newY > 0) {
                        if (!tryToMove(current, currentX, newY - 1)) {
                            break;
                        }
                        --newY;
                    }
                    pieceDropped();
                    break;
                default:
                    System.out.println("Invalid case");
            }
        }
    }

    public GameCourt(
            JLabel status, String username, Map<String, Integer> leaderBoard, JFrame frame) {

        highScorers = leaderBoard;

        setFocusable(true);

        gameCourt = new Tetriminos.Tetrimino[width * height];
        setFocusable(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GREEN));
        current = new Tetriminos();
        timer = new Timer(400, this);

        addKeyListener(new Adapter());
        clear();
        this.status = status;

        this.username = username;
    }

    public void setgameCourt(int w, int h) {
        gameCourt = new Tetriminos.Tetrimino[w * h];
    }

    public void actionPerformed(ActionEvent e) {
        if (reachedTheEnd) {
            setReachedTheEnd(false);
            newPiece();
        } else {
            if (!tryToMove(current, currentX, currentY - 1)) {

                pieceDropped();
            }
            
        }
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int l) {
        level = l;
    }

    public boolean getLineisFull() {
        return this.lineIsFull;
    }

    public void setLineisFull(boolean bool) {
        lineIsFull = bool;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int p) {
        points = p;
    }

    public int getTimesDropped() {
        return this.timesDropped;
    }

    public void setTimesDropped(int t) {
        timesDropped = t;
    }

    public JLabel getStatus()  {
        return this.status; }

    public boolean getReachedTheEnd() {
        return this.reachedTheEnd;
    }

    public void setReachedTheEnd(boolean bool) {
        reachedTheEnd = bool;
    }

    public boolean getPause() {
        return this.pause;
    }

    public void setPause(boolean bool) {
        pause = bool;
    }

    public void setCurrent(Tetriminos curr) {
        current = curr;
    }

    public Tetriminos getCurrent() {
        return this.current;
    }

    public void setCurrentX(int curr) {
        currentX = curr;
    }

    public int getCurrentX() {
        return this.currentX;
    }

    public void setCurrentY(int curr) {
        currentY = curr;
    }

    public int getwidth() {
        return this.width;
    }

    public void setwidth(int curr) {
        width = curr;
    }

    public int getheight() {
        return this.height;
    }

    public void setheight(int curr) {
        height = curr;
    }

    public int getCurrentY() {
        return this.currentY;
    }

    public boolean getStartedToFall() {
        return this.startedToFall;
    }

    public void setStartedToFall(boolean bool) {
        startedToFall = bool;
    }

    public void setStatus(String text)  {
        status.setText(text); }

    public void pause() {
        pause = !pause;
        if (pause) {
            timer.stop();
            status.setText("Game Paused- press resume to begin again."); 
        } else {
            timer.start();
            status.setText(
                    "Score: " + String.valueOf(points) + " " + "Level: " + String.valueOf(level));
        }

        repaint();
        requestFocusInWindow();
    }

    public void start() {

        if (pause) {
            pause();
        }
        setStartedToFall(true);
        setReachedTheEnd(false);
        setPoints(0);
        setLevel(0);
        clear();

        newPiece();
        timer.start();
        requestFocusInWindow();
    }

    private void clear() {
        for (int i = 0; i < height * width; ++i) { 
            gameCourt[i] = Tetriminos.Tetrimino.NONE; }
    }

    private int width() {
        return (int) getSize().getWidth() / width; }

    private int height() {
        return (int) getSize().getHeight() / height; }

    public Tetriminos.Tetrimino tetriminosInArray(
            Tetriminos.Tetrimino[] gameCourt, int x, int y) {
        return gameCourt[(y * width) + x]; }

    public void newPiece() {
        current.setRandom();

        currentX = width / 2;
        currentY = height - 1 + current.minY();

        if (!tryToMove(current, currentX, currentY)) {
            current.setTetriminos(Tetriminos.Tetrimino.NONE);
            timer.stop();

            setStartedToFall(false);
            setStatus("You lost!!");
        }
    }

    public void pieceDropped() {
        for (int i = 0; i < 4; ++i) {
            int x = currentX + current.getX(i);
            int y = currentY - current.getY(i);
            gameCourt[(y * width) + x] = current.getTetriminos(); }
        removeFull();
        setTimesDropped(++timesDropped);
        if (!reachedTheEnd) { 
            newPiece(); 
        }
        if (timesDropped % 5 == 0) {
            boosts.push("2 Add 5 points");
        }
    }

    public void addNextBoost() {
        if (!boosts.empty()) {
            String boost = boosts.pop();
            int nextBoost = Integer.parseInt(boost.substring(0, 1));
            if (nextBoost == 1) {
                current.setTetriminos(Tetriminos.Tetrimino.SQUARE);
                repaint(); 
            }  else if (nextBoost == 2) {
                points = points + 5;
                status.setText(
                        "Score: "
                                + String.valueOf(points)
                                + " "
                                + "Level: "
                                + String.valueOf(level));
                current.setTetriminos(Tetriminos.Tetrimino.VERTICAL);
                repaint();
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Dimension size = getSize();
        int topOfgameCourt = (int) size.getHeight() - height * height();
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Tetriminos.Tetrimino tetriminosTwo = 
                    tetriminosInArray(gameCourt, j, height - i - 1);
                if (tetriminosTwo != Tetriminos.Tetrimino.NONE) {
                    draw(g, j * width(), topOfgameCourt + i * height(), tetriminosTwo);
                }
            }
        }

        if (current.getTetriminos() != Tetriminos.Tetrimino.NONE) {
            for (int i = 0; i < 4; ++i) {
                int newX = currentX + current.getX(i);
                int newY = currentY - current.getY(i);
                draw(
                        g,
                        newX * width(),
                        topOfgameCourt + (height - newY - 1) * height(),
                        current.getTetriminos());
            }
        }
    }

    public boolean tryToMove(
            Tetriminos tetriminosTwo,
            int x,
            int y)  {
        for (int i = 0; i < 4; ++i) {

            int newX = x + tetriminosTwo.getX(i);
            int newY = y - tetriminosTwo.getY(i);

            if (newX < 0 || newX >= width || newY < 0 || newY >= height) {     
                return false; 
            }

            if (tetriminosInArray(gameCourt, newX, newY) != Tetriminos.Tetrimino.NONE) { 
                return false; 
            }
        }

        current = tetriminosTwo;
        currentX = x;
        currentY = y;
        repaint();
        return true;
    }

    public void removeFull() {

        System.out.println(gameCourt.length);
        int fullLine = 0;
        for (int i = height - 1; i >= 0; --i) {

            setLineisFull(true);

            for (int j = 0; j < width; ++j) {

                if (tetriminosInArray(gameCourt, j, i) == Tetriminos.Tetrimino.NONE) {
                    setLineisFull(false);
                    break;
                }
            }

            if (getLineisFull()) {

                ++fullLine;
                for (int k = i; k < height - 1; ++k) {
                    for (int j = 0; j < width; ++j) {
                        gameCourt[(k * width) + j] = tetriminosInArray(gameCourt, j, k + 1); }
                }
            }
        }

        if (fullLine > 0) {

            points = points + 20;
            int levelNew = levelUp();
            if (level != levelNew) {
                boosts.push("1 Change current block to square");
                level = levelNew;
            }
            status.setText(
                    "Score: " + String.valueOf(points) + " " + "Level: " + String.valueOf(level));
            reachedTheEnd = true;

            current.setTetriminos(Tetriminos.Tetrimino.NONE);

            repaint();
        }
    }

    public String showBoosts() {
        String allBoosts = "";
        while (!boosts.empty()) {
            allBoosts = allBoosts + " " + boosts.pop();
        }
        return allBoosts;
    }

    private void draw(Graphics g, int x, int y, Tetriminos.Tetrimino tetriminos) {

        Color[] colors = {
            new Color(0, 0, 0),
            new Color(0, 120, 255),
            new Color(189, 0, 255),
            new Color(255, 154, 0),
            new Color(1, 255, 31),
            new Color(227, 255, 0),
            new Color(255, 0, 127),
            new Color(255, 0, 0),
            new Color(128, 0, 0)
        };

        Color color = colors[tetriminos.ordinal()];

        g.setColor(color);

        g.fillRect(x + 1, y + 1, width() - 1, height() - 1);

        g.drawLine(x, y + height(), x + width(), y + height());
        g.drawLine(x + width(), y + height(), x + width(), y);
        g.drawLine(x, y + height(), x, y);
        g.drawLine(x, y, x + width(), y);
    }

    public int levelUp() {
        return points / 40;
    }

    public int saveProgressScore() {
        return points;
    }

    public void saveScore() {
        if (!(highScorers.containsKey(username)) || points > highScorers.get(username)) {
            highScorers.put(username, points);
        }
    }

    public String highScorers() {
        String[] names = new String[3];
        int[] scores = new int[3];
        String finals = "";
        Object[] highScores = highScorers.values().toArray();
        Integer[] highScoresInt = new Integer[highScores.length];
        for (int i = 0; i < highScores.length; i++) {
            highScoresInt[i] = (Integer) highScores[i]; 
        }
        Arrays.sort(highScoresInt, Collections.reverseOrder());
        if (highScoresInt.length > 3) {
            for (int i = 0; i < 3; i++) {
                scores[i] = highScoresInt[i];
                for (String j : highScorers.keySet()) {
                    if (highScorers.get(j) == scores[i]) {
                        names[i] = j;
                    }
                }
                finals = finals + names[i] + " " + scores[i] + "\n";
            }
        } else {
            for (int i = 0; i < highScoresInt.length; i++) {
                scores[i] = highScoresInt[i];
                for (String j : highScorers.keySet()) {
                    if (highScorers.get(j) == scores[i]) {
                        names[i] = j;
                    }
                }
                finals = finals + names[i] + " " + scores[i] + "\n";
            }
        }

        System.out.println(highScorers);
        System.out.println(names[1]);
        System.out.println(scores[1]);
        System.out.println(finals);

        return finals;
    }
}
