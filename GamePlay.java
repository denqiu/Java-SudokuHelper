package sudoku;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

/**
 * WE DECLARE THAT THIS CODE IS OUR OWN WRITTEN CODE AND THAT WE,
 * BRITTANY PRICE AND DENNIS QIU, DID NOT VIOLATE THE UNCW ACADEMIC HONOR CODE 
 * WHILE COMPLETING THIS PROJECT.
 * This class models a game controller of the SudokuGame.
 * @author BrittanyPrice
 * @author DennisQiu
 */                                           //Dennis and Brittany
public class GamePlay implements ActionListener, MouseListener, KeyListener, FocusListener {

	private static String currentDirectory = ".";
	
	private Cells currentCell = null;
	private Cells typedCell = null;
	
	private int cellNumbers = 0;
	
	private String stringHints = "";
	private String stringPuzzle = "";
	
	ArrayList<Integer> availableHints = new ArrayList<Integer>(9);
	
	/**
	 * An empty game play constructor. 
	 */
	public GamePlay() {
		
	}
	
	/**
	 * Determines the position of each block in the SudokuGame board.
	 * @param x the X-Coordinate position of the individual cell in a block
	 * @param y the Y-Coordinate position of the individual cell in a block
	 * @return The position of the block in the SudokuGame board.
	 */
	private static int getBlockPosition(int x, int y) {
        int getBlockPosition;
        
        if (y < 3) { //blocks 0,1,2
			if (x < 3) 
				getBlockPosition = 0;
			else if (x < 6) 
				getBlockPosition = 1;
			else 
				getBlockPosition = 2;
		} 
        else if (y < 6) { //blocks 3,4,5
			if (x < 3) 
				getBlockPosition = 3;
			else if (x < 6) 
				getBlockPosition = 4;
			else 
				getBlockPosition = 5;
		} 
        else { //blocks 6,7,8
			if (x < 3) 
				getBlockPosition = 6;
			else if (x < 6) 
				getBlockPosition = 7;
			else 
				getBlockPosition = 8;
		}
		return getBlockPosition;
	}

