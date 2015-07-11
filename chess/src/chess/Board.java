package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import chess.ChessPiece.pieceType;

/**
 * @author jordan
 *
 */

public class Board {
	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	private static final int numTypes = 8;
	private static final String wKnight = " WKn", bKnight = " BKn", wRook = " WR ", bRook = " BR ", wPawn = " WP ", bPawn = " BP ",
			wBishop = " WB ", bBishop = " BB ", wCannon = " WC ", bCannon = " BC ", wLeaper = " WL ",
			bLeaper = " BL ", wQueen = " WQ ", bQueen = " BQ ", wKing = " WK ", bKing = " BK ";
	
	private int rowSize;
	private int columnSize;
	private boolean whiteInCheck, whiteInCheckmate;
	private boolean blackInCheck, blackInCheckmate;
	private Square[][] squares;
	private ArrayList<List<ChessPiece>> whitePieces; //2d list of chessPieces
	private ArrayList<List<ChessPiece>> blackPieces;
	private boolean isDone;
	private color turn;
	
	public boolean getWhiteInCheckmate() {
		return whiteInCheckmate;
	}


	public void setWhiteInCheckmate(boolean whiteInCheckmate) {
		this.whiteInCheckmate = whiteInCheckmate;
	}


	public boolean getBlackInCheckmate() {
		return blackInCheckmate;
	}


	public void setBlackInCheckmate(boolean blackInCheckmate) {
		this.blackInCheckmate = blackInCheckmate;
	}

	
	public enum color {
		BLACK, WHITE
	}
	
	/**
	 * Uses a 2d string array that maps all the chess pieces on a board to construct a new board
	 * @param array is the 2d string array that is being read to construct a board
	 */
	
	public Board(String[][] array){
		this.rowSize = array.length;
		this.columnSize = array[0].length;
		this.squares = new Square[columnSize][rowSize];
		this.whiteInCheck = false;
		this.blackInCheck = false;
		this.turn = color.WHITE;
		this.setWhitePieces(new ArrayList<List<ChessPiece>>());
		this.setBlackPieces(new ArrayList<List<ChessPiece>>());
		
		for(int x = 0; x < columnSize; x++){
			for(int y = 0; y < rowSize; y++){
				squares[x][y] = new Square();
				squares[x][y].setColumn(x);
				squares[x][y].setRow(y);
				squares[x][y].setPiecePresence(false);
			}
		}
		for(int iter = 0; iter < numTypes; iter++){ //adding first dimension lists for 8 piece types
			getWhitePieces().add(new ArrayList<ChessPiece>());
			getBlackPieces().add(new ArrayList<ChessPiece>());		
		}
		
		this.initPieces();
		int whiteRook = 0, blackRook = 0, whiteBishop = 0, blackBishop = 0, whiteKnight = 0, blackKnight = 0, whitePawn = 0, blackPawn = 0,
			whiteKing = 0, blackKing = 0, whiteQueen = 0, blackQueen = 0, whiteCannon = 0, blackCannon = 0, whiteLeaper = 0, blackLeaper = 0;
		for(int x = 0; x < columnSize; x++){
			for(int y = 0; y < rowSize; y++){
				switch(array[x][y]){
				default:		squares[x][y].setPiecePresence(false);
								squares[x][y].setChessPiece(null);
								break;
						 
				case wRook:		this.whitePieces.get(ROOK).get(whiteRook).setRowPos(y);
								this.whitePieces.get(ROOK).get(whiteRook).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.whitePieces.get(ROOK).get(whiteRook));
								whiteRook++;
								break;
							
				case wKnight: 	this.whitePieces.get(KNIGHT).get(whiteKnight).setRowPos(y);
								this.whitePieces.get(KNIGHT).get(whiteRook).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.whitePieces.get(KNIGHT).get(whiteKnight));
								whiteKnight++;
								break;
								
				case wBishop: 	this.whitePieces.get(BISHOP).get(whiteBishop).setRowPos(y);
								this.whitePieces.get(BISHOP).get(whiteBishop).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.whitePieces.get(BISHOP).get(whiteBishop));
								whiteBishop++;
								break;
								
				case wQueen:	this.whitePieces.get(QUEEN).get(whiteQueen).setRowPos(y);
								this.whitePieces.get(QUEEN).get(whiteQueen).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.whitePieces.get(QUEEN).get(whiteQueen));
								whiteQueen++;
								break;
								
