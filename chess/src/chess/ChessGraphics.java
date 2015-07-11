package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import chess.Board.color;

public class ChessGraphics implements MouseListener, ActionListener{
	private static final int PAWN = 0, ROOK = 1, BISHOP = 2, KNIGHT = 3, CANNON = 4, THREELEAPER = 5, QUEEN = 6, KING = 7;
	
	private static final int columnSize = 8, rowSize = 8;
	
	private static final String wKnight = " WKn", bKnight = " BKn", wRook = " WR ", bRook = " BR ", wPawn = " WP ", bPawn = " BP ",
								wBishop = " WB ", bBishop = " BB ", wCannon = " WC ", bCannon = " BC ", wLeaper = " WL ",
								bLeaper = " BL ", wQueen = " WQ ", bQueen = " BQ ", wKing = " WK ", bKing = " BK ";
	
	private static Stack<String[][]> stack;
	
	private ChessPiece pieceSelected;
	private squareButton[][] squareButtons;
	private Board board;
	private JFrame windowFrame;
	private JPanel windowPanel;
	private JLabel turnLabel;
	private JPanel gameOverPanel;
	private JPanel startPanel;	
	private JButton startButton;
	private JButton restartButton;
	private JButton undoButton;
	private JButton forfeitButton;
	private JButton againButton;
	private JButton exitButton;
	private Player player1;
	private Player player2;
	private String winner;
	
	private ImageIcon blackKnight = new ImageIcon(this.getClass().getResource("/images/black knight.png"));
	private ImageIcon whiteKnight = new ImageIcon(this.getClass().getResource("/images/white knight.png"));
	
	private ImageIcon blackKing = new ImageIcon(this.getClass().getResource("/images/black king.png"));
	private ImageIcon whiteKing = new ImageIcon(this.getClass().getResource("/images/white king.png"));
	
	private ImageIcon blackQueen = new ImageIcon(this.getClass().getResource("/images/black queen.png"));
	private ImageIcon whiteQueen = new ImageIcon(this.getClass().getResource("/images/white queen.png"));
	
	private ImageIcon blackRook = new ImageIcon(this.getClass().getResource("/images/black rook.png"));
	private ImageIcon whiteRook = new ImageIcon(this.getClass().getResource("/images/white rook.png"));

	private ImageIcon blackBishop = new ImageIcon(this.getClass().getResource("/images/black bishop.png"));
	private ImageIcon whiteBishop = new ImageIcon(this.getClass().getResource("/images/white bishop.png"));
	
	private ImageIcon blackPawn = new ImageIcon(this.getClass().getResource("/images/black pawn.png"));
	private ImageIcon whitePawn = new ImageIcon(this.getClass().getResource("/images/white pawn.png"));
	
	private ImageIcon blackCannon = new ImageIcon(this.getClass().getResource("/images/black cannon.png"));
	private ImageIcon whiteCannon = new ImageIcon(this.getClass().getResource("/images/white cannon.png"));
	
	private ImageIcon blackLeaper = new ImageIcon(this.getClass().getResource("/images/black threeleaper.png"));
	private ImageIcon whiteLeaper = new ImageIcon(this.getClass().getResource("/images/white threeleaper.png"));
	
	/**
	 * Uses the drawBoard() method to create a 2d string array representation of the
	 * chess board. Then iterate through the 2d array and set the image icon corresponding
	 * to the string at each coordinate.
	 */
	public void initIcons(){
		String[][] boardSquares = board.drawBoard();
		for(int y = 0; y < boardSquares.length; y++){
			for(int x = 0; x < boardSquares[0].length; x++){
				switch(boardSquares[x][y]){
				case wRook: squareButtons[x][y].setIcon(whiteRook); break;
				case wKnight: squareButtons[x][y].setIcon(whiteKnight); break;
				case wBishop: squareButtons[x][y].setIcon(whiteBishop); break;
				case wQueen: squareButtons[x][y].setIcon(whiteQueen); break;
				case wCannon: squareButtons[x][y].setIcon(whiteCannon); break;
				case wPawn: squareButtons[x][y].setIcon(whitePawn); break;
				case wLeaper: squareButtons[x][y].setIcon(whiteLeaper); break;
				case wKing: squareButtons[x][y].setIcon(whiteKing); break;
				
				case bRook: squareButtons[x][y].setIcon(blackRook); break;
				case bKnight: squareButtons[x][y].setIcon(blackKnight); break;
				case bBishop: squareButtons[x][y].setIcon(blackBishop); break;
				case bQueen: squareButtons[x][y].setIcon(blackQueen); break;
				case bCannon: squareButtons[x][y].setIcon(blackCannon); break;
				case bPawn: squareButtons[x][y].setIcon(blackPawn); break;
				case bLeaper: squareButtons[x][y].setIcon(blackLeaper); break;
				case bKing: squareButtons[x][y].setIcon(blackKing); break;
				
				default: squareButtons[x][y].setIcon(null); break;
				}
				
			}
		}
	}
	/**
	 * allocates space for each square button and adds a mouse listener to each.
	 */
	public void initSquares(){
		for (int x = 0; x < squareButtons.length; x++) {
            for (int y = 0; y < squareButtons[0].length; y++) {
            	squareButtons[x][y] = new squareButton(board.getSquare(x, y));
            	squareButtons[x][y].addMouseListener(this);
            }
		}
	}
	
