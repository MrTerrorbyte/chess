package chess;

import chess.ChessPiece.pieceType;

public class Knight extends ChessPiece {

	public Knight(Board.color color, pieceType knight, int column, int row) {
		super(color, knight, column, row);
	}
	/**
	 * Calls super.canMove() to check for bounds and to make sure there is no chess piece 
	 * at the given column and row. A knight has 8 different movements in the form of an L.
	 * 
	 * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 */
	public boolean canMove(Board board, int column, int row){
		if(super.canMove(board, column, row) == false)
			return false;
	
		int columnPos = this.getColumnPos();
		int rowPos = this.getRowPos();
		
		if(column == columnPos + 2 && row == rowPos + 1){
			return true;
		}
		if(column == columnPos + 2 && row == rowPos - 1){
			return true;
		}
		if(column == columnPos - 2 && row == rowPos + 1){
			return true;
		}
		if(column == columnPos - 2 && row == rowPos - 1){
			return true;
		}
		if(column == columnPos + 1 && row == rowPos + 2){
			return true;
		}
		if(column == columnPos + 1 && row == rowPos - 2){
			return true;
		}
		if(column == columnPos - 1 && row == rowPos + 2){
			return true;
		}
		if(column == columnPos - 1 && row == rowPos - 2){
			return true;
		}
		
		return false;
	}
	
	/**
	 * If the knight can move, then the method will call the generic move method.
	 * 
	 * @param board is the chess board that the pieces are on
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
		}
		else System.out.println("knight was not able to move");
	}
	
	/**
	 * Uses canMove() to see if the chess piece can move to where the target is. If it can,
	 * then the chess piece can capture the target.
	 * 
	 * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 * @return true if knight can capture, else return false
	 */
	@Override
	public boolean canCapturePiece(Board board, int column, int row){
		if(super.canCapture(board, column, row) == false) //out of bounds or piece at [col][row] doesn't exist
			return false;
		
		board.getSquare(column, row).setPiecePresence(false); //temporarily vacate that target spot to see if knight can move there
		if(this.canMove(board, column, row)){
			board.getSquare(column, row).setPiecePresence(true); //set it back
			return true;
		}
		board.getSquare(column, row).setPiecePresence(true); //set it back
		return false;
	}
	
	/**
	 * If it can, the chess piece uses the generic capture method to capture another chess piece.
	 * 
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 */
	public void capture(Board board, int column, int row){
		if(!canCapturePiece(board, column, row)){
			System.out.println("Bishop did not capture");
			return;
		}
		
		super.capture(board, column, row);

	}
	
	@Override
	public pieceType getPiece() {
		return pieceType.KNIGHT;
	}
}
