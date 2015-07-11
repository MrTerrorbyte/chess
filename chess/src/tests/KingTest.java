package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.Board;
import chess.King;

public class KingTest {
	Board board = new Board(8,8);
	King whiteKing = (King) board.getWhitePieces().get(KING).get(0);
	King blackKing = (King) board.getBlackPieces().get(KING).get(0);
	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;

	@Test
	public void movementTest() {
		board.getWhitePieces().get(PAWN).get(3).move(board, 4, 3);
		board.getBlackPieces().get(PAWN).get(3).move(board, 4, 4);
		
		System.out.println(board.getSquare(4, 3).getChessPiece());
		
		whiteKing.move(board, 4, 1);
		blackKing.move(board, 4, 6);
		
		assertTrue(whiteKing.canMove(board, 4, 2));
		assertFalse(blackKing.canMove(board, -1000, 5));
	}
	@Test
	public void captureTest(){
		board.teleport(whiteKing, 4, 4);
		board.teleport(blackKing, 4, 3);
		assertTrue(whiteKing.canCapture(board, 4, 3));
		assertFalse(whiteKing.canCapture(board, 0, 0));
		board.teleport(blackKing, 5, 4);
		assertTrue(whiteKing.canCapture(board, 5, 4));
	}

}
