package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.Board;
import chess.ChessPiece;

public class QueenTest {
	
	Board board = new Board(8,8);
	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	
	/**
	 * tests queen's movement
	 */
	@Test
	public void movementTest() {
		assertFalse(board.getWhitePieces().get(QUEEN).get(0).canMove(board, 1000, -100));
		board.getWhitePieces().get(PAWN).get(2).move(board, 3, 3);
		board.getBlackPieces().get(PAWN).get(3).move(board, 4, 4);
		board.getWhitePieces().get(PAWN).get(3).move(board, 4, 3);
		board.getBlackPieces().get(QUEEN).get(0).move(board, 6, 4);
		board.getWhitePieces().get(PAWN).get(4).move(board, 5, 2);
		board.getBlackPieces().get(QUEEN).get(0).move(board, 4, 2);
		assertTrue(board.getSquare(4, 2).getPiecePresence());
	}
	
	/**
	 * tests queen's capture
	 */
	@Test
	public void captureTest(){
		assertFalse(board.getWhitePieces().get(QUEEN).get(0).canCapturePiece(board, -1000, 1000));
		board.teleport(board.getWhitePieces().get(QUEEN).get(0), 4, 4);
		assertTrue(board.getWhitePieces().get(QUEEN).get(0).canCapturePiece(board, 4, 6));
		board.getWhitePieces().get(QUEEN).get(0).capture(board, 4, 6);
		assertEquals(6,board.getWhitePieces().get(QUEEN).get(0).getRowPos());
		ChessPiece.inCheck(board);
		assertTrue(board.getBlackInCheck());
	}
}