	/**
	 * Allocates memory to each square and calls the constructor to set the coordinates and chess piece.
	 * Adds a background of white or black to each square in the called window.squares[][]
	 * This background makes up the chess board.
	 */
	public void setBackground(){
		for (int x = 0; x < squareButtons.length; x++) {
            for (int y = 0; y < squareButtons[0].length; y++) {
            	ChessPiece piece = board.getSquare(x, y).getChessPiece();
                //the squares that have both dimensions odd or both even are white
                if ((y % 2 == 1 && x % 2 == 1) || (y % 2 == 0 && x % 2 == 0)) {
                    squareButtons[x][y].setBackground(Color.white);
                } else {
                    squareButtons[x][y].setBackground(Color.gray);
                }
            }
        }
	}
	/**
	 * Sets up the main game window. Also adds each square button to the chess board panel.
	 */
	public void initWindow(){
		this.windowPanel = new JPanel(new BorderLayout());
		windowFrame.setSize(800, 800);
		
		JPanel topPanel = new JPanel(new FlowLayout());
		JPanel chessBoard = new JPanel(new GridLayout(board.getColumnSize(),board.getRowSize()));
		restartButton = new JButton("restart");
		restartButton.addActionListener(this);
		forfeitButton = new JButton("forfeit");
		forfeitButton.addActionListener(this);
		
		undoButton = new JButton("undo");
		undoButton.addActionListener(this);
		turnLabel = new JLabel(player1.getName()+"'s turn");
		
		topPanel.add(forfeitButton);
		topPanel.add(restartButton);
		topPanel.add(undoButton);
		topPanel.add(turnLabel);

		topPanel.add(player1.getScoreLabel());
		topPanel.add(player2.getScoreLabel());
		
		topPanel.setBackground(Color.yellow);
		windowPanel.add(topPanel, BorderLayout.PAGE_START);
		
		squareButtons = new squareButton[board.getColumnSize()][board.getRowSize()];
		this.initSquares();
		this.setBackground(); //sets the squares' background to white/gray
		this.initIcons();
		 
		for(int x = 0; x < squareButtons.length; x++){
		   	for(int y = 0; y < squareButtons[0].length; y++){
		   		chessBoard.add(squareButtons[y][x]);
		   	}
		}
		
		windowPanel.add(chessBoard, BorderLayout.CENTER);		 
	}
	
