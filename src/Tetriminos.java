import java.util.*;
import java.util.Random;

public class Tetriminos {

    public enum Tetrimino {
        NONE,
        SQUARE,
        MIDDLE,
        VERTICAL,
        HORIZANTAL,
        LEFTSTAIR,
        RIGHSTAIR,
        LEFTTAIL,
        RIGHTTAIL
    };

    private Tetriminos.Tetrimino current;
    private int[][] coordinatesCurrent;
    private Map<Integer, int[][]> coordinatesTetriminos = new TreeMap<Integer, int[][]>();

    public Tetriminos() {
        coordinatesCurrent = new int[4][2];
        setTetriminos(Tetriminos.Tetrimino.NONE);
    }

    public void setTetriminos(Tetriminos.Tetrimino Tetriminos) {
        coordinatesTetriminos = new TreeMap<Integer, int[][]>();
        int[][] none = {{0, 0}, {0, 0}, {0, 0}, {0, 0}};
        int[][] square = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        int[][] middle = {{-1, 0}, {0, 0}, {1, 0}, {0, 1}};
        int[][] verticalLine = {{0, -1}, {0, 0}, {0, 1}, {0, 2}};
        int[][] horizantalLine = {{-1, 0}, {0, 0}, {1, 0}, {2, 0}};
        int[][] leftStair = {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}};
        int[][] rightStair = {{0, -1}, {0, 0}, {1, 0}, {1, 1}};
        int[][] leftTail = {{1, -1}, {0, -1}, {0, 0}, {0, 1}};
        int[][] rightTail = {{-1, 0}, {0, 0}, {1, 0}, {2, 0}};
        coordinatesTetriminos.put(0, none);
        coordinatesTetriminos.put(1, square);
        coordinatesTetriminos.put(2, middle);
        coordinatesTetriminos.put(3, verticalLine);
        coordinatesTetriminos.put(4, horizantalLine);
        coordinatesTetriminos.put(5, leftStair);
        coordinatesTetriminos.put(6, rightStair);
        coordinatesTetriminos.put(7, leftTail);
        coordinatesTetriminos.put(8, rightTail);
        coordinatesCurrent = coordinatesTetriminos.get(Tetriminos.ordinal());
        current = Tetriminos;
    }

    public void setRandom() {
        Random r = new Random();
        int r1 = Math.abs(r.nextInt()) % 8 + 1;
        Tetriminos.Tetrimino[] randValues = Tetriminos.Tetrimino.values();
        setTetriminos(randValues[r1]);
    }

    private void setX(int index, int x) {

        coordinatesCurrent[index][0] = x;
    }

    private void setY(int index, int y) {
        coordinatesCurrent[index][1] = y;
    }

    public int getX(int index) {
        return coordinatesCurrent[index][0];
    }

    public int getY(int index) {
        return coordinatesCurrent[index][1];
    }

    public Tetriminos rotateRight() {
        if (current == Tetriminos.Tetrimino.SQUARE) { 
            return this ; 
        }
        Tetriminos result = new Tetriminos();

        result.current = current;

        for (int i = 0; i < 4; ++i) {
            result.setY(i, getX(i));

            result.setX(i, -getY(i));
        }
        return result;
    }

    public Tetriminos rotateLeft() {
        if (current == Tetriminos.Tetrimino.SQUARE) { 
            return this; 
        }
        Tetriminos result = new Tetriminos();
        result.current = current;

        for (int i = 0; i < 4; ++i) {

            result.setY(i, -getX(i));
            result.setX(i, getY(i));
        }

        return result;
    }

    public int minX() {
        int x = coordinatesCurrent[0][0];

        for (int i = 0; i < 4; i++) {

            x = Math.min(x, coordinatesCurrent[i][0]);
        }
        return x;
    }

    public int minY() {
        int y = coordinatesCurrent[0][1];

        for (int i = 0; i < 4; i++) {

            y = Math.min(y, coordinatesCurrent[i][1]);
        }
        return y;
    }

    public Tetriminos.Tetrimino getTetriminos() {
        return current;
    }

    public int[][] getCooCurr() {
        return coordinatesCurrent;
    }
}