				case wCannon:	this.whitePieces.get(CANNON).get(whiteCannon).setRowPos(y);
								this.whitePieces.get(CANNON).get(whiteCannon).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.whitePieces.get(CANNON).get(whiteCannon));
								whiteCannon++;
								break;
								
				case wPawn: 	this.whitePieces.get(PAWN).get(whitePawn).setRowPos(y);
								this.whitePieces.get(PAWN).get(whitePawn).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.whitePieces.get(PAWN).get(whitePawn));
								whitePawn++;
								break;
								
				case wLeaper:	this.whitePieces.get(THREELEAPER).get(whiteLeaper).setRowPos(y);
								this.whitePieces.get(THREELEAPER).get(whiteLeaper).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.whitePieces.get(THREELEAPER).get(whiteLeaper));
								whiteLeaper++;
								break;
								
				case wKing: 	this.whitePieces.get(KING).get(whiteKing).setRowPos(y);
								this.whitePieces.get(KING).get(whiteKing).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.whitePieces.get(KING).get(whiteKing));
								whiteKing++;
								break;
				
				case bRook:		this.blackPieces.get(ROOK).get(blackRook).setRowPos(y);
								this.blackPieces.get(ROOK).get(blackRook).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.blackPieces.get(ROOK).get(blackRook));
								blackRook++;
								break;
							
				case bKnight: 	this.blackPieces.get(KNIGHT).get(blackKnight).setRowPos(y);
								this.blackPieces.get(KNIGHT).get(blackKnight).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.blackPieces.get(KNIGHT).get(blackKnight));
								blackKnight++;
								break;
								
				case bBishop: 	this.blackPieces.get(BISHOP).get(blackBishop).setRowPos(y);
								this.blackPieces.get(BISHOP).get(blackBishop).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.blackPieces.get(BISHOP).get(blackBishop));
								blackBishop++;
								break;
								
				case bQueen:	this.blackPieces.get(QUEEN).get(blackQueen).setRowPos(y);
								this.blackPieces.get(QUEEN).get(blackQueen).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.blackPieces.get(QUEEN).get(blackQueen));
								blackQueen++;
								break;
								
				case bCannon:	this.blackPieces.get(CANNON).get(blackCannon).setRowPos(y);
								this.blackPieces.get(CANNON).get(blackCannon).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.blackPieces.get(CANNON).get(blackCannon));
								blackCannon++;
								break;
								
				case bPawn: 	this.blackPieces.get(PAWN).get(blackPawn).setRowPos(y);
								this.blackPieces.get(PAWN).get(blackPawn).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.blackPieces.get(PAWN).get(blackPawn));
								blackPawn++;
								break;
								
				case bLeaper:	this.blackPieces.get(THREELEAPER).get(blackLeaper).setRowPos(y);
								this.blackPieces.get(THREELEAPER).get(blackLeaper).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.blackPieces.get(THREELEAPER).get(blackLeaper));
								blackLeaper++;
								break;
								
				case bKing: 	this.blackPieces.get(KING).get(blackKing).setRowPos(y);
								this.blackPieces.get(KING).get(blackKing).setColumnPos(x);
								squares[x][y].setPiecePresence(true);
								squares[x][y].setChessPiece(this.blackPieces.get(KING).get(blackKing));
								blackKing++;
								break;
				}
			}
		}
	}
	
	/**
	 * constructor for board.
	 * allocates memory for square array and whitepieces and blackpieces which are 2d arraylists.
	 * 
	 * @param rowSize is the width of the board to be constructed
	 * @param columnSize is the height of the board to be constructed
	 */
	public Board(int rowSize, int columnSize){
		ChessGraphics.initStack();
		squares = new Square[columnSize][rowSize];
		this.rowSize = rowSize;
		this.columnSize = columnSize;
		this.whiteInCheck = false;
		this.blackInCheck = false;
		this.turn = color.WHITE;
		setWhitePieces(new ArrayList<List<ChessPiece>>());
		setBlackPieces(new ArrayList<List<ChessPiece>>());
		
		for(int x = 0; x < columnSize; x++){
			for(int y = 0; y < rowSize; y++){
				squares[x][y] = new Square();
				squares[x][y].setColumn(x);
				squares[x][y].setRow(y);
				squares[x][y].setPiecePresence(false);
			}
		}
		
		for(int iter = 0; iter < numTypes; iter++){ //adding first dimension lists for 8 piece types
			getWhitePieces().add(new ArrayList<ChessPiece>());
			getBlackPieces().add(new ArrayList<ChessPiece>());		
		}
		this.initPieces();
		this.initChessPiecePos();

	}


	
	/**
	 * Sets the chess piece private variable of each square 
	 * that starts out with a chess piece
	 */
	public void initChessPiecePos(){
		for(int type = 0; type < numTypes; type++){
			ListIterator<ChessPiece> whiteIter = getWhitePieces().get(type).listIterator();
			ListIterator<ChessPiece> blackIter = getBlackPieces().get(type).listIterator();	
			while(whiteIter.hasNext() && blackIter.hasNext()){
				ChessPiece whitePiece = whiteIter.next();
				ChessPiece blackPiece = blackIter.next();
				
				int whitePieceCol = whitePiece.getColumnPos();
				int whitePieceRow = whitePiece.getRowPos();
				
				int blackPieceCol = blackPiece.getColumnPos();
				int blackPieceRow = blackPiece.getRowPos();
				
				squares[whitePieceCol][whitePieceRow].setChessPiece(whitePiece);
				squares[whitePieceCol][whitePieceRow].setPiecePresence(true);
				squares[blackPieceCol][blackPieceRow].setChessPiece(blackPiece);
				squares[blackPieceCol][blackPieceRow].setPiecePresence(true);
			}
		}
	}
	
	/**
	 * moves chess piece to destination without checking if it's a legal move.
	 * used for creating board scenarios and debugging.
	 * @param piece : the chess piece that gets teleported
	 * @param column is the x coordinate of the destination
	 * @param row is the y coordinate of the destination
	 */
	public void teleport(ChessPiece piece, int column, int row){
		this.getSquare(piece.getColumnPos(), piece.getRowPos()).setPiecePresence(false);
		this.getSquare(piece.getColumnPos(),piece.getRowPos()).setChessPiece(null);
		this.getSquare(column,row).setPiecePresence(true);
		this.getSquare(column,row).setChessPiece(piece);
		piece.setRowPos(row);
		piece.setColumnPos(column);
	}

	/**
	 * initializes every chess piece and places them in starting positions.
	 * iter*num is the row starting position.
	 * For example, rooks' row starting position is iter*7 because on the board,
	 * rooks are 7 squares apart.
	 * (0,0) is at the top left.
	 */
	public void initPieces(){
		// inits the top two and bottom two rows
		for(int iter = 0; iter < 2; iter++){
			/*
			 	iter*7 : rooks are 7 spaces away from each other, starting at column 0
				iter*5+1 : knights are 5 spaces away from each other, and start at column 1
			  	iter*3+2 : bishops are 3 spaces away from each other, and start at column 2
			*/
			getWhitePieces().get(ROOK).add(new Rook(color.WHITE, pieceType.ROOK, iter*7, 0));
			getWhitePieces().get(KNIGHT).add(new Knight(color.WHITE, pieceType.ROOK, iter*5+1, 0));
			getWhitePieces().get(BISHOP).add(new Bishop(color.WHITE, pieceType.BISHOP, iter*3+2, 0));
				
			getBlackPieces().get(ROOK).add(new Rook(color.BLACK, pieceType.ROOK, iter*7, 7));
			getBlackPieces().get(KNIGHT).add(new Knight(color.BLACK, pieceType.ROOK, iter*5+1, 7));
			getBlackPieces().get(BISHOP).add(new Bishop(color.BLACK, pieceType.BISHOP, iter*3+2, 7));
		}
		//here i start at 1 and end at 6 because at column 0 and 7 are my special pieces
		for(int pawnIter = 1; pawnIter < 7; pawnIter++){
			getWhitePieces().get(PAWN).add(new Pawn(color.WHITE, pieceType.PAWN, pawnIter, 1));
			getBlackPieces().get(PAWN).add(new Pawn(color.BLACK, pieceType.PAWN, pawnIter, 6));
		}
		getWhitePieces().get(QUEEN).add(new Queen(color.WHITE, pieceType.QUEEN, 3, 0));
		getWhitePieces().get(KING).add(new King(color.WHITE, pieceType.KING, 4, 0));
		
		getWhitePieces().get(CANNON).add(new Cannon(color.WHITE, pieceType.CANNON, 0, 1));
		getWhitePieces().get(THREELEAPER).add(new Threeleaper(color.WHITE, pieceType.THREELEAPER, 7, 1));
		
		getBlackPieces().get(CANNON).add(new Cannon(color.BLACK, pieceType.CANNON, 0, 6));
		getBlackPieces().get(THREELEAPER).add(new Threeleaper(color.BLACK, pieceType.THREELEAPER, 7, 6));
		
		getBlackPieces().get(QUEEN).add(new Queen(color.BLACK, pieceType.QUEEN, 3, 7));
		getBlackPieces().get(KING).add(new King(color.BLACK, pieceType.KING, 4, 7));
	}
	/**
	 * 
	 * @param column is the x coordinate of the wanted square
	 * @param row is the y coordinate of the wanted square
	 * @return null if coords are out of bounds, else return the square at the given coords
	 */
	public Square getSquare(int column, int row){
		if(column < 0 || column >= this.columnSize || row < 0 || row >= this.rowSize){
			return null;
		}
		return this.squares[column][row];
	}
	
	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public boolean getWhiteInCheck() {
		return whiteInCheck;
	}

	public void setWhiteInCheck(boolean whiteInCheck) {
		this.whiteInCheck = whiteInCheck;
	}

	public boolean getBlackInCheck() {
		return blackInCheck;
	}

	public void setBlackInCheck(boolean blackInCheck) {
		this.blackInCheck = blackInCheck;
	}

	
	public boolean isDone() {
		return isDone;
	}


	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	
	public color getTurn() {
		return turn;
	}

	public void setTurn(color turn) {
		this.turn = turn;
	}
	
	/**
	 * Iterates through every chess piece, and add a string element
	 * into the string array based on what that piece is.
	 * For example, when I see a white rook, I will add the element "WR"
	 * at the coords of the chess piece.
	 * If there's no chess piece, then the element will just be 4 spaces.
	 * 4 is a random number that I think looks good.
	 * 
	 * @return new string[][] that maps the board
	 */
	public String[][] drawBoard(){
		String[][] array = new String[8][8];
		for(int x = 0; x < squares.length; x++){
			for(int y = 0; y < squares[0].length; y++){
				if(squares[x][y].piecePresence == true){
					ChessPiece piece = squares[x][y].getChessPiece();
					if(squares[x][y].getChessPiece().getColor() == color.WHITE){
						if(piece instanceof Rook){
							array[x][y] = " WR ";
						}
						else if(piece instanceof Knight){
							array[x][y] = " WKn";
						}
						else if(piece instanceof Pawn){
							array[x][y] = " WP ";
						}
						else if(piece instanceof Bishop){
							array[x][y] = " WB ";
						}
						else if(piece instanceof Queen){
							array[x][y] = " WQ ";
						}
						else if(piece instanceof King){
							array[x][y] = " WK ";
						}
						else if(piece instanceof Cannon){
							array[x][y] = " WC ";
						}
						else if(piece instanceof Threeleaper){
							array[x][y] = " WL ";
						}
					}
					else{ //piece's color is black
						if(piece instanceof Rook){
							array[x][y] = " BR ";
						}
						else if(piece instanceof Knight){
							array[x][y] = " BKn";
						}
						else if(piece instanceof Pawn){
							array[x][y] = " BP ";
						}
						else if(piece instanceof Bishop){
							array[x][y] = " BB ";
						}
						else if(piece instanceof Queen){
							array[x][y] = " BQ ";
						}
						else if(piece instanceof King){
							array[x][y] = " BK ";
						}
						else if(piece instanceof Cannon){
							array[x][y] = " BC ";
						}
						else if(piece instanceof Threeleaper){
							array[x][y] = " BL ";
						}
					}
				}
				else{	
					array[x][y] = "    ";
				}

			}
		}
		
		
		return array;
	}
	
	public ArrayList<List<ChessPiece>> getWhitePieces() {
		return whitePieces;
	}
	public void setWhitePieces(ArrayList<List<ChessPiece>> whitePieces) {
		this.whitePieces = whitePieces;
	}

	public ArrayList<List<ChessPiece>> getBlackPieces() {
		return blackPieces;
	}
	public void setBlackPieces(ArrayList<List<ChessPiece>> blackPieces) {
		this.blackPieces = blackPieces;
	}

	public class Square {
		private int row;
		private int column;
		private Boolean piecePresence;
		private ChessPiece chessPiece;

		public Square() {
			this.piecePresence = false;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}
		
		public Boolean getPiecePresence() {
			return piecePresence;
		}

		public void setPiecePresence(Boolean piecePresence) {
			this.piecePresence = piecePresence;
		}
		
		public ChessPiece getChessPiece() {
			return this.chessPiece;
		}

		public void setChessPiece(ChessPiece chessPiece) {
			this.chessPiece = chessPiece;
		}

	}

}
