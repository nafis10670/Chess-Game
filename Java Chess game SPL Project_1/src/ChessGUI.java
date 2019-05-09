import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;

import jChess.*;

import javax.imageio.ImageIO;

public class ChessGUI  implements ActionListener{

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private final JLabel message = new JLabel(
            "Java Chess Game is ready to play!");
    private static final String COLS = "ABCDEFGH";
    public static final int QUEEN = 0, KING = 1,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5, BLANK = -1;
    
    public static final int BLACK = 0, WHITE = 1;
    public int currentMove;
    
    private boolean isSelected;
    
    private	int sourceX;
    private int sourceY;
    private int destX;
    private int destY;
    
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
        tools.add(newGameAction);
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
        		{ ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK},
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
        		{ BLANK,BLANK,  BLANK,  BLANK,BLANK, BLANK,  BLANK,  BLANK}, 
        		{ BLANK,BLANK,  BLANK,  BLANK,BLANK, BLANK,  BLANK,  BLANK},
        		{ BLANK,BLANK,  BLANK,  BLANK,BLANK, BLANK,  BLANK,  BLANK},
        		{ BLANK,BLANK,  BLANK,  BLANK,BLANK, BLANK,  BLANK,  BLANK},
           		{ WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE},
        		{ WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE}
        };
    }

    public final JComponent getGui() {
        return gui;
    }

    private final void createImages() {
        try {
            //URL url = new URL("http://i.stack.imgur.com/memI0.png");
            File url = new File("images\\memI0.png");
            BufferedImage bi = ImageIO.read(url);
            for (int ii = 0; ii < 2; ii++) {
                for (int jj = 0; jj < 6; jj++) {
                    chessPieceImages[ii][jj] = bi.getSubimage(
                            jj * 64, ii * 64, 64, 64);
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
    private final void drawBoard() {
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
    
    public boolean CheckPieceValidity() {
    	boolean valid = false;
//    	return true;
    	
    	if(chessBoardConfig[sourceX][sourceY] == ROOK) {
//    		System.out.printf("\nSOURCEX: %d SOURCEY: %d DESTX: %d DESTY: %d\n", sourceX, sourceY, destX, destY);
    		valid =  rookObj.isValid(sourceX, sourceY, destX, destY);
    	}
    	
//    	else if(chessBoardConfig[sourceX][sourceY] == KNIGHT) {
//    		valid =  knightObj.isValid(sourceX, sourceY, destX, destY);
//    	}
//    	
    	else if(chessBoardConfig[sourceX][sourceY] == BISHOP) {
    		valid =  bishopObj.isValid(sourceX, sourceY, destX, destY);
    	}
    	
    	else if(chessBoardConfig[sourceX][sourceY] == KING) {
    		valid =  kingObj.isValid(sourceX, sourceY, destX, destY);
    	}
    	
    	else if(chessBoardConfig[sourceX][sourceY] == QUEEN) {
    		valid =  queenObj.isValid(sourceX, sourceY, destX, destY);
    	}
    	
    	else if(chessBoardConfig[sourceX][sourceY] == PAWN) {
    		valid =  pawnObj.isValid(sourceX, sourceY, destX, destY);
    	}
    	
//    	else if(chessBoardConfig[sourceX][sourceY] == KNIGHT) {
//    		knightObj.isValid(sourceX, sourceY, destX, destY);
//    	}
    	
    	return valid;
    }
    
    public void moveChessPiece() {
    	chessBoardConfig[destX][destY] = chessBoardConfig[sourceX][sourceY];
    	colorInfo[destX][destY] = colorInfo[sourceX][sourceY];
    	
    	chessBoardConfig[sourceX][sourceY] = BLANK;
    	colorInfo[sourceX][sourceY] = BLANK;
    	
    	currentMove = 1 - currentMove;
    	drawBoard();
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
		
		System.out.println("X : " + x + " Y : " + y);
		
		if(isSelected == false) {
			sourceX = x;
			sourceY = y;
			isSelected = true;
		}
		
		else if(isSelected == true) {
			destX = x;
			destY = y;
			System.out.print("Selected");
			if(CheckPieceValidity() == true) {
				moveChessPiece();
				isSelected = false;
			}
			
			else {
				System.out.println("Invalid Move!");
			}
			System.out.printf("\nSOURCEX: %d SOURCEY: %d DESTX: %d DESTY: %d\n", sourceX, sourceY, destX, destY);
			
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
}
