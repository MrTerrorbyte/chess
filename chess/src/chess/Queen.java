package chess;

import chess.ChessPiece.pieceType;

public class Queen extends ChessPiece {
	public Queen(Board.color color, pieceType queen, int column, int row){
		super(color, queen, column, row);
	}
	/**
	 * Calls super.move() to check for bounds and to make sure there is no chess piece at the given coords.
	 * Queens can move diagonally or vertically/horizontally. This method checks which direction its moving
	 * and returns false if its direction is invalid.
	 * Also calls isBlocked() to make sure there is no chess piece blocking its path.
	 * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 * @return true if the queen can move to the given coordinates, else return false
	 */
	@Override
	public boolean canMove(Board board, int column, int row){
		if(super.canMove(board, column, row) == false)
            return false;

        int columnPos = this.getColumnPos();
        int rowPos = this.getRowPos();
        int diff = 0;
        
        if(column > columnPos || column < columnPos){ //going left or right
        	if(row != rowPos){ //going diagonally left or right
        		diff = Math.abs(column - columnPos);
        		if(Math.abs(row - rowPos) != diff){
        			return false;
        		}
        	}
		}
		else if(row > rowPos || row < rowPos){ //going down or up
			if(column != columnPos){
				diff = Math.abs(row - rowPos);
				if(Math.abs(columnPos - rowPos) != diff){
					return false;
				}
			}
		}
        
        board.getSquare(columnPos, rowPos).setPiecePresence(false); //temporarily set to false in order to run isBlocked().
		if(isBlocked(board, columnPos, rowPos, column, row) == true){ //there is a piece blocking
			board.getSquare(columnPos, rowPos).setPiecePresence(true); //set it back
			System.out.println("queen: piece blocking path");
			return false;
		}
		board.getSquare(columnPos, rowPos).setPiecePresence(true); //set it back
		return true;
	}
	
	/**
	 * checks every square in between the queen's current position and the destination.
	 * if any of those squares(excluding the destination) have a chess piece there, then
	 * return true, because there is a chess piece blocking its path. 
	 * @param board is the board that the pieces are on
	 * @param destCol is the x coordinate of the destination
	 * @param destRow is the y coordinate of the destination
	 * @return true if there is a piece blocking the queen's path, else return false
	 */
	public boolean isBlocked(Board board, int currCol, int currRow, int destCol, int destRow){
		if(currCol == destCol && currRow == destRow){
			return false;
		}
		if(board.getSquare(currCol, currRow).getPiecePresence() == true){
			return true;
		}
		if(destCol > currCol){ //going right
			if(destRow != currRow){ //diagonal
				if(destRow > currRow) //going up right
					return isBlocked(board, (char)(currCol+1), currRow+1, destCol, destRow);
				else //going down right
					return isBlocked(board, (char)(currCol+1), currRow-1, destCol, destRow);
			}
			//else going straight right
			return isBlocked(board, (char)(currCol+1), currRow, destCol, destRow);
		}
		else if(destRow < currRow && destCol == currCol){ //going down
			return isBlocked(board, currCol, currRow-1, destCol, destRow);
		}
		else if(destRow > currRow && destCol == currCol){ //going up
			return isBlocked(board, currCol, currRow+1, destCol, destRow);
		}
		else if(destCol < currCol){ //going left
			if(destRow != currRow){ //diagonal
				if(destRow > currRow) //going up left
					return isBlocked(board, (char)(currCol-1), currRow+1, destCol, destRow);
				else //going down left
					return isBlocked(board, (char)(currCol-1), currRow-1, destCol, destRow);
			}
			//else going straight left
			return isBlocked(board, (char)(currCol-1), currRow, destCol, destRow);
		}
		
		else //going up
			return isBlocked(board, currCol, currRow+1, destCol, destRow);
	}
		
	/**
	 * First make sure the queen can move to the destination. If it can, then move it, otherwise just print
	 * the statement saying it didn't move.
	 * @param board is the board that the pieces are on
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
		else System.out.println("queen was not able to move");
	}
	
	/**
	 * Uses canMove() to see if the chess piece can move to where the target is. If it can,
	 * then the chess piece can capture the target.
	 * @param board is the board that the pieces are on
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 * @return true if the queen can capture the prey, else return false
	 */
	@Override
	public boolean canCapturePiece(Board board, int column, int row){
		if(super.canCapture(board, column, row) == false) //out of bounds or piece at [col][row] doesn't exist
			return false;
		
		board.getSquare(column, row).setPiecePresence(false); //temporarily vacate that target spot to see if queen can move there
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
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 */
	public void capture(Board board, int column, int row){
		if(!canCapturePiece(board, column, row)){
			System.out.println("Queen did not capture");
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
		return pieceType.QUEEN;
	}
}
