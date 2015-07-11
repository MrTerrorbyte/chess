package chess;

import chess.Board.Square;
import chess.ChessPiece.pieceType;

/**
 * @author jordan
 *
 */
public class Pawn extends ChessPiece {
	private boolean firstTurn;
	
	/**
	 * Calls the general constructor in ChessPiece and sets firstTurn to true, so it can
	 * move 2 squares instead of 1.
	 * 
	 * @param color is either white or black
	 * @param pawn is the type of chess piece
	 * @param column is the initial x coordinate of the piece
	 * @param row is the initial y coordinate of the piece
	 */
	public Pawn(Board.color color, pieceType pawn, int column, int row) {
		super(color, pawn, column, row);
		firstTurn = true;
	}
	
	/**
	 * checks if the pawn can move to the given column and row.
	 * the method is separated into two sections because the pawn's movement depends on its color.
	 * it also depends on whether it is its first turn. 
	 * 
	 * @param board is the chess board that the chess pieces are on.
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 */
	public boolean canMove(Board board, int column, int row){
		if(super.canMove(board, column, row) == false){
			return false;
		}
		
		int currCol = this.getColumnPos();
		int currRow = this.getRowPos();
		
		if(column != currCol){
			return false;
		}
		if(this.getColor() == Board.color.BLACK){
			if(row > currRow){
				return false;
			}
			if(board.getSquare(column, currRow-1).getPiecePresence() == true){
				return false;
			}
			if(this.firstTurn == false && row <= currRow-2){ //only on its first turn can it move 2 spaces
				return false;
			}
			if(this.firstTurn == true && row <= currRow-3){ //even on its first turn, it cannot move more than 2 spaces
				return false;
			}
		}
		else if(this.getColor() == Board.color.WHITE){
			if(row < currRow){
				return false;
			}
			if(board.getSquare(column, currRow+1).getPiecePresence() == true){
				return false;
			}
			if(this.firstTurn == false && row >= currRow+2){
				return false;
			}
			if(this.firstTurn == true && row >= currRow+3){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * first make sure if the pawn can move to the given column and row.
	 * then move the pawn, and set its first turn to false.
	 * if canMove failed, then print out the statement.
	 * 
	 * @param board is the chess board that the chess pieces are on.
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 */
	public void move(Board board, int column, int row){
		if(this.canMove(board, column, row)){
			
			String[][] array = board.drawBoard();
			ChessGraphics.getStack().push(array);
			
			for(int x = 0; x < array.length; x++){
				for(int y = 0; y < array[0].length; y++){
					System.out.print(array[y][x]);
				}
				System.out.println();
			}
			System.out.println();
			
			super.move(board, column, row);
			this.firstTurn = false;
		}
		else System.out.println("pawn was not able to move");
	}
	
	/**
	 * Checks to make sure that the prey is diagonal to the pawn.
	 * 
	 * @param board is the chess board that the chess pieces are on.
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 * @return true if the pawn can capture the piece, else return false
	 */
	@Override
	public boolean canCapturePiece(Board board, int column, int row){
		if(super.canCapture(board, column, row) == false) //out of bounds or piece at [col][row] doesn't exist
			return false;
		
		if(!(column == this.getColumnPos() - 1 || column == this.getColumnPos() + 1)){
			return false;
		}
		if(this.getColor() == Board.color.WHITE){
			if(!(row == this.getRowPos() + 1 && (column == this.getColumnPos() + 1 || column == this.getColumnPos() - 1))){
				return false;
			}
		}
		else if(this.getColor() == Board.color.BLACK){
			if(!(row == this.getRowPos() - 1 && (column == this.getColumnPos() + 1 || column == this.getColumnPos() - 1))){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * If it can, the chess piece uses the generic capture method to capture another chess piece.
	 * 
	 * @param board is the chess board that the chess pieces are on.
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 */
	public void capture(Board board, int column, int row){
		if(!canCapturePiece(board, column, row)){
			System.out.println("Pawn did not capture");
			return;
		}
		
		
		String[][] array = board.drawBoard();
		ChessGraphics.getStack().push(array);
		
		for(int x = 0; x < array.length; x++){
			for(int y = 0; y < array[0].length; y++){
				System.out.print(array[y][x]);
			}
			System.out.println();
		}
		System.out.println();
		
		ChessPiece prey = board.getSquare(column, row).getChessPiece();
		prey.setPresent(false);
		Square squareEat = board.getSquare(column, row);
		squareEat.setChessPiece(null);
		squareEat.setPiecePresence(false);
		
		super.move(board, column, row);

	}
	
	@Override
	public pieceType getPiece() {
		return pieceType.PAWN;
	}
	
}
