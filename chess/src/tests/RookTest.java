package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.Board;
import chess.Board.color;
import chess.Rook;

public class RookTest {
	
	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	Board board = new Board(8,8);
	
	/**
	 * tests rook's movement
	 */
	@Test
	public void movementTest() {
		board.setTurn(color.BLACK);
		Rook blackRook = (Rook) board.getBlackPieces().get(ROOK).get(1);
		assertFalse(blackRook.canMove(board, 7, 4));
		board.teleport(blackRook, 4, 4);
		assertTrue(blackRook.canMove(board, 7, 4));
		blackRook.move(board, 7, 4);
	
		board.setTurn(color.BLACK);
		assertTrue(blackRook.canMove(board, 7, 5));
		
		board.setTurn(color.BLACK);
		assertFalse(blackRook.canMove(board, -1000, 5)); //out of bounds
		
	}
	
	/**
	 * tests rook's capture
	 */
	@Test
	public void captureTest(){
		board.getWhitePieces().get(THREELEAPER).get(0).move(board, 7, 4);
		board.getBlackPieces().get(THREELEAPER).get(0).move(board, 7, 3);
		assertTrue(board.getWhitePieces().get(ROOK).get(1).canCapture(board, 7, 3));
		board.getWhitePieces().get(ROOK).get(1).capture(board, 7, 3);
	}
}