	/**
	 * Sets up the start panel which is the first panel seen. It contains the start button
	 * and option to input players' names.
	 */
	public void initStartPanel(){
		startPanel = new JPanel(new BorderLayout());
		JPanel innerPanel = new JPanel(new FlowLayout());
		startButton = new JButton("Start Game");
		startButton.addActionListener(this);
		innerPanel.add(startButton);
		player1 = new Player();
		player2 = new Player();
		player1.getPlayerButton().setText("Player1's name");
		player2.getPlayerButton().setText("Player2's name");
		player1.getPlayerButton().addActionListener(player1);
		player2.getPlayerButton().addActionListener(player2);
		innerPanel.add(player1.getPlayerButton());
		innerPanel.add(player2.getPlayerButton());
		startPanel.add(innerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Sets up the game over panels.
	 */
	public void initGameOver(){
		againButton = new JButton("Play Again");
		exitButton = new JButton("Exit");
		gameOverPanel = new JPanel();
		gameOverPanel.setLayout(null);
		JLabel gameOverLabel;
		if(winner.equals("player1")){
			gameOverLabel = new JLabel(player1.getName()+" Wins");
		}
		else{
			gameOverLabel = new JLabel(player2.getName()+" Wins");
		}
		gameOverLabel.setFont(new Font(null, 1, 30));
		gameOverLabel.setBounds(300,100,300,100);
		againButton.setBounds(300, 200, 200, 50);
		exitButton.setBounds(300, 250, 200, 50);
		againButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		gameOverPanel.add(gameOverLabel);
		gameOverPanel.add(againButton);
		gameOverPanel.add(exitButton);
	}
	
	/**
	 * This function alters the JLabel, so that it reflects correctly whose turn it is.
	 * @param turn is the JLabel on the top of the screen saying whose turn it is
	 */
	public void changeTurn(JLabel turn){
		if(this.board.getTurn() == Board.color.WHITE){
			turn.setText(player1.getName()+"'s turn");
		}
		else turn.setText(player2.getName()+"'s turn");
	}
	
	public static void main(String[] args) {
		ChessGraphics window = new ChessGraphics();
		window.windowFrame = new JFrame("chess");
		
		window.board = new Board(columnSize, rowSize);
		window.stack = new Stack<String[][]>();
		
		window.winner = "player1";
		window.initStartPanel();
		window.initGameOver();
		window.initWindow();
		
		window.windowFrame.add(window.startPanel);
		window.windowFrame.setVisible(true);
		window.windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public class squareButton extends JButton{
		private Board.Square square;
		
		public squareButton(Board.Square square){
			this.square = square;
		}
		public squareButton(){
			super();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * If there is a check/checkmate, this will open a message dialog.
	 */
	public void showDialog(){
		if(board.getWhiteInCheckmate() == true){
			JOptionPane.showMessageDialog(windowPanel, player1.getName()+" is in checkmate! AHAHA UR GONNA LOSE");
		}
		else if(board.getWhiteInCheck() == true){
			JOptionPane.showMessageDialog(windowPanel, player1.getName()+" is in check! DUN DUN DUN!!!");
		}
		if(board.getBlackInCheckmate() == true){
			JOptionPane.showMessageDialog(windowPanel, player2.getName()+" is in checkmate AHAHA UR GONNA LOSE!");
		}
		else if(board.getBlackInCheck() == true){
			JOptionPane.showMessageDialog(windowPanel, player2.getName()+" is in check! DUN DUN DUN!!!");
		}
	}
	
	/**
	 * This method runs whenever my mouse is pressed. It checks which square I clicked, and moves/captures
	 * the second square I click. Before moving/capturing, it saves its current state in the stack, so I
	 * can undo if I need to.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		for(int x = 0; x < squareButtons.length; x++){
			for(int y = 0; y < squareButtons[0].length; y++){
				Board.Square square = squareButtons[x][y].square;
				if(squareButtons[x][y].equals(e.getSource())){
					if(pieceSelected != null){
						if(square.getChessPiece() == null){
							if(!pieceSelected.canMove(board, x, y)){
								pieceSelected = null;
								JOptionPane.showMessageDialog(windowPanel, "illegal move SUCKA AHAHA");
								return;
							}
							pieceSelected.move(board, x, y);
							pieceSelected = null;
							ChessPiece.inCheck(board);
							if(board.getWhiteInCheck() == true || board.getBlackInCheck() == true){
								ChessPiece.checkmate(board, Board.color.WHITE);
								ChessPiece.checkmate(board, Board.color.BLACK);
								ChessPiece.inCheck(board);
								showDialog();
							}
							changeTurn(turnLabel);
							initIcons();
						}
						else{ //there is a piece at the selected square
							if(pieceSelected.canCapturePiece(board, x, y)){
								pieceSelected.capture(board, x, y);
								ChessPiece.inCheck(board);
								if(board.getWhiteInCheck() == true || board.getBlackInCheck() == true){
									ChessPiece.checkmate(board, Board.color.WHITE);
									ChessPiece.checkmate(board, Board.color.BLACK);
									ChessPiece.inCheck(board);
									showDialog();
								}
								pieceSelected = null;
								if(board.getBlackPieces().get(KING).get(0).getPresent() == false){
									winner = "player1";
									player1.setScore(player1.getScore()+1);
									windowFrame.remove(windowPanel);
									windowFrame.add(gameOverPanel);
									windowFrame.validate();
								}
								else if(board.getWhitePieces().get(KING).get(0).getPresent() == false){
									winner = "player2";
									player2.setScore(player2.getScore()+1);
									windowFrame.remove(windowPanel);
									windowFrame.add(gameOverPanel);
									windowFrame.validate();
								}
								changeTurn(turnLabel);
								initIcons();
							}
							else{
								pieceSelected = null;
								JOptionPane.showMessageDialog(windowPanel, "illegal move SUCKA AHAHA");
								return;
							}
						}
					}
					else if(pieceSelected == null){
						if(square.getChessPiece() == null){
							return;
						}
						else{ //there is a chess piece at the selected square
							pieceSelected = square.getChessPiece();
							return;
						}
					}
				}
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Sets the board equal to the board from one move before and setting its turn back
	 * to what it was.
	 * @return a board that is constructed from a 2d string array popped from the stack
	 */
	public Board undoMove(){
		Board.color turn = board.getTurn();
		if(ChessGraphics.getStack().size() > 0){
			String[][] array = ChessGraphics.getStack().pop();
			Board retBoard = new Board(array);
			
			if(turn == Board.color.WHITE){
				retBoard.setTurn(Board.color.BLACK);
			}
			else{
				retBoard.setTurn(Board.color.WHITE);
			}
			return retBoard;
		}
		return null;
	}
	/**
	 * A player can undo his or her move when it gets to the opponent's turn, but before the opponent makes his or her move.
	 */
	public void undo(){
		if(stack.size() > 0){
			windowFrame.remove(windowPanel);
			board = undoMove();
			initWindow();
			changeTurn(turnLabel);
			windowFrame.add(windowPanel);
			windowFrame.validate();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(startButton.equals(e.getSource())){
			startGame();
		}
		else if(restartButton.equals(e.getSource())){
			restart();
		}
		else if(undoButton.equals(e.getSource())){
			undo();
		}
		else if(forfeitButton.equals(e.getSource())){
			forfeit();
		}
		else if(exitButton.equals(e.getSource())){
			System.exit(0);
		}
		else if(againButton.equals(e.getSource())){
			playAgain();
		}
	}
	
	public void playAgain(){
		windowFrame.remove(gameOverPanel);
		board = new Board(board.getColumnSize(), board.getRowSize());
		stack = new Stack<String[][]>();
		initWindow();
		startGame();
	}
	
	/**
	 * removes the startPanel from the window frame and sets all the players' info
	 */
	public void startGame(){
		turnLabel.setText(player1.getName()+"'s turn");
		windowFrame.remove(startPanel);
		player1.setScoreLabel(player1.getScore());
		player2.setScoreLabel(player2.getScore());
		initGameOver();
		windowFrame.add(windowPanel);
		windowFrame.validate();
	}
	
	/**
	 * restarts the game, giving each player a point. Resets the board and stack.
	 */
	public void restart(){
		windowFrame.remove(windowPanel);
		board = new Board(board.getColumnSize(), board.getRowSize());
		stack = new Stack<String[][]>();
		player1.setScore(player1.getScore()+1);
		player2.setScore(player2.getScore()+1);
		player1.setScoreLabel(player1.getScore());
		player2.setScoreLabel(player2.getScore());
		initWindow();
		windowFrame.add(windowPanel);
		windowFrame.validate();
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * When a player forfeits, the other player's score goes up. For some reason, revalidate isn't enough to
	 * refresh the screen. Repaint was needed.
	 */
	public void forfeit(){
		windowFrame.remove(windowPanel);
		if(board.getTurn() == Board.color.WHITE){
			player2.setScore(player2.getScore()+1);
			winner = "player2";
			windowFrame.add(gameOverPanel);
		}
		else{
			player1.setScore(player1.getScore()+1);
			winner = "player1";
			windowFrame.add(gameOverPanel);
		}
		
		player1.setScoreLabel(player1.getScore());
		player2.setScoreLabel(player2.getScore());
		windowFrame.revalidate();
		windowFrame.repaint();
	}
	
	public static Stack<String[][]> getStack() {
		return stack;
	}
	public static void initStack(){
		stack = new Stack<String[][]>();
	}
}
