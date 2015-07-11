package chess;

import chess.Board.Square;
import chess.ChessPiece.pieceType;

public class Cannon extends ChessPiece{

	public Cannon(Board.color color, pieceType cannon, int column, int row) {
		super(color, cannon, column, row);
	}
	
	@Override
	public pieceType getPiece() {
		return pieceType.CANNON;
	}
	
	/**
	 * Calls super.canMove() to check for bounds and to make sure there is no chess piece 
	 * at the given column and row. A cannon can only go vertically or horizontally, so
	 * if both the given column and row are different than the current row and column, then
	 * return false. Only one can change. Also runs isBlocked() to see if there are any
	 * chess pieces blocking its path.
	 * 
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 */
	@Override
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
		else if(row > rowPos){ //going up
			if(column != columnPos){
				return false;
			}
		}
		else if(row < rowPos){ //going down
			if(column != columnPos){
				return false;
			}
		}
		if(isBlocked(board, column, row) > 0){
			System.out.println("cannon: piece blocking path");
			return false;
		}
		return true;
	}
	

	/**
	 * First make sure the cannon can move to the destination. If it can, then move it, otherwise just print
	 * the statement saying it didn't move.
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
		else{
			System.out.println("cannon was not able to move");
		}
	}
	
	/**
	 * If it can, the cannon captures another chess piece and moves to that location.
	 * 
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 */
	public void capture(Board board, int column, int row){
		if(!canCapturePiece(board, column, row)){
			System.out.println("Cannon did not capture");
			return;
		}
		if(this.getColor() != board.getTurn()){
			System.out.println("not your turn");
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
		
		board.getSquare(this.getColumnPos(), this.getRowPos()).setPiecePresence(false);
		board.getSquare(this.getColumnPos(), this.getRowPos()).setChessPiece(null);
		Square squarePrey = board.getSquare(column, row);
		squarePrey.getChessPiece().setPresent(false);
		squarePrey.setChessPiece(this);
		squarePrey.setPiecePresence(true);
		this.setColumnPos(column);
		this.setRowPos(row);
		
		if(this.getColor() == Board.color.BLACK){
			board.setTurn(Board.color.WHITE);
		}
		else {
			board.setTurn(Board.color.BLACK);
		}

	}
	
	
	/**
	 * First makes sure it is either going vertically or horizontally. Then use isBlocked()
	 * to make sure that there is exactly one piece in between the cannon and the target.
	 * 
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 * @return true only if there is one piece in between. Else return false;
	 */
	@Override
	public boolean canCapturePiece(Board board, int column, int row){
		if(super.canCapture(board, column, row) == false){ //out of bounds or piece at [col][row] doesn't exist
			return false;
		}
		
		int piecesInBetween = 0;
		if(column == this.getColumnPos() || row == this.getRowPos()){ 
			//can only capture either going vertically or horizontally
			piecesInBetween = isBlocked(board, column, row);
			if(piecesInBetween == 0){
				return false;
			}
			if(piecesInBetween == 1){
				return true;
			}
			else{
				return false;
			}
		}
		return false;		
	}
	
	/**
	 * checks every square in between the cannon's current position and the destination.
	 * if any of those squares(excluding the destination) have a chess piece there, then
	 * increment count. This method counts the number of pieces in between the cannon
	 * and the destination. Cannons can only capture an enemy piece when there is exactly
	 * one piece in between them. 
	 * 
	 * @param board is the board the pieces are on
	 * @param destCol is the x coordinate of the destination
	 * @param destRow is the y coordinate of the destination
	 * @return count which is the number of pieces in between cannon and enemy.
	 */
	public int isBlocked(Board board, int destCol, int destRow){
		int currCol = this.getColumnPos();
		int currRow = this.getRowPos();
		int count = 0;
		
		Board.Square targetSquare = board.getSquare(destCol, destRow);
		boolean origPresence = targetSquare.getPiecePresence(); 
		targetSquare.setPiecePresence(false); //temporarily set to false, in order to exclude the destination square
		
		if(currCol == destCol){ //going vertically
			while(currRow != destRow){
				if(currRow > destRow){ //going down
					currRow--;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						count++;
					}
				}
				else{ //going up
					currRow++;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						count++;
					}
				}
			}
			targetSquare.setPiecePresence(origPresence);
			return count;
		}
		else if(currRow == destRow){ //going horizontally
			while(currCol != destCol){
				if(currCol > destCol){ //going left
					currCol--;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						count++;
					}
				}
				else{ //going right
					currCol++;
					if(board.getSquare(currCol, currRow).getPiecePresence() == true){
						count++;
					}
				}
			}
			targetSquare.setPiecePresence(origPresence);
			return count;
		}
		targetSquare.setPiecePresence(origPresence);
		return count;
	}

}
