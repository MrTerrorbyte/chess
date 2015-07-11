package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.Board;
import chess.Cannon;
import chess.Board.color;

public class CannonTest {

	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	Board board = new Board(8,8);
	/**
	 * tests cannon's movement
	 */
	@Test
	public void movementTest(){
		Cannon theCannon = (Cannon) board.getWhitePieces().get(CANNON).get(0);
		theCannon.move(board, 100, 100); //out of bounds
		board.teleport(board.getBlackPieces().get(CANNON).get(0), 0, 4);
		assertFalse(theCannon.canMove(board, 0, 5)); //path blocked
		theCannon.move(board, 0, 3);
		assertEquals(3, theCannon.getRowPos());
	}
	/**
	 * tests cannon's capture
	 */
	@Test
	public void captureTest(){
		Cannon theCannon = (Cannon) board.getWhitePieces().get(CANNON).get(0);
		theCannon.capture(board, 0, 7);
		assertEquals(7, theCannon.getRowPos());
		assertEquals(color.WHITE, board.getSquare(0, 7).getChessPiece().getColor());
		board.setTurn(Board.color.WHITE);
		theCannon.capture(board, 2, 7);
		String[][] boardSquares = board.drawBoard();
		for(int y = 0; y < boardSquares.length; y++){
			for(int x = 0; x < boardSquares[0].length; x++){
				System.out.print(boardSquares[x][y]);
			}
			System.out.println();
		}
	}

}
