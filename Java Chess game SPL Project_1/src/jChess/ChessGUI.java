package jChess;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;

import javax.imageio.ImageIO;

public class ChessGUI  implements ActionListener{

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private String host = "10.220.50.142";
    private int port = 1500;
    public final JLabel message = new JLabel("Java Chess Game is ready to play!");
    private static final String COLS = "ABCDEFGH";
    public static final int QUEEN = 0, KING = 1,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5, BLANK = -1;
    
    public static final int BLACK = 0, WHITE = 1;
    public int currentMove;
    public int type;
    
    boolean connected = false;
    
//    SingleServer srv;
    Client clnt;
    
    private boolean isSelected;
    
    private	static int sourceX;
    private static int sourceY;
    private static int destX;
    private static int destY;
    private int Wkingi = 0;
    private int Wkingj = 0;
    private int Bkingi = 0;
    private int Bkingj = 0;
    
    private int[][] chessBoardConfig;
    private int[][] colorInfo;

    
    private int chessBoardHeight = 8;
    private int chessBoardWidth = 8;
    
    private int x;
    private int y;
    
    Rook rookObj = new Rook();
    Knight knightObj = new Knight();
    Bishop bishopObj = new Bishop();
    Queen queenObj = new Queen();
    King kingObj = new King();
    Pawn pawnObj = new Pawn();
    
    

    ChessGUI() {
    	
        initializeGui();
    }

    public final void initializeGui() {
       createImages();
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        Action newGameAction = new AbstractAction("New") {

            @Override
            public void actionPerformed(ActionEvent e) {
            	initializeBoard();
                drawBoard();
            }
        };
        Action multiPlayer = new AbstractAction("Multi") {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		initializeBoard();
        		drawBoard();
        		String username = JOptionPane.showInputDialog("Username");
        		
        		clnt = new Client(host, port, username, ChessGUI.this);
        		clnt.Start();
        		connected = true;
        		
        	}
        };
        
        
        tools.add(newGameAction);
        tools.add(multiPlayer);
        tools.add(new JButton("Save")); 
        tools.add(new JButton("Restore")); 
        tools.addSeparator();
        tools.add(new JButton("Resign")); 
        tools.addSeparator();
        tools.add(message);

        gui.add(new JLabel("?"), BorderLayout.LINE_START);

