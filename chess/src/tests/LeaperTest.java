package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.Board;
import chess.Board.color;
import chess.Threeleaper;

public class LeaperTest {
	
	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	Board board = new Board(8,8);
	
	@Test
	public void movementTest() {
		assertFalse(board.getBlackPieces().get(THREELEAPER).get(0).canMove(board, -1000, -1000));
		assertFalse(board.getBlackPieces().get(THREELEAPER).get(0).canMove(board, 1000, 0));
		assertFalse(board.getBlackPieces().get(THREELEAPER).get(0).canMove(board, 7, 5)); //must move 3 spaces
		
		board.getWhitePieces().get(THREELEAPER).get(0).move(board, 7, 4);
		assertTrue(((Threeleaper)board.getBlackPieces().get(THREELEAPER).get(0)).canMove(board, 7, 3));
		board.getBlackPieces().get(THREELEAPER).get(0).move(board, 7, 3);
		assertEquals(3, board.getBlackPieces().get(THREELEAPER).get(0).getRowPos());
	}
		
	@Test
	public void captureTest(){
		Threeleaper leaper = (Threeleaper) board.getWhitePieces().get(THREELEAPER).get(0);
		leaper.move(board, 7, 4);
		board.getBlackPieces().get(PAWN).get(0).move(board, 1, 5);
		assertTrue(leaper.canCapturePiece(board, 7, 7));
		leaper.capture(board, 7, 7);
		assertEquals(7, leaper.getRowPos());
	}

}
