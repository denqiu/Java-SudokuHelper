package sudokuGame;

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
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * WE DECLARE THAT THIS CODE IS OUR OWN WRITTEN CODE AND THAT WE,
 * BRITTANY PRICE AND DENNIS QIU, DID NOT VIOLATE THE UNCW ACADEMIC HONOR CODE 
 * WHILE COMPLETING THIS PROJECT.
 * This class models a game controller of the SudokuGame.
 * @author BrittanyPrice, DennisQiu
 */                                         
public class SudokuController implements ActionListener, FocusListener, KeyListener, 
					MouseListener, MouseMotionListener {

	private static String resourcesFolder = "Resources.";
	
	private SudokuCells currentCell = null;
	private static SudokuCells cursorCell = null;
	private SudokuCells typedCell = null;
	
	private int cellNumbers = 0;
	
	private String stringHints = "";
	private String stringPuzzle = "";
	
	ArrayList<Integer> availableHints = new ArrayList<Integer>(9);
	
	/**
	 * An empty game controller constructor. 
	 */
	public SudokuController() {
		
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
		Color[] boardColors = {Color.WHITE, Color.YELLOW};
		int blockNumber; 
		int index;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {	
				blockNumber = getBlockPosition(j, i);
				index = blockNumber % 2;
				
				SudokuBoard.allCells[i][j].setCellValue(0);
				SudokuBoard.allCells[i][j].setText(" ");
				SudokuBoard.allCells[i][j].setBackground(boardColors[index]);
				SudokuBoard.allCells[i][j].setBorder(blackBorder);
				SudokuBoard.allCells[i][j].setEditable(true);
				SudokuBoard.allCells[i][j].setEnabled(true);
			}
		}
	}
	
	/**
	 * Restarts the SudokuGame board, resets the puzzle back to the original puzzle that was opened.
	 */
	public static void restartBoard() {
		Color[] boardColors = {Color.WHITE, Color.YELLOW};
		int blockNumber; 
		int index;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {	
				blockNumber = getBlockPosition(j, i);
				index = blockNumber % 2;
				
				if (!SudokuBoard.allCells[i][j].getBackground().equals(Color.MAGENTA)) {
					SudokuBoard.allCells[i][j].setCellValue(0);
					SudokuBoard.allCells[i][j].setText(" ");
					SudokuBoard.allCells[i][j].setBackground(boardColors[index]);
					SudokuBoard.allCells[i][j].setEditable(true);
				}
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
        	SudokuBoard.puzzleNumber.setText("");
        	clearBoard();
		}
		
		if (arg0.getActionCommand().equals("Restart")) {
        	SudokuBoard.puzzleNumber.setText("");
        	for (int i = 0; i < 9; i++)
        		for (int j = 0; j < 9; j++)
        			if (SudokuBoard.allCells[i][j].isEnabled() == true)
        				restartBoard();
		}
		
		
		if (arg0.getActionCommand().equals("Read")) {
			JFileChooser chooser = new JFileChooser(resourcesFolder);
	        int status = chooser.showOpenDialog(SudokuBoard.c);

	        if (status == JFileChooser.APPROVE_OPTION) {
	            File file = chooser.getSelectedFile();
	            resourcesFolder = file.getAbsolutePath();
				SudokuBoard.readPuzzle(file);
				
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
            	SudokuBoard.puzzleNumber.setText(stringPuzzle);	
	        }
		}
		
		if (arg0.getActionCommand().equals("Save")) {
			JFileChooser chooser = new JFileChooser(resourcesFolder);
	        int status = chooser.showSaveDialog(SudokuBoard.c);

	        if (status == JFileChooser.APPROVE_OPTION) {
	            File file = chooser.getSelectedFile();
	            resourcesFolder = file.getAbsolutePath();
				SudokuBoard.savePuzzle(file);
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
		SudokuCells cellEntered = (SudokuCells) o;
		currentCell = cellEntered;
		SudokuBlocks blockEntered = (SudokuBlocks) cellEntered.getParent();
		
		Point position = cellEntered.getPosition();
		availableHints.clear();
		
		if (cellEntered.getCellValue() != 0) {
			SudokuBoard.hints.setText(" ");
			return;
		}
		ArrayList<Integer> existingNumbers = new ArrayList<Integer>(9);
		existingNumbers.clear(); 

		for (int i = 0; i < 9; i++) { // check if numbers already exist within column
			cellNumbers = SudokuBoard.allCells[i][(int) position.getY()].getCellValue();
			if (cellNumbers != 0) {
				if (existingNumbers.contains(cellNumbers))
					continue;
				else
					existingNumbers.add(cellNumbers);
			}
		}
		for (int i = 0; i < 9; i++) { // check if numbers already exist within row
			cellNumbers = SudokuBoard.allCells[(int) position.getX()][i].getCellValue();
			if (cellNumbers != 0) {
				if (existingNumbers.contains(cellNumbers))
					continue;
				else
					existingNumbers.add(cellNumbers);
			}
		}
		for (SudokuCells c: blockEntered.getBlocks()) { // check if numbers already exist within block
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
		SudokuBoard.hints.setText(stringHints);
	}

	/**
	 * Performs the various actions when the mouse exits the visual field of the SudokuGame board.
	 * Sets both the SudokuCells and SudokuHints to empty to show that the mouse is not within a cell.
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		currentCell = null;
		typedCell = null;
		stringHints = "";
		SudokuBoard.hints.setText(stringHints);
	}

	/**
	 * Performs the various actions whenever a mouse is pressed down on the SudokuGame board.
	 * Shows which cell the user is currently in, by the color of the cell.
	 * Sends the message that the cell that is typed is the cell the user is currently in.
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (typedCell != null) {
			if (currentCell != null) {
				currentCell.requestFocusInWindow();
				typedCell = currentCell;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Gains a random color.
	 */
	@Override
	public void focusGained(FocusEvent arg0) {
		Color[] randomColors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, 
				Color.PINK, Color.RED, Color.YELLOW};
		Color[] randomBorderColors = {Color.YELLOW, Color.CYAN, Color.BLUE, Color.PINK, Color.ORANGE, 
				Color.RED, Color.MAGENTA, Color.GREEN};
		
		Color myRandomColor = randomColors[new Random().nextInt(randomColors.length)];
		Color myRandomBorder = randomBorderColors[new Random().nextInt(randomBorderColors.length)];

		Border randomBorder = new LineBorder(myRandomBorder, 3);

		if (currentCell != null) {
			currentCell.setBackground(myRandomColor);
			currentCell.setBorder(randomBorder);
		}
	}

	/**
	 * Loses a random color.
	 */
	@Override
	public void focusLost(FocusEvent arg0) {
		Color[] randomColors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, 
				Color.PINK, Color.RED, Color.YELLOW};
		Color[] randomBorderColors = {Color.YELLOW, Color.CYAN, Color.BLUE, Color.PINK, Color.ORANGE, 
				Color.RED, Color.MAGENTA, Color.GREEN};
		
		Color myRandomColor = randomColors[new Random().nextInt(randomColors.length)];
		Color myRandomBorder = randomBorderColors[new Random().nextInt(randomBorderColors.length)];

		Border randomBorder = new LineBorder(myRandomBorder, 3);

		if (currentCell != null) {
			currentCell.setBackground(myRandomColor);
			currentCell.setBorder(randomBorder);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * Performs the various actions whenever a typed key is released on the SudokuGame board.
	 * Successfully executes the back space key on any cell that is not colored magenta, or if 
	 * the cell was empty to begin with.
	 * Ensures that the numbers in each pink cell will not be deleted when the BACK SPACE key
	 * is pressed.
	 */
	@Override
	public void keyReleased(KeyEvent arg0) {
		try { //back space on any cell that does not have a magenta background
			if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (currentCell.getBackground() != Color.MAGENTA) {
					if (currentCell.getCellValue() > 0) {
						currentCell.setCellValue(0);
						currentCell.setText(" ");
					}
				}
				return;
			}
			String cellTextInput = currentCell.getText().trim();
			int cellNumberInput = Integer.valueOf(cellTextInput);

			Color[] randomColors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, 
					Color.PINK, Color.RED, Color.YELLOW};			
			Color myRandomColor = randomColors[new Random().nextInt(randomColors.length)];
			
			if (availableHints.contains(cellNumberInput)) {
				currentCell.setCellValue(cellNumberInput);
				currentCell.setText(cellTextInput);
				currentCell.setForeground(myRandomColor);
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

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Object o = arg0.getSource();
		SudokuCells mouseCell = (SudokuCells) o;		
		cursorCell = mouseCell;
	
		int blockNumber; 
		int index;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {	
				blockNumber = getBlockPosition(j, i);
				index = blockNumber % 2;

				if (SudokuBoard.allCells[i][j].isEnabled() == true) {
					cursorCell.setBackground(Color.PINK);
					if (SudokuBoard.allCells[i][j].getBackground().equals(Color.PINK)) {
						if (index == 0) 
							SudokuBoard.allCells[i][j].setBackground(Color.WHITE);
						if (index == 1) 
							SudokuBoard.allCells[i][j].setBackground(Color.YELLOW);
					}
				}
				else if (SudokuBoard.allCells[i][j].isEnabled() == false) {
					cursorCell.setBackground(Color.MAGENTA);
					if (SudokuBoard.allCells[i][j].getBackground().equals(Color.PINK)) 
						SudokuBoard.allCells[i][j].setBackground(Color.MAGENTA);
				}
			}
		}
	}
}
