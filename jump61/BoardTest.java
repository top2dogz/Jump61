
package jump61;

import static jump61.Side.*;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests of Boards.
 *  @author Izaac Ruiz
 */

public class BoardTest {

    private static final String NL = System.getProperty("line.separator");

    @Test
    public void testSize() {
        Board B = new Board(5);
        assertEquals("bad length", 5, B.size());
        ConstantBoard C = new ConstantBoard(B);
        assertEquals("bad length", 5, C.size());
        Board D = new Board(C);
        assertEquals("bad length", 5, D.size());
        Board F = new Board(8);
        assertNotEquals("bad length", F.size(), D.size());
    }

    @Test
    public void testSet() {
        Board B = new Board(5);
        B.set(2, 2, 1, RED);
        assertEquals("wrong number of spots", 1, B.get(2, 2).getSpots());
        assertEquals("wrong color", RED, B.get(2, 2).getSide());
        assertEquals("wrong count", 1, B.numOfSide(RED));
        assertEquals("wrong count", 0, B.numOfSide(BLUE));
        assertEquals("wrong count", 24, B.numOfSide(WHITE));
        B.set(1, 1, 1, RED);
        B.set(5, 5, 1, BLUE);
        assertEquals("wrong count", 2, B.numOfSide(RED));
        assertEquals("wrong count", 1, B.numOfSide(BLUE));
        B.set(1, 1, 1, RED);
        assertEquals("wrong count", 2, B.numOfSide(RED));
    }

    @Test
    public void testMove() {
        Board B = new Board(6);
        checkBoard("#0", B);
        B.addSpot(RED, 1, 1);
        checkBoard("#1", B, 1, 1, 2, RED);
        B.addSpot(BLUE, 2, 1);
        checkBoard("#2", B, 1, 1, 2, RED, 2, 1, 2, BLUE);
        B.addSpot(RED, 1, 1);
        checkBoard("#3", B, 1, 1, 1, RED, 2, 1, 3, RED, 1, 2, 2, RED);
        B.undo();
        checkBoard("#2U", B, 1, 1, 2, RED, 2, 1, 2, BLUE);
        B.undo();
        checkBoard("#1U", B, 1, 1, 2, RED);
        B.undo();
        checkBoard("#0U", B);

    }

    @Test
    public void testDump() {
        Board B = new Board(5);
        B.addSpot(RED, 1, 1);
        B.addSpot(BLUE, 5, 5);
        B.addSpot(RED, 1, 1);
        B.addSpot(BLUE, 5, 5);
        checkBoard("#4", B, 1, 1, 1, RED, 2, 1, 2, RED, 1, 2, 2, RED,
                5, 5, 1, BLUE, 4, 5, 2, BLUE, 5, 4, 2, BLUE);
    }

    @Test
    public void testSideAndWinner() {
        Board B = new Board(2);
        B.addSpot(RED, 1, 1);
        B.addSpot(BLUE, 1, 2);
        assertEquals(1, B.numOfSide(RED));
        assertEquals(1, B.numOfSide(BLUE));
        B.addSpot(RED, 1, 1);
        assertEquals(4, B.numOfSide(RED));
        assertEquals(RED, B.getWinner());
    }

    @Test
    public void testIsLegal() {
        Board B = new Board(4);
        assertTrue(B.isLegal(RED, 1));
        B.addSpot(RED, 1);
        assertFalse(B.isLegal(BLUE, 1));
        assertTrue(B.isLegal(BLUE, 2));
    }



    /** Checks that B conforms to the description given by CONTENTS.
     *  CONTENTS should be a sequence of groups of 4 items:
     *  r, c, n, s, where r and c are row and column number of a square of B,
     *  n is the number of spots that are supposed to be there and s is the
     *  color (RED or BLUE) of the square.  All squares not listed must
     *  be WHITE with one spot.  Raises an exception signaling a unit-test
     *  failure if B does not conform. */
    private void checkBoard(String msg, Board B, Object... contents) {
        for (int k = 0; k < contents.length; k += 4) {
            String M = String.format("%s at %d %d", msg, contents[k],
                                     contents[k + 1]);
            assertEquals(M, (int) contents[k + 2],
                         B.get((int) contents[k],
                               (int) contents[k + 1]).getSpots());
            assertEquals(M, contents[k + 3],
                         B.get((int) contents[k],
                               (int) contents[k + 1]).getSide());
        }
        int c;
        c = 0;
        for (int i = B.size() * B.size() - 1; i >= 0; i -= 1) {
            assertTrue("bad white square #" + i,
                       (B.get(i).getSide() != WHITE)
                       || (B.get(i).getSpots() == 1));
            if (B.get(i).getSide() != WHITE) {
                c += 1;
            }
        }
        assertEquals("extra squares filled", contents.length / 4, c);
    }

}
