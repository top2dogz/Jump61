**Background**

The KJumpingCube game1 is a two-person board game. It is a pure strategy game, involving no element of chance. 

**Rules of Jump61**

The game board consists of an N×N
array of squares, where N>1

. At any time, each square may have one of three colors: red, blue, or white (neutral), and some number of spots (as on dice). Initially, all squares are white and have one spot.

For purposes of naming squares in this description, we'll use the following notation: r:c
refers to the square at row r and column c, where 1≤r,c≤N

. Rows are numbered from top to bottom (top row is row 1) and columns are numbered from the left. When entering commands, we replace the colon with a space (this being easier to type).

The neighbors of a square are the horizontally and vertically adjacent squares (diagonally adjacent squares are not neighbors). We say that a square is overfull if it contains more spots than it has neighbors. Thus, the four corner squares are overfull when they have more than two spots; other squares on the edge are overfull with more than three spots; and all others are overfull with more than four spots.

There are two players, whom we'll call Red and Blue. The players each move in turn, with Red going first. A move consists of adding one spot on any square that does not have the opponent's color (so Red may add a spot to either a red or white square). A spot placed on any square colors that square with the player's color.
After the player has moved, we repeat the following steps until no square is overfull or either all squares are red or all blue:

    Pick an overfull square.
    For each neighbor of the overfull square, move one spot out of the square and into the neighbor (even if occupied by the opposing side).
    Give each of these neighboring squares the player's color (if they don't have it already).

The order in which this happens, as it turns out, does not usually matter—that is, the end result will be the same regardless of which overfull square's spots are removed first, with the exception that the winning position might differ. A player wins when all squares are the player's color.



The program should respond to the following textual commands. There is one command per line, but otherwise, whitespace may precede and follow command names and operands freely. Empty lines have no effect, and a command line whose first non-blank character is # is ignored as a comment. Extra arguments to a command (beyond those specified below) are ignored. An end-of-file indication on the command input should have the same effect as the quit command.

    new

Abandons the current game (if one is in progress), and clears the board to its initial configuration (all squares neutral with one spot, Red's move).

    quit

Exits the program.

    auto P

Causes player P to be played by an automated player (an AI) henceforth. The value P
must be red (or r), or blue (or b). Ignore case—Red, RED, or R) also work. Initially, Blue is an automated player.

    manual P

Causes player P to take moves entered by the user. The value of P
is as for the auto command. Initially, Red is a manual player.

    size N

Clears the board to its initial configuration, but with the size of the board at N squares, where 2≤N≤10. Initially, N=6
.

    set R C N P

This command is intended for setting up positions for testing and debugging. Put N spots at row R and column C. P denotes the player, as for the auto command. N must be positive and less than or equal to the number of neighboring squares. The player who is next to move is determined by the total number of spots on the board. Because Red always moves first, the player to move is chosen based on if the number of added spots is odd or even.

    seed N

If your program's automated players use pseudo-random numbers to choose moves, this command sets the random seed to N (a long integer). This command has no effect if there is no random component to your automated players (or if you don't use them in a particular game). It doesn't matter exactly how you use N as long as your automated player behaves identically each time it is seeded with N
In the absence of a seed command, do what you want to seed your generator.
    
    help
Print a brief summary of the commands.

**Entering Moves**

To enter moves from the terminal (for a manual player), use a command of the form

    RC
    
Adds a spot to the square at row R, column C, where R and C are integers in the range 1 to the current board size. Rows are numbered from the top, columns from the left. After the spot is added, spots are redistributed as indicated in the rules above. Like other commands, R and C
may be surrounded by any amount of whitespace. Illegal moves must be rejected (they have no effect on the board; the program should tell the user that there is an error and request another move).

The first and then every other move is for the red player, the second and then every other is for blue, and the normal legality rules apply to all moves.

**Output**

When either player enters a winning move, the program should print a line saying either of

     * Red wins.
     * Blue wins.

(with asterisks and periods as shown) as appropriate. Use exactly those phrases.

When an AI plays, print out the moves that it makes using the same format as

    * 1 4

That is, an asterisk followed by the row and column of a move.
