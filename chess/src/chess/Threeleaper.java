package chess;

import chess.ChessPiece.pieceType;

public class Threeleaper extends ChessPiece{

	public Threeleaper(Board.color color, pieceType threeleaper, int column, int row) {
		super(color, threeleaper, column, row);
	}

	/**
	 * Threeleaper can only move 3 spaces either horizontally or vertically. It cannot be blocked.
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 * @return true if direction is legal. Return false otherwise.
	 */
	public boolean canMove(Board board, int column, int row){
		if(super.canMove(board, column, row) == false){
			return false;
		}
		int rowPos = this.getRowPos();
		int columnPos = this.getColumnPos();
		
		if(row == rowPos){ //going horizontally
			if(column == columnPos + 3){
				return true;
			}
			else if(column == columnPos - 3){
				return true;
			}
		}
		else if(column == columnPos){ //going vertically
			if(row == rowPos + 3){
				return true;
			}
			else if(row == rowPos - 3){
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Move the chess piece if it can.
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
		else{
			System.out.println("threeleaper was not able to move");
		}
	}
	
	@Override
	public pieceType getPiece() {
		return pieceType.THREELEAPER;
	}

	/**
	 * Uses canMove() to see if the chess piece can move to where the target is. If it can,
	 * then the chess piece can capture the target.
	 * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 * @return true if leaper can capture piece, else return false
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
	  * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 */
	@Override
	public void capture(Board board, int column, int row){
		if(!canCapturePiece(board, column, row)){
			System.out.println("Threeleaper did not capture");
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
		
		super.capture(board, column, row);

	}

}
