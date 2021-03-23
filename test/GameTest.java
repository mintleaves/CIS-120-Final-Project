import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.junit.jupiter.api.*;

/** You can use this file (and others) to test your implementation. */
public class GameTest {
    JLabel status = new JLabel("Current Score: 0 Level : 0");

    String username = "abc";

    Map<String, Integer> leaderBoard = new TreeMap<String, Integer>();

    final JFrame frame = new JFrame("TETRIS");

    GameCourt gc = new GameCourt(status, username, leaderBoard, frame);

    @Test
    public void testPause() {
        gc.pause();
        assertEquals(gc.getStatus().getText(), "Game Paused- press resume to begin again.");
    }

    @Test
    public void testTryToMoveFalse() {
        Tetriminos tetriminos2 = new Tetriminos();
        tetriminos2.setTetriminos(Tetriminos.Tetrimino.SQUARE);
        assertFalse(gc.tryToMove(tetriminos2, 26, 26));
    }

    @Test
    public void testTryToMoveTrue() {
        Tetriminos tetriminos2 = new Tetriminos();
        tetriminos2.setTetriminos(Tetriminos.Tetrimino.SQUARE);
        assertTrue(gc.tryToMove(tetriminos2, 3, 3));
    }

    @Test
    public void testDropBoosts() {
        gc.pieceDropped();
        gc.pieceDropped();
        gc.pieceDropped();
        gc.pieceDropped();
        gc.pieceDropped();
        gc.addNextBoost();
        assertEquals(gc.getStatus().getText(), "Score: 5 Level: 0");
    }

    @Test
    public void testStart() {
        gc.start();
        assertTrue(gc.getStartedToFall());
        assertFalse(gc.getReachedTheEnd());
    }

    @Test
    public void testDropped() {
        gc.setTimesDropped(3);
        gc.pieceDropped();
        assertEquals(gc.getTimesDropped(), 4);
    }

    @Test
    public void testNewPieceFalse() {
        Tetriminos tetriminos2 = new Tetriminos();
        tetriminos2.setTetriminos(Tetriminos.Tetrimino.SQUARE);
        gc.setCurrent(tetriminos2);
        gc.setCurrentY(3);
        gc.setCurrentX(3);
        gc.newPiece();
        assertFalse(gc.getStartedToFall());
        assertEquals(gc.getStatus().getText(), "Current Score: 0 Level : 0");
    }

    @Test
    public void testRemoveFull() {
        gc.setLineisFull(true);
        gc.removeFull();
        assertEquals(gc.getCurrent().getTetriminos(), Tetriminos.Tetrimino.NONE);
    }
}