	/**
	 * Clears the SudokuGame board, resetting any placements or color changes during the game.
	 */
	public static void clearBoard() {
		Color [] boardColors= {Color.WHITE, Color.PINK};
		int blockNumber; 
		int index;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {	
				blockNumber = getBlockPosition(j, i);
				index = blockNumber % 2;
				
				GameBoard.allCells[i][j].setCellValue(0);
				GameBoard.allCells[i][j].setText(" ");
				GameBoard.allCells[i][j].setBackground(boardColors[index]);
				GameBoard.allCells[i][j].setEditable(true);
			}
		}
	}

	/**
	 * Performs the various actions whenever a puzzle option is clicked.
	 * The "New" option clears the SudokuGame board.
	 * The "Read" option opens to the Resources folder by default.
	 * The "Save" option saves to the Resources folder by default.
	 * The "Exit" option exits the SudokuGame. 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (arg0.getActionCommand().equals("New")) {
        	GameBoard.puzzleNumber.setText("");
        	clearBoard();
		}
		
		if (arg0.getActionCommand().equals("Read")) {
			JFileChooser chooser = new JFileChooser(currentDirectory);
	        int status = chooser.showOpenDialog(GameBoard.c);

	        if (status == JFileChooser.APPROVE_OPTION) {
	            File file = chooser.getSelectedFile();
				currentDirectory = file.getAbsolutePath();
				GameBoard.readPuzzle(file);
				
	            String getFile = file.getName();
	            Matcher match = Pattern.compile("\\d+").matcher(getFile);

	            stringPuzzle = "";
	            if (match.find()) {
	            	if (getFile.endsWith(".txt")) {
	            		int fileNumber = Integer.parseInt(match.group());
						stringPuzzle = Integer.toString(fileNumber);
	            	}
	            	else 
						stringPuzzle = "This is not a puzzle. Please choose a puzzle.";
	    		}
            	GameBoard.puzzleNumber.setText(stringPuzzle);	
	        }
		}
		
		if (arg0.getActionCommand().equals("Save")) {
			JFileChooser chooser = new JFileChooser(currentDirectory);
	        int status = chooser.showSaveDialog(GameBoard.c);

	        if (status == JFileChooser.APPROVE_OPTION) {
	            File file = chooser.getSelectedFile();
				currentDirectory = file.getAbsolutePath();
				GameBoard.savePuzzle(file);
	        }
		}
		
		if (arg0.getActionCommand().equals("Exit")) {
			System.exit(0);
		} 	
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Performs the various actions whenever a mouse enters the SudokuGame board.
	 * Checks if each row, column, and block contains numbers to determine the 
	 * best possible numbers that does not go against the SudokuGame rules.
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		Object o = arg0.getSource();
		Cells cellEntered = (Cells) o;
		currentCell = cellEntered;
		Blocks blockEntered = (Blocks) cellEntered.getParent();
		
		Point position = cellEntered.getPosition();
		availableHints.clear();
		
		if (cellEntered.getCellValue() != 0) {
			GameBoard.hints.setText(" ");
			return;
		}
		ArrayList<Integer> existingNumbers = new ArrayList<Integer>(9);
		existingNumbers.clear(); 

		for (int i = 0; i < 9; i++) { // check if numbers already exist within column
			cellNumbers = GameBoard.allCells[i][(int) position.getY()].getCellValue();
			if (cellNumbers != 0) {
				if (existingNumbers.contains(cellNumbers))
					continue;
				else
					existingNumbers.add(cellNumbers);
			}
		}
		for (int i = 0; i < 9; i++) { // check if numbers already exist within row
			cellNumbers = GameBoard.allCells[(int) position.getX()][i].getCellValue();
			if (cellNumbers != 0) {
				if (existingNumbers.contains(cellNumbers))
					continue;
				else
					existingNumbers.add(cellNumbers);
			}
		}
		for (Cells c: blockEntered.getBlocks()) { // check if numbers already exist within block
			cellNumbers = c.getCellValue();
			if (cellNumbers != 0) {
				if (existingNumbers.contains(cellNumbers))
					continue;
				else
					existingNumbers.add(cellNumbers);
			}
		}
		for (int i = 1; i <= 9; i++) { //adds numbers that do not violate SudokuRules
			if (existingNumbers.contains(i))
				continue;
			else {
				availableHints.add(i);
				if (stringHints == "")
					stringHints = Integer.toString(i);
				else 
					stringHints = stringHints + ", " + Integer.toString(i);
			}
		}

		if (availableHints.isEmpty())
			stringHints = "Sorry. You used up all the available numbers.";
		GameBoard.hints.setText(stringHints);
	}

	/**
	 * Performs the various actions when the mouse exits the visual field of the SudokuGame board.
	 * Sets both the SudokuCells and SudokuHints to empty to show that the mouse is not within a cell.
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		currentCell = null;
		stringHints = "";
		GameBoard.hints.setText(stringHints);
	}

	/**
	 * Performs the various actions whenever a mouse is pressed down on the SudokuGame board.
	 * Shows which cell the user is currently in, by the color of the cell.
	 * Sends the message that the cell that is typed is the cell the user is currently in.
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (typedCell != null) {
			if (typedCell.getBackground().equals(Color.CYAN)) 
				typedCell.setBackground(Color.PINK);
			else if (typedCell.getBackground().equals(Color.GREEN))
				typedCell.setBackground(Color.WHITE);
		}
		if (currentCell != null) {
			currentCell.requestFocusInWindow();
			typedCell = currentCell;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Switches between colors YELLOW and CYAN while inside a block of YELLOW cells.
	 */
	@Override
	public void focusGained(FocusEvent arg0) {
		if (currentCell != null) {
			if (currentCell.getBackground().equals(Color.PINK))
				currentCell.setBackground(Color.CYAN);
		}
	}

	/**
	 * Switches between colors WHITE and GREEN while inside a block of WHITE cells.
	 */
	@Override
	public void focusLost(FocusEvent arg0) {
		if (currentCell != null) {			
			if (currentCell.getBackground().equals(Color.WHITE))
				currentCell.setBackground(Color.GREEN);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Performs the various actions whenever a typed key is released on the SudokuGame board.
	 * Successfully executes the back space key on any cell that is not colored pink, or if 
	 * the cell was empty to begin with.
	 * Ensures that the numbers in each pink cell will not be deleted when the BACK SPACE key
	 * is pressed.
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		try { //back space on any cell that does not have a pink background
			if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (currentCell.getBackground() != Color.PINK) {
					if (currentCell.getCellValue() > 0) {
						currentCell.setCellValue(0);
						currentCell.setText(" ");
					}
				}
				return;
			} 
			String cellTextInput = currentCell.getText().trim();
			int cellNumberInput = Integer.valueOf(cellTextInput);

			if (availableHints.contains(cellNumberInput)) {
				currentCell.setCellValue(cellNumberInput);
				currentCell.setText(cellTextInput);
				currentCell.setForeground(Color.RED);
			} 
			else
				currentCell.setText(" ");
		} 
		catch (NumberFormatException n) {
			currentCell.setText(" ");
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
}
