package ru.rublevvladislav.tictactoe;

import org.slf4j.*;

import java.util.ArrayList;
import java.util.List;

public class BigField extends SmallField{
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private SmallField[] board;
    private int whichField = 4;
    private int gameMode = 0;

    private static final Logger log = LoggerFactory.getLogger(BigField.class);

    /**
     * Construct the big field.
     */
    BigField(){
        board = new SmallField[FIELD_SIZE*FIELD_SIZE];
        initialize();
    }

    /**
     * Create array of smallfields.
     */
    private void initialize(){
        for (int i=0; i<FIELD_SIZE*FIELD_SIZE; i++){
            board[i] = new SmallField();
        }
    }

    /**
     * Places an X or O on the specified location depending on who turn it is.
     * Choose which field will be active next.
     * @param x the x coordinate of location.
     * @param y the y coordinate of location.
     * @return true if it's possible, else false.
     */
    @Override
    public boolean move(int index){
        boolean returnable = board[whichField].move(index);
        if (returnable && !gameOver){
            whichField = index;
            log.info("Make move in field {} to position {}", whichField, index);
        }
        checkAvailableMoves();
        return returnable;
    }

    /**
     * Check if all cells aren't blank.
     */
    private void checkAvailableMoves(){
        for (int i=0; i<FIELD_SIZE*FIELD_SIZE; i++){
            if (board[i].movesAvailable.size()!=0){
                break;
            }
            if (i == FIELD_SIZE*FIELD_SIZE-1){
                gameOver = true;
                winner = State.Blank;
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int j=0; j<FIELD_SIZE; j++) {
            for (int x = 0; x < FIELD_SIZE; x++) {
                for (int i = 0; i < FIELD_SIZE; i++) {
                    for (int y = 0; y < FIELD_SIZE; y++) {
                        if(i+j*FIELD_SIZE == whichField && !isGameOver()){
                            sb.append(ANSI_RED);
                        }
                        if (board[i+j*FIELD_SIZE].field[x][y] == State.Blank) {
                            sb.append("-");
                        } else {
                            sb.append(board[i+j*FIELD_SIZE].field[x][y].name());
                        }
                        sb.append(" ");
                        if(i+j*FIELD_SIZE == whichField && !isGameOver()){
                            sb.append(ANSI_RESET);
                        }
                    }
                    sb.append("  ");
                    if (i == FIELD_SIZE - 1) {
                        sb.append("\n");
                    }
                }

            }
            sb.append("\n");
        }
        return new String(sb);
    }

    public int getWhichField() {
        return whichField;
    }

    public SmallField[] getBoard() {
        return board;
    }

    public int getGameMode(){
        return gameMode;
    }

    public void setGameMode(int gameMode){
        this.gameMode = gameMode;
    }

    public List<List<String>> getField(){
        List<List<String>> field = new ArrayList();
        for (int i=0; i<FIELD_SIZE*FIELD_SIZE; i++){
            List<String> arr = new ArrayList<>();
            arr = board[i].getSmallField();
            field.add(arr);
        }
        return field;
    }
}
