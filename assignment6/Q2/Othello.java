import java.io.*;
import java.util.*;

public class Othello {
    int turn;
    int winner;
    int board[][];

    // add required class variables here
    int expectedWinner;
    int MAX = Integer.MAX_VALUE;
    int MIN = Integer.MIN_VALUE;

    public Othello(String filename) throws Exception {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        turn = sc.nextInt();
        board = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = sc.nextInt();
            }
        }
        winner = -1;
        // Student can choose to add preprocessing here
        expectedWinner = turn;
    }

    // add required helper functions here

    public int boardScore() {
        /*
         * Complete this function to return num_black_tiles - num_white_tiles if turn =
         * 0,
         * and num_white_tiles-num_black_tiles otherwise.
         */
        int num_black_tiles = countTiles(0,board);
        int num_white_tiles = countTiles(1,board);
        if (turn == 0) {
            return num_black_tiles - num_white_tiles;
        }
        return num_white_tiles - num_black_tiles;

    }

    private int countTiles(int player,int[][]board1) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board1[i][j] == player) {
                    count++;
                }
            }
        }
        return count;
    }

    private void flipTiles(int row, int col, int[][] board1, int player) {
        int otherPlayer = 1 - player;
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                if (r == 0 && c == 0) {
                    continue;
                }
                int i = row + r;
                int j = col + c;
                while (i >= 0 && i < 8 && j >= 0 && j < 8 && board1[i][j] == otherPlayer) {
                    i += r;
                    j += c;
                }
                if (i >= 0 && i < 8 && j >= 0 && j < 8 && board1[i][j] == player) {
                    // flip tiles along this direction
                    i -= r;
                    j -= c;
                    while (i != row || j != col) {
                        board1[i][j] = player;
                        i -= r;
                        j -= c;
                    }
                }
            }
        }
    }

    private int minimax(int[][] board1, int depth, int alpha, int beta, boolean maximizingPlayer,int turn1) {
        if (depth == 0) {
            int num_black_tiles = countTiles(0, board1);
            int num_white_tiles = countTiles(1, board1);
            return turn1 == 0 ? num_black_tiles - num_white_tiles : num_white_tiles - num_black_tiles;
        }

        if (maximizingPlayer) {
            int bestValue = Integer.MIN_VALUE;
            boolean validMoveFound = false;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(isValidMove( i, j, board1,turn1)){
                    validMoveFound = true;
                    int[][] newBoard = makeMove(board1, i, j, turn1);
                    int value = minimax(newBoard, depth - 1, alpha, beta, false,turn1);
                    bestValue = Math.max(bestValue, value);
                    alpha = Math.max(alpha, bestValue);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            }
            return validMoveFound ? bestValue : countTiles(turn1, board1);
        } else {
            int bestValue = Integer.MAX_VALUE;
            boolean validMoveFound = false;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(isValidMove( i, j, board1,1 - turn1)){
                    int[][] newBoard = makeMove(board1, i, j, 1 - turn1);
                    int value = minimax(newBoard, depth - 1, alpha, beta, true,turn1);
                    bestValue = Math.min(bestValue, value);
                    beta = Math.min(beta, bestValue);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }
            return validMoveFound ? bestValue : countTiles(turn1, board1);
            
        }
    }

    public int bestMove(int k) {
        /*
         * Complete this function to build a Minimax tree of depth k (current board
         * being at depth 0),
         * for the current player (siginified by the variable turn), and propagate
         * scores upward to find
         * the best move. If the best move (move with max score at depth 0) is i,j;
         * return i*8+j
         * In case of ties, return the smallest integer value representing the tile with
         * best score.
         * 
         * Note: Do not alter the turn variable in this function, so that the
         * boardScore() is the score
         * for the same player throughout the Minimax tree.
         */
        int bestScore = Integer.MIN_VALUE;
        int ans = -1;
        int[][] copy2 = getBoardCopy();
        // System.out.println("copy2");
        // print(copy2);
        // get all possible moves 
        ArrayList<Integer>possibleMoves=new ArrayList<Integer>();

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board[i][j]==-1 && isValidMove(i, j,board,turn)){
                    possibleMoves.add(i*8+j);
                }
            }
        }
        // for each possible move, make a copy of the board, make the move, and calls minimax
        // on the copy
        // System.out.println("Started");
        // System.out.println(possibleMoves);
        for(int i=0;i<possibleMoves.size();i++){
            int[][] copy = getBoardCopy();
            int move=possibleMoves.get(i);
            int row=move/8;
            int col=move%8;
            copy[row][col]=turn;
            flipTiles(row, col, copy, turn);
            int score;
            if(expectedWinner==turn){
            score = minimax(copy, k-1, Integer.MAX_VALUE, Integer.MIN_VALUE,true,turn );
            }
            else score = minimax(copy, k-1, Integer.MAX_VALUE, Integer.MIN_VALUE,false,turn );
            // System.out.print("Move "+move+ " score: "+score+" ");
            if (score > bestScore) {
                copy2=copy;
                bestScore = score;
                ans = move;
            }
        }

        
        // System.out.println("turn " + turn);
        // print(copy2);
        // System.out.println();
        return ans;
    }

    private void print(int[][] board1){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board1[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] makeMove(int[][] board1, int row, int col, int player) {
        int[][] newBoard = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard[i][j] = board1[i][j];
            }
        }
        newBoard[row][col] = player;
        flipTiles(row, col, newBoard, player);
        return newBoard;
    }


    private boolean isValidMove(int row, int col, int[][] copy,int turn1) {
        if (copy[row][col] != -1) {
            return false;
        }
        boolean isValid = false;
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                // Skip the current cell and out-of-bounds cells
                if (r == 0 && c == 0 || row + r < 0 || row + r >= 8 || col + c < 0 || col + c >= 8) {
                    continue;
                }

                // Look for a contiguous straight line of white pieces
                int i = row + r;
                int j = col + c;
                while (i >= 0 && i < 8 && j >= 0 && j < 8 && copy[i][j] == 1 - turn1) {
                    i += r;
                    j += c;
                }
                if (i >= 0 && i < 8 && j >= 0 && j < 8 && copy[i][j] == turn1 && (i != row + r || j != col + c)) {
                    return true;
                }
            }
        }
        return isValid;
    }

    public ArrayList<Integer> fullGame(int k) {
        /*
         * Complete this function to compute and execute the best move for each player
         * starting from
         * the current turn using k-step look-ahead. Accordingly modify the board and
         * the turn
         * at each step. In the end, modify the winner variable as required.
         */
            ArrayList<Integer> moves = new ArrayList<Integer>();
            while (!isGameOver()) {
                int bestMove = bestMove( k);
                int row = bestMove / 8;
                int col = bestMove % 8;
                // System.out.println("row "+row+" col "+col);
                if(row==-1 || col==-1){
                    turn=1-turn;
                    bestMove = bestMove(k);
                    row = bestMove / 8;
                    col = bestMove % 8;
                    if(row==-1 || col==-1){
                        break;
                    }
                }
                board = makeMove(board, row, col, turn);
                // System.out.println(bestMove);
                // System.out.println("Board");
                // print(board);
                moves.add(bestMove);
                turn = 1 - turn;
            }
            winner = getWinner();
            return moves;
        }
        
        

        public boolean isGameOver() {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j]==-1) {
                        return false;
                    }
                }
            }
            return true;
        }
    public int[][] getBoardCopy() {
        int copy[][] = new int[8][8];
        for (int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int getWinner() {
        if (winner != -1) {
            return winner;
        }
        int blackCount = 0;
        int whiteCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0) {
                    blackCount++;
                } else if (board[i][j] == 1) {
                    whiteCount++;
                }
            }
        }
        if (blackCount > whiteCount) {
            winner = 0;
        } else if (whiteCount > blackCount) {
            winner = 1;
        } else {
            winner = -1;
        }
        return winner;
    }
    

    public int getTurn() {
        return turn;
    }

    public static void main(String[] args) throws Exception {
        Othello game = new Othello("../../input2.txt");
        // System.out.println("Winner: " + game.getWinner());
        // System.out.println("Moves: " + moves);

        // System.out.println("Board:");
        // for(int i = 0; i < 8; ++i){
        // for(int j = 0; j < 8; ++j){
        // System.out.print(game.board[i][j] + " ");
        // }
        // System.out.println();
        // }
        System.out.println(game.boardScore());
        System.out.println(game.fullGame(5));
        System.out.println(game.getWinner());
    }
}
