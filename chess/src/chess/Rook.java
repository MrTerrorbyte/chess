package chess;

import chess.ChessPiece.pieceType;

public class Rook extends ChessPiece {

	public Rook(Board.color color, pieceType rook, int column, int row) {
		super(color, rook, column, row);
	}
	
	/**
	 * Calls super.canMove() to check for bounds and to make sure there is no chess piece 
	 * at the given column and row. A rook can only go vertically or horizontally, so
	 * if both the given column and row are different than the current row and column, then
	 * return false. Only one can change. Also runs isBlocked() to see if there are any
	 * chess pieces blocking its path.
	 * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 */
	public boolean canMove(Board board, int column, int row){
		if(super.canMove(board, column, row) == false){
			return false;
		}
		int rowPos = this.getRowPos();
		int columnPos = this.getColumnPos();
		
		if(column > columnPos){ //going right
			if(row != rowPos){
				return false;
			}
		}
		else if(column < columnPos){ //going left
			if(row != rowPos){
				return false;
			}
		}
		else if(row > rowPos){ //going down
			if(column != columnPos){
				return false;
			}
		}
		else if(row < rowPos){ //going up
			if(column != columnPos){
				return false;
			}
		}
		if(isBlocked(board, column, row) == true){
			return false;
		}
		return true;
	}
	/**
	 * checks every square in between the rook's current position and the destination.
	 * if any of those squares(excluding the destination) have a chess piece there, then
	 * return true, because there is a chess piece blocking its path. 
	 * @param board is the board that the pieces are on
	 * @param destCol is the x coordinate of the destination
	 * @param destRow is the y coordinate of the destination
	 * @return true if there is something blocking the rook's path, else return false
	 */
	public boolean isBlocked(Board board, int destCol, int destRow){
		int currCol = this.getColumnPos();
		int currRow = this.getRowPos();

		Board.Square targetSquare = board.getSquare(destCol, destRow);
		boolean origPresence = targetSquare.getPiecePresence(); 
		targetSquare.setPiecePresence(false); //temporarily set to false, in order to exclude the destination square
		
		if(currCol == destCol){ //going vertically
			while(currRow != destRow){ 
				if(currRow > destRow){ //going up
					currRow--;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						targetSquare.setPiecePresence(origPresence);
						return true;
					}
				}
				else{ //going down
					currRow++;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						targetSquare.setPiecePresence(origPresence);
						return true;	
					}
				}
				
			}
			targetSquare.setPiecePresence(origPresence);
			return false;
		}
		else if(currRow == destRow){ //going horizontally
			while(currCol != destCol){
				if(currCol > destCol){ //going left
					currCol--;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						targetSquare.setPiecePresence(origPresence);
						return true;
					}
				}
				else{ //going right
					currCol++;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						targetSquare.setPiecePresence(origPresence);
						return true;
					}
				}
			}
			targetSquare.setPiecePresence(origPresence);
			return false;
		}
		targetSquare.setPiecePresence(origPresence);
		return false;
	}
	/**
	 * First make sure the rook can move to the destination. If it can, then move it, otherwise just print
	 * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 */
	public void move(Board board, int column, int row){
		if(this.canMove(board, column, row)){
			super.move(board, column, row);
	
		}
		else{
			System.out.println("rook was not able to move");
		}
	}
	
	/**
	 * Uses canMove() to see if the chess piece can move to where the target is. If it can,
	 * then the chess piece can capture the target.
	 * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 * @return true if the rook can capture the prey, else return false
	 */
	@Override
	public boolean canCapturePiece(Board board, int column, int row){
		if(super.canCapture(board, column, row) == false){ //out of bounds or piece at [col][row] doesn't exist
			return false;
		}
		
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
	public void capture(Board board, int column, int row){
		if(!canCapturePiece(board, column, row)){
			System.out.println("Rook did not capture");
			return;
		}
		
		System.out.println(board.getSquare(column, row).getChessPiece());
		
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
		return pieceType.ROOK;
	}

}
