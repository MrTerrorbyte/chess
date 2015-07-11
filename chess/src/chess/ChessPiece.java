package chess;

import java.util.ListIterator;

import chess.Board.Square;


public abstract class ChessPiece {
	public static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	private int rowPos;
	private int columnPos;
	private Board.color color;
	private boolean isPresent;
	private pieceType piece;
	
	public enum pieceType{PAWN, KNIGHT, ROOK, BISHOP,CANNON, THREELEAPER, QUEEN, KING};
	
	public abstract pieceType getPiece();
	public abstract boolean canCapturePiece(Board board, int column, int row);
	
	public void setPiece(pieceType piece) {
		this.piece = piece;
	}
	public int getRowPos() {
		return rowPos;
	}
	public void setRowPos(int rowPos) {
		this.rowPos = rowPos;
	}
	public int getColumnPos() {
		return columnPos;
	}
	public void setColumnPos(int column) {
		columnPos = column;
	}
	public boolean getPresent() {
		return isPresent;
	}
	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}
	public Board.color getColor() {
		return color;
	}
	public void setColor(Board.color color) {
		this.color = color;
	}

	public ChessPiece(Board.color color, pieceType piece, int column, int row){
		this.color = color;
		this.piece = piece;
		this.columnPos = column;
		this.rowPos = row;
		this.isPresent = true;
	}
	
	public ChessPiece(){
		this.columnPos = 0;
		this.rowPos = 0;
	}
	/**
	 * checks to make sure the given coords are within the bounds of the board, and that the square is empty.
	 * 
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 * @return true if coords are in bounds and piece is present
	 */
	public boolean canMove(Board board, int column, int row){
		if(column > board.getColumnSize()-1 || column < 0 || row > board.getRowSize()-1 || row < 0 || this.isPresent == false){
			return false;
		}
		
		if(column == this.getColumnPos() && row == this.getRowPos()){ //can't move to itself
			return false;
		}
		
		if(this.getPresent() == false){ //if the mover isn't on the board, then it can't move.
			return false;
		}
		
		if(board.getTurn() != this.getColor()){
			return false;
		}
		
		Square targetSquare = board.getSquare(column, row);
		
		//if there's all ready a piece on that square, return false
		if(targetSquare.getPiecePresence() == true){
			return false;
		}
		return true;
	}
	
	/**
	 * get the square that the chess piece is standing on and get rid of the
	 * piece on the square. 
	 * get the square with the param column and row, and set its presence to true
	 * 
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 */
	public void move(Board board, int column, int row){
		if(this.color == Board.color.BLACK){
			board.setTurn(Board.color.WHITE);
		}
		else {
			board.setTurn(Board.color.BLACK);
		}
		
		Board.Square origSquare = board.getSquare(this.getColumnPos(), this.getRowPos());
		this.setColumnPos(column);
		this.setRowPos(row);
		origSquare.setPiecePresence(false);
		origSquare.setChessPiece(null);
		Board.Square newSquare = board.getSquare(column, row);
		newSquare.setChessPiece(this);
		newSquare.setPiecePresence(true);
	}

	/**
	 * checks for bounds, makes sure there is a piece to capture, and makes sure
	 * chess pieces of the same color don't capture each other.
	 * 
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 * @return false if coords are out of bounds, turn is wrong, nothing to capture, or capturing own team. Else return true.
	 */
	public boolean canCapture(Board board, int column, int row){
		if(column > board.getColumnSize()-1 || column < 0 || row > board.getRowSize()-1 || row < 0){
			return false;
		}

		if(board.getTurn() != this.getColor()){
			return false;
		}
		
		if(column == this.getColumnPos() && row == this.getRowPos()){ //can't capture itself
			return false;
		}
		
		if(this.isPresent == false){ //if the predator isn't on the board, then it can't do anything.
			return false;
		}
		
		if(board.getSquare(column, row).getPiecePresence() == false){ //can't capture what's not there
			return false;
		}
		if(board.getSquare(column, row).getChessPiece().getColor() == this.color){ //make sure black doesn't capture black, and white capture white
			return false;
		}
		return true;
	}
	
	
	/**
	 * Captures piece by setting the prey's presence to false, and moving the predator to the
	 * prey's coords.
	 * @param board is the chess board that the pieces are on
	 * @param column is the x coordinate of the prey
	 * @param row is the y coordinate of the prey
	 */
	public void capture(Board board, int column, int row){
		if(this.color != board.getTurn()){
			return;
		}
		
		ChessPiece prey = board.getSquare(column, row).getChessPiece();
		prey.isPresent = false;
		Square squareEat = board.getSquare(column, row);
		squareEat.setChessPiece(null);
		squareEat.setPiecePresence(false);
		if(this.color == Board.color.BLACK){
			board.setTurn(Board.color.WHITE);
		}
		else {
			board.setTurn(Board.color.BLACK);
		}
		board.teleport(this, column, row);
	}
	
	/**
	 * For each chess piece, run canCapture on the enemy king.
	 * @param board is the chess board that the chess pieces are on.
	 */
	public static void inCheck(Board board){
		Board.color turn = board.getTurn();
		board.setBlackInCheck(false);
		board.setWhiteInCheck(false);
		if(board.getBlackPieces().get(KING).get(0).getPresent() == false){
			return;
		}
		else if(board.getWhitePieces().get(KING).get(0).getPresent() == false){
			return;
		}
		
		int blackRow = board.getBlackPieces().get(KING).get(0).getRowPos();
		int blackCol = board.getBlackPieces().get(KING).get(0).getColumnPos();
		int whiteRow = board.getWhitePieces().get(KING).get(0).getRowPos();
		int whiteCol = board.getWhitePieces().get(KING).get(0).getColumnPos();
		
		for(int type = 0; type < 8; type++){
			ListIterator<ChessPiece> whiteIter = board.getWhitePieces().get(type).listIterator();
			ListIterator<ChessPiece> blackIter = board.getBlackPieces().get(type).listIterator();
			while(whiteIter.hasNext()){
				ChessPiece whitePiece = whiteIter.next();
				board.setTurn(Board.color.WHITE);
				if(whitePiece.canCapturePiece(board, blackCol, blackRow)){
					board.setBlackInCheck(true);
				}
			}
			while(blackIter.hasNext()){
				ChessPiece blackPiece = blackIter.next();
				board.setTurn(Board.color.BLACK);
				if(blackPiece.canCapturePiece(board, whiteCol, whiteRow)){
					board.setTurn(Board.color.BLACK);
					board.setWhiteInCheck(true);
					board.setTurn(turn);
				}
			}
		}
		board.setTurn(turn);
	}
	
	/**
	 * Have every chesspiece go through every legal move to see if there is a checkmate
	 * @param board is the board that the pieces are on
	 * @param color is the team that is being tested.
	 */
	public static void checkmate(Board board, Board.color color){
		Board.color turn = board.getTurn();
		if(color == Board.color.WHITE){
			board.setWhiteInCheckmate(false);
			if(board.getWhiteInCheck() == false){
				board.setWhiteInCheckmate(false);
				return;
			}
			else{
				for(int type = 0; type < 8; type++){
					ListIterator<ChessPiece> whiteIter = board.getWhitePieces().get(type).listIterator();
					while(whiteIter.hasNext()){
						ChessPiece whitePiece = whiteIter.next();
						if(whitePiece.isPresent == true){
							board.setTurn(Board.color.WHITE);
							boolean mate = tryAllMoves(board, whitePiece);
							board.setTurn(turn);
							if(mate == false){
								board.setWhiteInCheckmate(false);
								return;
							}
							
							//king is still in check so now try capturing
							board.setTurn(Board.color.WHITE);
							mate = tryAllCaptures(board, whitePiece);
							board.setTurn(turn);
							if(mate == false){
								board.setWhiteInCheckmate(false);
								return;
							}
						}
					}
				}
				board.setWhiteInCheckmate(true);
			}
		}
		else{ //color is black
			board.setBlackInCheckmate(false);
			if(board.getBlackInCheck() == false){
				board.setBlackInCheckmate(false);
				return;
			}
			else{
				for(int type = 0; type < 8; type++){
					ListIterator<ChessPiece> blackIter = board.getBlackPieces().get(type).listIterator();
					while(blackIter.hasNext()){
						ChessPiece blackPiece = blackIter.next();
						if(blackPiece.isPresent == true){
							board.setTurn(Board.color.BLACK);
							boolean mate = tryAllMoves(board, blackPiece);
							board.setTurn(turn);
							if(mate == false){
								board.setBlackInCheckmate(false);
								return;
							} 
							
							//king is still in check, so now try capturing
							board.setTurn(Board.color.BLACK);
							mate = tryAllCaptures(board, blackPiece);
							board.setTurn(turn);
							if(mate == false){
								board.setBlackInCheckmate(false);
								return;
							}
						}
					}
				}
				board.setBlackInCheckmate(true);
			}
		}
	}
	
	/**
	 * 
	 * @param board is the board that the piece is on
	 * @param piece is the chess piece that is going through all of its moves
	 * @return true if after all the piece's moves, the piece's king is still in check. Returns false if a move brought the king out of check.
	 */
	public static boolean tryAllMoves(Board board, ChessPiece piece){
		if(piece.color == Board.color.WHITE){
			for(int x = 0; x < board.getColumnSize(); x++){
				for(int y = 0; y < board.getRowSize(); y++){
					if(piece.canMove(board, x, y)){
						int origX = piece.getColumnPos();
						int origY = piece.getRowPos();
						board.teleport(piece, x, y);
						inCheck(board);
						if(board.getWhiteInCheck() == true){ //move didn't bring king out of check, so move it back
							board.teleport(piece, origX, origY);
						}
						else{ //move brought king out of check, so not checkmate
							board.teleport(piece, origX, origY);
							return false;
						}
					}
				}
			}
		}
		else{ //color is black
			for(int x = 0; x < board.getColumnSize(); x++){
				for(int y = 0; y < board.getRowSize(); y++){
					if(piece.canMove(board, x, y)){
						int origX = piece.getColumnPos();
						int origY = piece.getRowPos();
						board.teleport(piece, x, y);
						inCheck(board);
						if(board.getBlackInCheck() == true){ //move didn't bring king out of check, so move it back
							board.teleport(piece, origX, origY);
						}
						else{
							board.teleport(piece, origX, origY);
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Using the given piece, try to capture each enemy piece to see if this
	 * prevents a check.
	 * 
	 * @param board is the board the pieces are on
	 * @param piece This is the chess piece used to try and capture every enemy
	 * @return false if the king is not in check anymore, else return true
	 */
	public static boolean tryAllCaptures(Board board, ChessPiece piece){
		Board.color turn = board.getTurn();
		int origX = piece.getColumnPos();
		int origY = piece.getRowPos();
		
		if(piece.getColor() == Board.color.WHITE){
			for(int type = 0; type < 8; type++){
				ListIterator<ChessPiece> blackIter = board.getBlackPieces().get(type).listIterator();
				while(blackIter.hasNext()){
					ChessPiece blackPiece = blackIter.next();
					if(blackPiece.getPresent() == true){
						if(piece.canCapturePiece(board, blackPiece.getColumnPos(), blackPiece.getRowPos())){
							board.setTurn(Board.color.WHITE);
							blackPiece.isPresent = false;
							board.teleport(piece, blackPiece.getColumnPos(), blackPiece.getRowPos());
							inCheck(board);
							if(board.getWhiteInCheck() == true){ //still in check, so move pieces back
								board.teleport(piece, origX, origY);
								board.getSquare(blackPiece.getColumnPos(), blackPiece.getRowPos()).setChessPiece(blackPiece);
								board.getSquare(blackPiece.getColumnPos(), blackPiece.getRowPos()).setPiecePresence(true);
								blackPiece.isPresent = true;
							}
							else{ //not in check anymore, so move stuff back and return false
								board.teleport(piece, origX, origY);
								board.getSquare(blackPiece.getColumnPos(), blackPiece.getRowPos()).setChessPiece(blackPiece);
								board.getSquare(blackPiece.getColumnPos(), blackPiece.getRowPos()).setPiecePresence(true);
								blackPiece.isPresent = true;
								board.setTurn(turn);
								return false;
							}
							board.setTurn(turn);
						}
					}
				}
			}
		}
		else{//color is black
			for(int type = 0; type < 8; type++){
				ListIterator<ChessPiece> whiteIter = board.getWhitePieces().get(type).listIterator();
				while(whiteIter.hasNext()){
					ChessPiece whitePiece = whiteIter.next();
					if(whitePiece.getPresent() == true){
						if(piece.canCapturePiece(board, whitePiece.getColumnPos(), whitePiece.getRowPos())){
							board.setTurn(Board.color.BLACK);
							whitePiece.isPresent = false;
							board.teleport(piece, whitePiece.getColumnPos(), whitePiece.getRowPos());
							inCheck(board);
							if(board.getBlackInCheck() == true){ //still in check, so move pieces back
								board.teleport(piece, origX, origY);
								board.getSquare(whitePiece.getColumnPos(), whitePiece.getRowPos()).setChessPiece(whitePiece);
								board.getSquare(whitePiece.getColumnPos(), whitePiece.getRowPos()).setPiecePresence(true);
								whitePiece.isPresent = true;
							}
							else{ // not in check anymore, so move pieces back and return false
								board.teleport(piece, origX, origY);
								board.getSquare(whitePiece.getColumnPos(), whitePiece.getRowPos()).setChessPiece(whitePiece);
								board.getSquare(whitePiece.getColumnPos(), whitePiece.getRowPos()).setPiecePresence(true);
								whitePiece.isPresent = true;
								board.setTurn(turn);
								return false;
							}
							board.setTurn(turn);
						}
						
					}
				}
			}
		}
		return true;
	}
	
}
