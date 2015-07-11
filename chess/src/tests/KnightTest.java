package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.Board;

public class KnightTest {
	Board board = new Board(8,8);
	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	
	/**
	 * tests the knight's movement
	 */
	@Test
	public void movementTest() {
		assertTrue(board.getWhitePieces().get(KNIGHT).get(0).canMove(board, 2, 2));
		assertTrue(board.getWhitePieces().get(KNIGHT).get(0).canMove(board, 0, 2));
		assertFalse(board.getWhitePieces().get(KNIGHT).get(0).canMove(board, 4, 1));
		assertFalse(board.getWhitePieces().get(KNIGHT).get(0).canMove(board, -1000, 2));
		
		board.getWhitePieces().get(KNIGHT).get(0).move(board, 2, 2);
		assertEquals(2, board.getWhitePieces().get(KNIGHT).get(0).getRowPos());
	}

	/**
	 * tests the knight's capture
	 */
	@Test
	public void captureTest(){
		assertFalse(board.getWhitePieces().get(KNIGHT).get(0).canCapturePiece(board, 3, 3));
		assertFalse(board.getWhitePieces().get(KNIGHT).get(0).canCapturePiece(board, 3000, 3));
		board.teleport(board.getBlackPieces().get(PAWN).get(0), 2, 2);
		assertTrue(board.getWhitePieces().get(KNIGHT).get(0).canCapturePiece(board, 2, 2));
		board.getWhitePieces().get(KNIGHT).get(0).capture(board, 2, 2);
		assertEquals(2, board.getWhitePieces().get(KNIGHT).get(0).getRowPos());
	}
}
