package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.Bishop;
import chess.Board;

public class BishopTest {
	
	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	Board board = new Board(8,8);
	
	/**
	 * tests the movement for bishops
	 */
	@Test
	public void movementTest() {
		Bishop whiteBishop = (Bishop) board.getWhitePieces().get(BISHOP).get(0);
		Bishop blackBishop = (Bishop) board.getBlackPieces().get(BISHOP).get(0);
		
		assertFalse(whiteBishop.canMove(board, 1000, -1000)); //out of bounds
		assertFalse(blackBishop.canMove(board, 20, 10)); //out of bounds
		
		board.getWhitePieces().get(PAWN).get(2).move(board, 3, 2);
		board.getBlackPieces().get(PAWN).get(2).move(board, 3, 5);
		
		whiteBishop.move(board, 5, 3);
		blackBishop.move(board, 4, 5);
	}
	/**
	 * tests the capturing for bishops
	 */
	@Test
	public void captureTest(){
		Bishop whiteBishop = (Bishop) board.getWhitePieces().get(BISHOP).get(0);
		Bishop blackBishop = (Bishop) board.getBlackPieces().get(BISHOP).get(0);
		
		assertFalse(whiteBishop.canCapture(board, -1000, 100));
		board.teleport(whiteBishop, 4, 4);
		board.teleport(blackBishop, 3, 3);
		assertTrue(whiteBishop.canCapture(board, 3, 3));
		whiteBishop.capture(board, 3, 3);
	}

}
