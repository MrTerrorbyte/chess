package tests;

import static org.junit.Assert.*;

import java.util.ListIterator;

import org.junit.Test;

import chess.Bishop;
import chess.Board;
import chess.Cannon;
import chess.ChessPiece;
import chess.Pawn;
import chess.Queen;
import chess.Rook;
import chess.Threeleaper;
import chess.Board.color;



public class ChessPieceTest {
	
	Board board = new Board(8,8);
	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	
	/**
	 * tests isBlocked() and canMove()
	 */
	@Test
	public void testIsBlocked(){
		assertTrue(((Rook)board.getWhitePieces().get(ROOK).get(0)).isBlocked(board, 0, 5));
		assertFalse(((Rook)board.getWhitePieces().get(ROOK).get(0)).canMove(board, 100, -100));
		assertTrue(((Bishop)board.getWhitePieces().get(BISHOP).get(1)).isBlocked(board, 4, 2));
		
		((Pawn)board.getWhitePieces().get(PAWN).get(0)).move(board, 1, 3);
		assertEquals(1, board.getWhitePieces().get(PAWN).get(0).getColumnPos());
		assertEquals(3, board.getWhitePieces().get(PAWN).get(0).getRowPos());
		assertTrue(((Rook)board.getWhitePieces().get(ROOK).get(0)).isBlocked(board, 0, 5));
		
		board.teleport(board.getWhitePieces().get(QUEEN).get(0), 0, 2);
		assertEquals(1,((Cannon)board.getWhitePieces().get(CANNON).get(0)).isBlocked(board, 0, 4));
		
		assertFalse(((Threeleaper)board.getWhitePieces().get(THREELEAPER).get(0)).canMove(board, 7, 5));
		board.teleport(board.getBlackPieces().get(THREELEAPER).get(0), 0, 5);
		assertFalse(((Threeleaper)board.getWhitePieces().get(THREELEAPER).get(0)).canMove(board, 5, 5));
		
		assertFalse(((Queen)board.getWhitePieces().get(QUEEN).get(0)).canMove(board, 2, 6));
		((Queen)board.getWhitePieces().get(QUEEN).get(0)).move(board, 6, 2);
	}
	
	/**
	 * tests the inCheck() method.
	 * Teleport some pieces to simulate different "check" scenario.
	 */
	@Test
	public void testCheck(){
		ChessPiece.inCheck(board);
		assertFalse(board.getBlackInCheck());
		assertFalse(board.getWhiteInCheck());
		
		board.teleport(board.getBlackPieces().get(KING).get(0),4,4);
		board.teleport(board.getWhitePieces().get(QUEEN).get(0), 3, 3);
		board.teleport(board.getWhitePieces().get(PAWN).get(0), 1, 3);
		ChessPiece.inCheck(board);
		assertTrue(board.getBlackInCheck());
		
		board = new Board(8,8);
		board.teleport(board.getBlackPieces().get(KING).get(0), 4, 4);
		board.teleport(board.getWhitePieces().get(THREELEAPER).get(0), 1, 4);
		ChessPiece.inCheck(board);
		assertTrue(board.getBlackInCheck());
		board.teleport(board.getBlackPieces().get(PAWN).get(2), 2, 4);
		ChessPiece.inCheck(board);
		assertTrue(board.getBlackInCheck());
		
		board.teleport(board.getBlackPieces().get(PAWN).get(2), 3, 6);
		board.teleport(board.getWhitePieces().get(CANNON).get(0), 0, 4);
		ChessPiece.inCheck(board);
		assertTrue(board.getBlackInCheck());
	}
	
	
	/**
	 * tests checkmate
	 */
	@Test
	public void testCheckmate(){
		board.getWhitePieces().get(CANNON).get(0).move(board, 0, 2);
		board.getBlackPieces().get(PAWN).get(0).move(board, 1, 5);
		board.getWhitePieces().get(CANNON).get(0).move(board, 4, 2);
		ChessPiece.inCheck(board);
		ChessPiece.checkmate(board, Board.color.BLACK);
		assertTrue(board.getBlackInCheckmate());
	}
	
	
	
}