        chessBoard = new JPanel(new GridLayout(0, 9)) {

           
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int)d.getWidth(),(int)d.getHeight());
                } else if (c!=null &&
                        c.getWidth()>d.getWidth() &&
                        c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                int s = (w>h ? h : w);
                return new Dimension(s,s);
            }
        };
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8,8,8,8),
                new LineBorder(Color.BLACK)
                ));
       
        Color ochre = new Color(204,119,34);
        chessBoard.setBackground(ochre);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(ochre);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
               
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                b.putClientProperty("first", ii);
                b.putClientProperty("second", jj);
                b.addActionListener(this);
                chessBoardSquares[jj][ii] = b;
            }
        }
       
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (9-(ii + 1)),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }
    
    public final void initializeBoard() {
    	currentMove = WHITE;
    	chessBoardConfig = new int[][]{
        		{ ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK},
        		{ PAWN, PAWN,   PAWN,   PAWN, PAWN,  PAWN,   PAWN,   PAWN},
        		{ BLANK,BLANK,  BLANK,  BLANK,BLANK, BLANK,  BLANK,  BLANK}, 
        		{ BLANK,BLANK,  BLANK,  BLANK,BLANK, BLANK,  BLANK,  BLANK},
        		{ BLANK,BLANK,  BLANK,  BLANK,BLANK, BLANK,  BLANK,  BLANK},
        		{ BLANK,BLANK,  BLANK,  BLANK,BLANK, BLANK,  BLANK,  BLANK},
        		{ PAWN, PAWN,   PAWN,   PAWN, PAWN,  PAWN,   PAWN,   PAWN},
        		{ ROOK, KNIGHT, BISHOP, QUEEN,KING,  BISHOP, KNIGHT, ROOK}
        };
        
        colorInfo =  new int[][]{
        		{ BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK},
        		{ BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK, BLACK},
        		{ BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK}, 
        		{ BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK},
        		{ BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK},
        		{ BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK, BLANK},
           		{ WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE},
        		{ WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE}
        };
    }
    
    

    public final JComponent getGui() {
        return gui;
    }

    private final void createImages() {
        try {
            File url = new File("images\\memI0.png");
            BufferedImage bi = ImageIO.read(url);
            for (int ii = 0; ii < 2; ii++) {
                for (int jj = 0; jj < 6; jj++) {
                    chessPieceImages[ii][jj] = bi.getSubimage(jj * 64, ii * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Initializes the icons of the initial chess board piece places
     */
    public final void drawBoard() {
    	String currMove;
    	if(currentMove == WHITE)
    		currMove = "WHITE!";
    	else
    		currMove = "BLACK";
        message.setText("Make your move " + currMove);
        ImageIcon icon = new ImageIcon(
                new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
//        // set up the black pieces
//        
//        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
//            chessBoardSquares[ii][0].setIcon(new ImageIcon(
//                    chessPieceImages[BLACK][STARTING_ROW[ii]]));
//        }
//        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
//            chessBoardSquares[ii][1].setIcon(new ImageIcon(
//                    chessPieceImages[BLACK][PAWN]));
//        }
//        // set up the white pieces
//        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
//            chessBoardSquares[ii][6].setIcon(new ImageIcon(
//                    chessPieceImages[WHITE][PAWN]));
//        }
//        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
//            chessBoardSquares[ii][7].setIcon(new ImageIcon(
//                    chessPieceImages[WHITE][STARTING_ROW[ii]]));
//        }
        
//        b.setIcon(icon);
//        if ((jj % 2 == 1 && ii % 2 == 1)
//                //) {
//                || (jj % 2 == 0 && ii % 2 == 0)) {
//            b.setBackground(Color.WHITE);
//        } else {
//            b.setBackground(Color.BLACK);
//        }
        
        for(int ii = 0; ii < chessBoardWidth; ii++) {
        	for (int jj = 0; jj < chessBoardHeight; jj++) {
        		if(chessBoardConfig[jj][ii] != BLANK) {
        			chessBoardSquares[ii][jj].setIcon(new ImageIcon(
            				chessPieceImages[colorInfo[jj][ii]][chessBoardConfig[jj][ii]]));
        		}
        		else {
        			chessBoardSquares[ii][jj].setIcon(icon);
        			if ((jj % 2 == 1 && ii % 2 == 1)
        	                //) {
        	                || (jj % 2 == 0 && ii % 2 == 0)) {
        				chessBoardSquares[ii][jj].setBackground(Color.WHITE);
        	        } else {
        	        	chessBoardSquares[ii][jj].setBackground(Color.BLACK);
        	        }
        		}
        	}
        }
        		
       isSelected = false;
    }
    
    public boolean CheckMoveValidity() {
    	boolean valid = false;
//    	return true;
    	
    	if(chessBoardConfig[sourceX][sourceY] == ROOK) {
    		valid =  rookObj.isValid(chessBoardConfig, colorInfo, sourceX, sourceY, destX, destY);
    	}
    	
    	else if(chessBoardConfig[sourceX][sourceY] == KNIGHT) {
    		valid =  knightObj.isValid(sourceX, sourceY, destX, destY);
    	}
    	
    	else if(chessBoardConfig[sourceX][sourceY] == BISHOP) {
    		valid =  bishopObj.isValid(chessBoardConfig, colorInfo, sourceX, sourceY, destX, destY);
    	}
    	
    	else if(chessBoardConfig[sourceX][sourceY] == KING) {
    		valid =  kingObj.isValid(colorInfo, sourceX, sourceY, destX, destY);
    	}
    	
    	else if(chessBoardConfig[sourceX][sourceY] == QUEEN) {
    		valid =  queenObj.isValid(chessBoardConfig, colorInfo, sourceX, sourceY, destX, destY);
    	}
    	
    	else if(chessBoardConfig[sourceX][sourceY] == PAWN) {
    		valid =  pawnObj.isValid(chessBoardConfig, colorInfo, sourceX, sourceY, destX, destY);
    	}
    	
    	return valid;
    }
    
    public void serverChange(int[][] newConfig, int[][] newColor)
    {
    	chessBoardConfig = newConfig;
        colorInfo = newColor;
    }
    
    public void moveChessPiece() {
    	
    	if(colorInfo[sourceX][sourceY] == currentMove) {
    		
    		chessBoardConfig[destX][destY] = chessBoardConfig[sourceX][sourceY];
        	colorInfo[destX][destY] = colorInfo[sourceX][sourceY];
        	
        	chessBoardConfig[sourceX][sourceY] = BLANK;
        	colorInfo[sourceX][sourceY] = BLANK;
        	
//        	for(int i=0;i<8;i++) {
//        		for(int j=0;j<8;j++) {
//        			System.out.printf("%d ",chessBoardConfig[i][j]);
//        		}
//        		System.out.printf("\n");
//        	}
        	
//        	currentMove = 1 - currentMove;						//MOVE CHANGES
        	drawBoard();
        	
        	if(isInCheck() == true) {// && isCheckmate() == false) {
        		message.setText("CHECK!");
        	}
    	}
    	
    	else {
    		message.setText("WRONG COLORED PIECE SELECTED!");
    	}
    	
    }
    
    public void checkKingPosition() {
    	
    	for(int i = 0; i < 8; i++) {
    		
    		for(int j = 0; j < 8; j++) {
    			
    			if(currentMove == WHITE) {
    				if(chessBoardConfig[i][j] == KING && colorInfo[i][j] == WHITE) {
    					Wkingi = i;
    					Wkingj = j;
    				}
    			}
    			else {
    				if(chessBoardConfig[i][j] == KING && colorInfo[i][j] == BLACK) {
    					Bkingi = i;
    					Bkingj = j;
    				}
    			}
    		}
    	}
//    	System.out.println("KING i: " + kingi + " j: " + kingj);
    }
    
    
    
    public boolean isProtectedByBlack(int X, int Y) {
    	int i, j;
    	boolean valid = false;
    	System.out.println("WHITE X: " + X + " Y: " + Y);
    	
    	for(i=0; i<8; i++) {
    		for(j=0;j<8;j++) {
    			
    			if(chessBoardConfig[i][j] == ROOK && colorInfo[i][j] == BLACK) {
    				valid = rookObj.isValid(chessBoardConfig, colorInfo, i, j, X, Y);
    			}
    			if(chessBoardConfig[i][j] == KNIGHT && colorInfo[i][j] == BLACK) {
    				valid = knightObj.isValid(i, j, X, Y);
    			}
    			if(chessBoardConfig[i][j] == BISHOP && colorInfo[i][j] == BLACK) {
    				valid = bishopObj.isValid(chessBoardConfig, colorInfo, i, j, X, Y);
    			}
    			if(chessBoardConfig[i][j] == QUEEN && colorInfo[i][j] == BLACK) {
    				valid = queenObj.isValid(chessBoardConfig, colorInfo, i, j, X, Y);
    			}
    			if(chessBoardConfig[i][j] == PAWN && colorInfo[i][j] == BLACK) {
    				valid = pawnObj.isValid(chessBoardConfig, colorInfo, i, j, X, Y);
    			}
    		}
    	}
    	
    	return valid;
    }
    
    
    public boolean isProtectedByWhite(int X, int Y) {
    	int i, j;
    	boolean valid = false;
    	System.out.println("BLACK X: " + X + " Y: " + Y);
    	
    	for(i=0; i<8; i++) {
    		for(j=0;j<8;j++) {
    			
    			if(chessBoardConfig[i][j] == ROOK && colorInfo[i][j] == WHITE) {
    				valid = rookObj.isValid(chessBoardConfig, colorInfo, i, j, X, Y);
    			}
    			if(chessBoardConfig[i][j] == KNIGHT && colorInfo[i][j] == WHITE) {
    				valid = knightObj.isValid(i, j, X, Y);
    			}
    			if(chessBoardConfig[i][j] == BISHOP && colorInfo[i][j] == WHITE) {
    				valid = bishopObj.isValid(chessBoardConfig, colorInfo, i, j, X, Y);
    			}
    			if(chessBoardConfig[i][j] == QUEEN && colorInfo[i][j] == WHITE) {
    				valid = queenObj.isValid(chessBoardConfig, colorInfo, i, j, X, Y);
    			}
    			if(chessBoardConfig[i][j] == PAWN && colorInfo[i][j] == WHITE) {
    				valid = pawnObj.isValid(chessBoardConfig, colorInfo, i, j, X, Y);
    			}
    		}
    	}
    	
		return valid;
    }
    
    public boolean isInCheck() {
    	
    	boolean valid = false;
    	
    	if(currentMove == WHITE) {
    		checkKingPosition();
    		if(isProtectedByBlack(Wkingi, Wkingj) == true) {
//    			message.setText("CHECK!");
    			valid = true;
    		}
    	}
    	if(currentMove == BLACK) {
    		checkKingPosition();
    		if(isProtectedByWhite(Bkingi, Bkingj) == true) {
//    			message.setText("CHECK!");
    			valid = true;
    		}
    	}
    	
    	return valid;
    }
    
    public boolean isCheckmate() {
    	
    	boolean valid = false;
    	
    	if(currentMove == WHITE && isInCheck() == true) {
    		
    		if(isProtectedByBlack(Wkingi+1, Wkingj) == true && isProtectedByBlack(Wkingi-1, Wkingj) == true &&
    				isProtectedByBlack(Wkingi, Wkingj+1) == true && isProtectedByBlack(Wkingi, Wkingj-1) == true &&
    				isProtectedByBlack(Wkingi+1, Wkingj+1) == true && isProtectedByBlack(Wkingi-1, Wkingj-1) == true && 
    				isProtectedByBlack(Wkingi+1, Wkingj-1) == true && isProtectedByBlack(Wkingi-1, Wkingj+1) == true) {
    			
    			valid = true;
    		}
    	}
    	
    	else if(currentMove == BLACK && isInCheck() == true) {
    		
    		if(isProtectedByWhite(Bkingi+1, Bkingj) == true && isProtectedByWhite(Bkingi-1, Bkingj) == true && 
    				isProtectedByWhite(Bkingi, Bkingj+1) == true && isProtectedByWhite(Bkingi, Bkingj-1) == true && 
    				isProtectedByWhite(Bkingi+1, Bkingj+1) == true && isProtectedByWhite(Bkingi-1, Bkingj-1) == true && 
    				isProtectedByWhite(Bkingi+1, Bkingj-1) == true && isProtectedByWhite(Bkingi-1, Bkingj+1) == true) {
    			
    			valid = true;
    		}
    	}
    	
    	return valid;
    }

    public static void main(String[] args) {
//        Runnable r = new Runnable() {
//
//            @Override
//            public void run() {
                ChessGUI cg = new ChessGUI();

                JFrame f = new JFrame("Java Chess Game");
                f.add(cg.getGui());
                
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
              
                f.setLocationByPlatform(true);
                
              
                f.pack();
               
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
//            }
//        };
       
//        SwingUtilities.invokeLater(r);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton button = (JButton) arg0.getSource();
		x = (int) button.getClientProperty("first");
		y = (int) button.getClientProperty("second");
		
//		isSelected = false;
		
//		System.out.println("X : " + x + " Y : " + y);
		
		if(isSelected == false) {
			sourceX = x;
			sourceY = y;
			isSelected = true;
		}
		
		else if(isSelected == true) {
			destX = x;
			destY = y;
//			System.out.println("Selected");
			if(CheckMoveValidity() == true) {
				moveChessPiece();
				ChessMessage msg = new ChessMessage();
				msg.type = ChessMessage.BOARD;
				msg.chessBoardArray = chessBoardConfig;
				msg.pieceColor = colorInfo;
				clnt.sendMessage(msg);
//				checkKingPosition();
				
//					clnt.sendMessage(getChessBoardConfig(), getColorInfo());
			}
			
			else {
				message.setText("INVALID MOVE!");
			}
			
			isSelected = false;
//			System.out.printf("\nSOURCEX: %d SOURCEY: %d DESTX: %d DESTY: %d\n", sourceX, sourceY, destX, destY);
			
		}
		
		
		
		
		// TODO:
		// selected global variable initially false
		// if selected is false
		//    store x y in global variable sourceX, sourceY
		//    set selected to true
		// if selected is true
		//    store x y in global variable destX, destY
		//    find the chesspiece in chessBoardConfig array
		//    check if the move is valid using class objects
		//    if valid: 
		//        call method moveChessPiece(sX, sY, dX, dY) - changes chessBoardConfig + colorInfo
		//        change currMov
		//        call method drawBoard()
		//		  set selected to false
	}

	public int[][] getChessBoardConfig() {
		return chessBoardConfig;
	}

	public int[][] getColorInfo() {
		return colorInfo;
	}
	
//	public int[][] getCompareChessBoard(){
//		return compareChessBoard;
//	}
}
