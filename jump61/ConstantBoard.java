package jump61;

/** A ConstantBoard is a view of an existing Board that does not allow
 *  modifications. Changes made to the underlying Board are reflected in
 *  ConstantBoards formed from it.
 *  @author P. N. Hilfinger
 */
class ConstantBoard extends Board {

    /** A new ConstantBoard that allows a read-only view of BOARD. That is,
     *  all operations are delegated to BOARD. */
    ConstantBoard(Board board) {
        _board = board;
    }

    @Override
    Board readonlyBoard() {
        return this;
    }

    @Override
    int size() {
        return _board.size();
    }

    @Override
    Square get(int n) {
        return _board.get(n);
    }

    @Override
    int numPieces() {
        return _board.numPieces();
    }

    @Override
    Side whoseMove() {
        return _board.whoseMove();
    }

    @Override
    boolean isLegal(Side player, int r, int c) {
        return _board.isLegal(player, r, c);
    }

    @Override
    boolean isLegal(Side player) {
        return _board.isLegal(player);
    }

    @Override
    int numOfSide(Side color) {
        return _board.numOfSide(color);
    }

    @Override
    public boolean equals(Object obj) {
        return _board.equals(obj);
    }

    @Override
    public int hashCode() {
        return _board.hashCode();
    }

    /* Mutators modified to do nothing. */

    @Override
    void clear(int N) {
    }

    @Override
    void copy(Board board) {
    }

    @Override
    void addSpot(Side player, int r, int c) {
    }

    @Override
    void addSpot(Side player, int n) {
    }

    @Override
    void set(int r, int c, int num, Side player) {
    }

    @Override
    void undo() {
    }

    /** Board to which all operations are delegated. */
    private Board _board;


}
