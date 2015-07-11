package chess;

import chess.ChessPiece.pieceType;

public class Bishop extends ChessPiece {
	public Bishop(Board.color color, pieceType bishop, int column, int row){
		super(color, bishop, column, row);
	}
	
	/**
	 * Calls super.move() to check for bounds and to make sure there is no chess piece at the given coords.
	 * Bishop can only move in diagonals, so the difference between row and column has to be the same.
	 * Also calls isBlocked() to make sure there is no chess piece blocking its path.
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 * @return true if bishop can move to given coords, else return false
	 */
	public boolean canMove(Board board, int column, int row){
		if(super.canMove(board, column, row) == false)
			return false;
		
		int columnPos = this.getColumnPos();
		int rowPos = this.getRowPos();
		int diff = 0;
		
		if(column > columnPos || column < columnPos){ //going right
			diff = Math.abs(column - columnPos);
			if(Math.abs(row - rowPos) != diff){
				return false;
			}
		}
		else { //going straight which is illegal
			return false;
		}
		if(isBlocked(board, column, row) == true){ //there is a piece blocking
			System.out.println("bishop: piece blocking path");
			return false;
		}
		return true;
	}
	
	/**
	 * Checks every square in its path. If there is a chess piece on any of those squares, excluding the 
	 * final square, then return true, else return false.
	 * @param board is the chess board that the pieces are on
	 * @param destCol is the x coordinate of the destination
	 * @param destRow is the y coordinate of the destination
	 * @return true if there is a chess piece blocking the bishop's path, else return false
	 */
	public boolean isBlocked(Board board, int destCol, int destRow){
		int currCol = this.getColumnPos();
		int currRow = this.getRowPos();

		Board.Square targetSquare = board.getSquare(destCol, destRow);
		boolean origPresence = targetSquare.getPiecePresence(); 
		targetSquare.setPiecePresence(false); //temporarily set to false, in order to exclude the destination square
		
		if(destCol > currCol){ //going to the right
			if(destRow > currRow){ //going right and down
				while(destRow != currRow && destCol != currCol){
					currRow++;
					currCol++;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						targetSquare.setPiecePresence(origPresence);
						return true;
					}
				}
			}
			else{ //going right and up
				while(destRow != currRow && destCol != currCol){
					currRow--;
					currCol++;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						targetSquare.setPiecePresence(origPresence);
						return true;
					}
				}
			}
		}
		if(destCol < currCol){ //going to the left
			if(destRow > currRow){ //going left and down
				while(destRow != currRow && destCol != currCol){
					currRow++;
					currCol--;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						targetSquare.setPiecePresence(origPresence);
						return true;
					}
				}
			}
			else{ //going left and up
				while(destRow != currRow && destCol != currCol){
					currRow--;
					currCol--;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						targetSquare.setPiecePresence(origPresence);
						return true;
					}
				}
			}
		}
		targetSquare.setPiecePresence(origPresence);
		return false;
	}
	
	/**
	 * First make sure the bishop can move to the destination. If it can, then move it, otherwise just print
	 * the statement saying it didn't move.
	 * @param board is the chess board that the chess pieces are on
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
		else System.out.println("bishop was not able to move");
	}
	
	/**
	 * Uses canMove() to see if the chess piece can move to where the target is. If it can,
	 * then the chess piece can capture the target.
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 * @return true if the bishop can capture the prey, else return false
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
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 */
	public void capture(Board board, int column, int row){
		if(!canCapturePiece(board, column, row)){
			System.out.println("Bishop did not capture");
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
		return pieceType.BISHOP;
	}
}
