package ru.rublevvladislav.tictactoe;

import org.slf4j.*;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

public class SmallField {
    static final int FIELD_SIZE = 3;
    public enum State {Blank, X, O}
    State[][] field;
    static State playersTurn;
    static State winner;
    HashSet<Integer> movesAvailable;
    static boolean gameOver;

    private static final Logger log = LoggerFactory.getLogger(SmallField.class);

    /**
     * Construct the field.
     */
    SmallField(){
        field = new State[FIELD_SIZE][FIELD_SIZE];
        movesAvailable = new HashSet<>();
        reset();
    }

    /**
     * Return game to init state.
     */
    void reset(){
        gameOver = false;
        playersTurn = State.X;
        winner = State.Blank;
        initialize();
    }

    /**
     * Set the cells to be blank and load the available moves (all moves available at start of the game).
     */
    private void initialize(){
        for (int row = 0; row<FIELD_SIZE; row++){
            for (int col = 0; col < FIELD_SIZE; col++) {
                field[row][col] = State.Blank;
            }
        }
        movesAvailable.clear();
        for (int i = 0; i<FIELD_SIZE*FIELD_SIZE; i++){
            movesAvailable.add(i);
        }
    }

    /**
     * Places an X or O on the specified location depending on who turn it is.
     * @param x the x coordinate of location.
     * @param y the y coordinate of location.
     * @return true if it's possible, else false.
     */
    private boolean move(int x, int y) {
        if (gameOver) {
            throw new IllegalStateException("GameOver, no moves can be played");
        }

        if (field[x][y] == State.Blank) {
            field[x][y] = playersTurn;
        }
        else {
            return false;
        }

        movesAvailable.remove(x * FIELD_SIZE + y);

        checkRow(x);
        checkColumn(y);
        checkDiagonalFromLeft(x, y);
        checkDiagonalFromRight(x, y);

        playersTurn = (playersTurn == State.X) ? State.O : State.X;
        return true;
    }

    /**
     * Places an X or an O on the specified index depending on who turn it is.
     * @param index     the position on the board (example: index 2 is location (0, 2))
     * @return          true if the move has not already been played
     */
    boolean move(int index){
        return move(index / FIELD_SIZE, index % FIELD_SIZE);
    }

    /**
     * Check the specified row to see if there is a winner.
     * @param row the row to check.
     */
    private void checkRow(int row){
        for (int i=1; i<FIELD_SIZE; i++){
            if (field[row][i] != field[row][i-1]){
                break;
            }
            if (i == FIELD_SIZE - 1){
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    /**
     * Check the specified column to see if there is a winner.
     * @param column the column to check.
     */
    private void checkColumn(int column){
        for (int i=1; i<FIELD_SIZE; i++){
            if (field[i][column] != field[i-1][column]){
                break;
            }
            if (i == FIELD_SIZE - 1){
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    /**
     * Check the left diagonal to see if there is a winner.
     * @param row the x coordinate of recently played move.
     * @param column the y coordinate of recently played move.
     */
    private void checkDiagonalFromLeft(int row, int column){
        if (row == column){
            for (int i=1; i<FIELD_SIZE; i++){
                if (field[i][i] != field[i-1][i-1]){
                    break;
                }
                if (i == FIELD_SIZE -1){
                    winner = playersTurn;
                    gameOver = true;
                }
            }
        }
    }

    /**
     * Check the right diagonal to see if there is a winner.
     * @param row the x coordinate of recently played move.
     * @param column the y coordinate of recently played move.
     */
    private void checkDiagonalFromRight(int row, int column){
        if (row == FIELD_SIZE-column-1){
            for (int i=1; i<FIELD_SIZE; i++){
                if (field[FIELD_SIZE-i-1][i] != field[FIELD_SIZE-i][i-1]){
                    break;
                }
                if (i == FIELD_SIZE -1){
                    winner = playersTurn;
                    gameOver = true;
                }
            }
        }
    }

    /**
     * Check to see if the game is over
     * @return true if the game is over
     */
    boolean isGameOver(){
        return gameOver;
    }

    /**
     * Check to see who's turn.
     * @return X or O, depending who turn it is.
     */
    State getTurn(){
        return playersTurn;
    }

    /**
     * Check to see who won.
     * @return the player who won.
     */
    State getWinner(){
        return winner;
    }

    String whoWinner(){
        if (winner==State.X){
            return "X won!";
        }
        else if (winner==State.O){
            return "O won!";
        }
        else{
            return "Tie!";
        }
    }

    public HashSet<Integer> getAvailableMoves(){
        return movesAvailable;
    }

    List<String> getSmallField(){
        List<String> field1 = new ArrayList<String>();
        for (int x=0; x<FIELD_SIZE; x++){
            for (int y=0; y<FIELD_SIZE; y++){
                if (field[x][y] == State.Blank){
                    field1.add("---");
                }
                else if (field[x][y] == State.X){
                    field1.add("X");
                }
                else{
                    field1.add("O");
                }
            }
        }
        return field1;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int x=0; x<FIELD_SIZE; x++){
            for (int y=0; y<FIELD_SIZE; y++){
                if (field[x][y] == State.Blank){
                    sb.append("-");
                }
                else{
                    sb.append(field[x][y].name());
                }
                sb.append(" ");
                if (y == FIELD_SIZE - 1){
                    sb.append("\n");
                }
            }

        }
        return new String(sb);
    }
}
