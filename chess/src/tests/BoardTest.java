package tests;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.ListIterator;

import org.junit.Test;

import chess.Board;
import chess.ChessGraphics;
import chess.ChessPiece;
import chess.ChessPiece.pieceType;



public class BoardTest {
	public static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	Board chessBoard = new Board(8,8);
	/**
	 * makes sure the pieces are all there.
	 */
	@Test
	public void initTest() {
		for(int type = 0; type < 8; type++){
			Iterator<ChessPiece> iter = chessBoard.getWhitePieces().get(type).iterator();
			while(iter.hasNext()){
				ChessPiece curr = (ChessPiece) iter.next();
				assertTrue(curr.getPresent());
				if(type == PAWN){
					assertTrue(curr.getPiece() == pieceType.PAWN);
				}
				if(type == ROOK){
					assertTrue(curr.getPiece() == pieceType.ROOK);
				}
				if(type == BISHOP){
					assertTrue(curr.getPiece() == pieceType.BISHOP);					
				}
				if(type == KNIGHT){
					assertTrue(curr.getPiece() == pieceType.KNIGHT);
				}
				if(type == KING){
					assertTrue(curr.getPiece() == pieceType.KING);
				}
				if(type == CANNON){
					assertTrue(curr.getPiece() == pieceType.CANNON);
				}
				if(type == THREELEAPER){
					assertTrue(curr.getPiece() == pieceType.THREELEAPER);
				}
				if(type == QUEEN){
					assertTrue(curr.getPiece() == pieceType.QUEEN);
				}
			}
		}
	}
	/**
	 * tests undo
	 */
	@Test
	public void testUndo(){
		ChessGraphics window = new ChessGraphics();
		window.setBoard(chessBoard);
		window.getBoard().getWhitePieces().get(PAWN).get(0).move(chessBoard, 1, 3);
		window.getBoard().getBlackPieces().get(PAWN).get(0).move(chessBoard, 1, 4);
		window.setBoard(window.undoMove());
		assertEquals(6, window.getBoard().getBlackPieces().get(PAWN).get(0).getRowPos());
		assertEquals(Board.color.BLACK, window.getBoard().getTurn());
		window.setBoard(window.undoMove());
		assertEquals(1, window.getBoard().getWhitePieces().get(PAWN).get(0).getRowPos());
		assertEquals(Board.color.WHITE, window.getBoard().getTurn());
	}
	
	
}
