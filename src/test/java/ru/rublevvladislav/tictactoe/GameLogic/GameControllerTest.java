package ru.rublevvladislav.tictactoe.GameLogic;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class GameControllerTest{
	private SmallField game;

	@Before
	public void init() throws Exception {
		game = new SmallField();
	}
	/**
     * This test will simulate and verify x is the winner.
     *
     *    X | O | 
     *      | X | O
     *      |   | X
     */
	@Test
	public void testGetWinnerDiagFromLeftToRight() throws Exception {
		game.move(0);
		game.move(1);
		game.move(4);
		game.move(5);
		game.move(8);
		assertEquals(true, game.isGameOver());
		assertEquals("X won!", game.whoWinner());
	}
	/**
     * This test will simulate and verify x is the winner.
     *
     *    O | O | X
     *      | X | 
     *    X |   | 
     */
	@Test
	public void testGetWinnerDiagFromRightToLeft() throws Exception {
		game.move(2);
		game.move(1);
		game.move(4);
		game.move(0);
		game.move(6);
		assertEquals(true, game.isGameOver());
		assertEquals("X won!", game.whoWinner());
	}
	/**
     * This test will simulate and verify x is the winner.
     *
     *    X | X | X
     *    O |   | 
     *      |   | O
     */
	@Test
	public void testGetWinnerRowLine() throws Exception {
		game.move(0);
		game.move(3);
		game.move(1);
		game.move(8);
		game.move(2);
		assertEquals(true, game.isGameOver());
		assertEquals("X won!", game.whoWinner());
	}
	/**
     * This test will simulate and verify x is the winner.
     *
     *    X | O | 
     *    X |   | 
     *    X |   | O
     */
	@Test
	public void testGetWinnerColumnLine() throws Exception {
		game.move(0);
		game.move(1);
		game.move(3);
		game.move(8);
		game.move(6);
		assertEquals(true, game.isGameOver());
		assertEquals("X won!", game.whoWinner());
	}
	/**
     * This test will simulate and verify x is the winner.
     *
     *    X | O | X
     *    O | O | X
     *    X | X | O
     */
	@Test
	public void testTie() throws Exception {
		game.move(0);
		game.move(1);
		game.move(2);
		game.move(3);
		game.move(5);
		game.move(4);
		game.move(7);
		game.move(8);
		game.move(6);
		assertEquals(false, game.isGameOver());
		assertEquals("Tie!", game.whoWinner());
	}
}