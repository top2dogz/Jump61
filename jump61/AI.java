
package jump61;

import java.util.ArrayList;
import java.util.Random;

import static jump61.Side.*;

/** An automated Player.
 *  @author P. N. Hilfinger
 */
class AI extends Player {

    /** A new player of GAME initially COLOR that chooses moves automatically.
     *  SEED provides a random-number seed used for choosing moves.
     */
    AI(Game game, Side color, long seed) {
        super(game, color);
        _random = new Random(seed);
    }

    @Override
    String getMove() {
        Board board = getGame().getBoard();

        assert getSide() == board.whoseMove();
        int choice = searchForMove();
        getGame().reportMove(board.row(choice), board.col(choice));
        return String.format("%d %d", board.row(choice), board.col(choice));
    }

    /** Generates legal moves of a board position into an array.
     * @param side Indicates which side to generate moves.
     * @param board The current state of the board.
     * @return All legal moves for a certain side */
    private ArrayList<Integer> moveGenerator(Side side, Board board) {
        int size = board.size() * board.size();
        ArrayList<Integer> moves = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (board.isLegal(side, i)) {
                moves.add(i);
            }
        }
        return moves;
    }

    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private int searchForMove() {
        Board work = new Board(getBoard());
        int value;
        assert getSide() == work.whoseMove();
        ArrayList<Integer> moves;
        _foundMove = -1;
        int bestScore = (int) Double.NEGATIVE_INFINITY;
        if (getSide() == RED) {
            moves = moveGenerator(RED, work);
        } else {
            moves = moveGenerator(BLUE, work);
        }
        for (Integer move : moves) {
            work.addSpot(getSide(), move);
            value = minMax(work, 4, 4,
                    (int) Double.NEGATIVE_INFINITY,
                    (int) Double.POSITIVE_INFINITY);
            work.undo();
            if (value > bestScore) {
                bestScore = value;
                _foundMove = move;
            }
        }
        return _foundMove;
    }


    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int minMax(Board board, int depth,
                       int sense, int alpha, int beta) {
        if (depth == 0 || board.getWinner() != null) {
            return staticEval(board, WIN_MOVE_WEIGHT, depth);
        }
        ArrayList<Integer> moves;
        int bestSoFar = (int) Double.NEGATIVE_INFINITY;
        if (sense > 0) {
            moves = moveGenerator(getSide(), board);
            for (int move : moves) {
                board.addSpot(getSide(), move);
                bestSoFar = Math.max(minMax(board, depth - 1,
                        -sense, alpha, beta), bestSoFar);
                alpha = Math.max(alpha, bestSoFar);
                if (alpha >= beta) {
                    break;
                }
                return bestSoFar;
            }
        } else {
            bestSoFar = (int) Double.POSITIVE_INFINITY;
            moves = moveGenerator(getSide().opposite(), board);
            for (int move: moves) {
                board.addSpot(getSide().opposite(), move);
                bestSoFar = Math.min(minMax(board, depth - 1,
                        -sense, alpha, beta), bestSoFar);
                beta = Math.min(beta, bestSoFar);
                if (alpha >= beta) {
                    break;
                }
                return bestSoFar;
            }
        }
        return bestSoFar;
    }

    /** Return a heuristic estimate of the value of board position B.
     *  Use WINNINGVALUE to indicate a win for Red and -WINNINGVALUE to
     *  indicate a win for Blue. DEPTH represents position in
     *  minMax function. */
    private int staticEval(Board b, int winningValue, int depth) {
        int value;
        Side mySide = getSide();
        Side opponent = mySide.opposite();
        if (b.getWinner() == mySide) {
            return winningValue * (1 + depth);
        }
        if (b.getWinner() == opponent) {
            return -winningValue * (1 + depth);
        }
        int myEval = b.numOfSide(mySide);
        int oppEval = b.numOfSide(opponent);

        value = myEval - oppEval;


        return value;

    }

    /** A random-number generator used for move selection. */
    private Random _random;

    /** Used to convey moves discovered by minMax. */
    private int _foundMove;

    /** */
    public static final int WIN_MOVE_WEIGHT = 1000;

}
