package chess;

import chess.ChessPiece.pieceType;

public class King extends ChessPiece {
	public King(Board.color color, pieceType king, int column, int row){
		super(color, king, column, row);
	}
	
	/**
	 * Tests to see if the king can move to the given coordinates
	 * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 * @return true if the king can move to the destination, else return false
	 */
	public boolean canMove(Board board, int column, int row){
		if(super.canMove(board, column, row) == false)
			return false;
		
		int rowPos = this.getRowPos();
		int columnPos = this.getColumnPos();
		
		if(column == columnPos){
			if(row == rowPos + 1){
					return true;
			}
			else if(row == rowPos - 1){
					return true;
			}
		}
		else if(row == rowPos){
			if(column == columnPos + 1){
					return true;
			}
			else if(column == columnPos -1){
					return true;
			}
		}
		return false;
	}
	
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
		else System.out.println("King was not able to move");
	}
	
	/**
	 * Uses canMove() to see if the chess piece can move to where the target is. If it can,
	 * then the chess piece can capture the target.
	 * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 * @return true if the king can capture the prey, else return false
	 */
	@Override
	public boolean canCapturePiece(Board board, int column, int row){
		if(super.canCapture(board, column, row) == false) //out of bounds or piece at [col][row] doesn't exist
			return false;
		
		board.getSquare(column, row).setPiecePresence(false); //temporarily vacate that target spot to see if king can move there
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
	public void capture(Board board, int column, int row){
		if(!canCapturePiece(board, column, row)){
			System.out.println("King did not capture");
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
	
	@Override
	public pieceType getPiece() {
		return pieceType.KING;
	}
}
