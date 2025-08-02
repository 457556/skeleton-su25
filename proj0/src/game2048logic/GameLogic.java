package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return minR.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        // TODO: Fill this in in tasks 2, 3, 4
        if (r <= minR) {
            return minR;
        } else if (board[r][c] == 0) {
            return minR;
        }

        int row = minR;
        boolean foundObstacle = false;

        for (int y = r - 1; y >= minR; y -= 1) {
            if (board[y][c] != 0) {
                row = y;
                foundObstacle = true;
                break;
            }
        }
        int oldValue = board[r][c];
        board[r][c] = 0;
        if (foundObstacle) {
            if (board[row][c] == oldValue) {
                board[row][c] = oldValue * 2;
                return 1 + row;
            } else {
                board[row + 1][c] = oldValue;
            }
        } else {
            board[minR][c] = oldValue;
        }
        return minR;
    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        // TODO: fill this in in task 5
        int newMinR = 0;
        for (int r =  0; r < board.length; r += 1) {
            if (board[r][c] == 0) {
                continue;
            }
            int result = moveTileUpAsFarAsPossible(board, r, c, newMinR);
            newMinR = result;
        }
        return;
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        // TODO: fill this in in task 6
        for (int l = 0; l < board[0].length; l += 1) {
            tiltColumn(board, l);
        }
        return;
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // TODO: fill this in in task 7
        if (side == Side.NORTH) {
            tiltUp(board);
            return;
        } else if (side == Side.EAST) {
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
            return;
        } else if (side == Side.SOUTH) {
            rotateRight(board);
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
            rotateLeft(board);
            return;
        } else if (side == Side.WEST) {
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
            return;
        } else {
            System.out.println("Invalid side specified");
        }
    }
}
