package ru.rublevvladislav.tictactoe.ArtificialIntelligence;

import ru.rublevvladislav.tictactoe.BigField;

public class Random {
    public static void PlayMove(BigField field){
        int[] moves = new int[field.getBoard()[field.getWhichField()].getAvailableMoves().size()];
        int i = 0;

        for (Integer item : field.getBoard()[field.getWhichField()].getAvailableMoves()){
            moves[i++] = item;
        }
        int randomMove = moves[new java.util.Random().nextInt(moves.length)];
        field.move(randomMove);
    }
}
