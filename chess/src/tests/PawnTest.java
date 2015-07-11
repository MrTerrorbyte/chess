package tests;

import static org.junit.Assert.*;

import java.util.ListIterator;

import org.junit.Test;

import chess.Board;
import chess.ChessPiece;
import chess.Pawn;

public class PawnTest {
	
	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	Board board = new Board(8,8);
	/**
	 * tests movement for pawns
	 */
	@Test
	public void movementTest() {
		ListIterator<ChessPiece> iterWhite = board.getWhitePieces().get(PAWN).listIterator();
		ListIterator<ChessPiece> iterBlack = board.getBlackPieces().get(PAWN).listIterator();
		
		while(iterWhite.hasNext() && iterBlack.hasNext()){
			Pawn whitePawn = (Pawn) iterWhite.next();
			Pawn blackPawn = (Pawn) iterBlack.next();
			
			assertFalse(blackPawn.canMove(board, blackPawn.getColumnPos(), blackPawn.getRowPos()-2)); //wrong turn
			assertTrue(whitePawn.canMove(board, whitePawn.getColumnPos(), whitePawn.getRowPos()+2));
			whitePawn.move(board, whitePawn.getColumnPos(), whitePawn.getRowPos()+2);
			blackPawn.move(board, blackPawn.getColumnPos(), blackPawn.getRowPos()-2);
			assertFalse(blackPawn.canMove(board, blackPawn.getColumnPos(), blackPawn.getRowPos()+1)); //move backwards onto piece
			assertFalse(blackPawn.canMove(board, -1000, -10)); //out of bounds
			
		}
	}
	
	/**
	 * tests capture for pawns
	 */
	@Test
	public void captureTest(){
		
		board.teleport(board.getSquare(1, 1).getChessPiece(), 4, 4);
		board.teleport(board.getBlackPieces().get(PAWN).get(2), 3, 5);
		assertFalse(board.getBlackPieces().get(PAWN).get(0).canCapturePiece(board, -1000, 1000)); //out of bounds
		assertFalse(board.getBlackPieces().get(PAWN).get(2).canCapturePiece(board, 4, 4)); //wrong turn
		
		assertTrue(board.getSquare(4, 4).getChessPiece().canCapturePiece(board, 3, 5));
	}

}
